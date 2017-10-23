package com.newland.financial.p2p.service.impl;

import com.google.common.collect.Lists;
import com.newland.financial.p2p.domain.entity.BaseEntity;
import com.newland.financial.p2p.domain.entity.CustomerFlowDebit;
import com.newland.financial.p2p.domain.entity.DebitAndCredit;
import com.newland.financial.p2p.domain.entity.ExcelOrderModel;
import lombok.extern.java.Log;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.batch.MyBatisBatchItemWriter;
import org.mybatis.spring.batch.MyBatisPagingItemReader;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.batch.item.support.CompositeItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.PathResource;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.List;

@Configuration
@Log
public class TransToSmallLoanConfig {
    @Autowired
    private JobBuilderFactory jobs;

    @Autowired
    private StepBuilderFactory steps;

    @Autowired
    private JobLauncher jobLauncher;

    @Bean
    @Scope("prototype")
    public ItemReader getItemReader(SqlSessionFactory sqlSessionFactory) {
        MyBatisPagingItemReader myBatisPagingItemReader = new MyBatisPagingItemReader();
        myBatisPagingItemReader.setSqlSessionFactory(sqlSessionFactory);
        myBatisPagingItemReader.setPageSize(10);
        myBatisPagingItemReader.setQueryId("selectSendOrderList");
        return myBatisPagingItemReader;
    }

    @Bean
    @Scope("prototype")
    public ItemWriter getItemWriter(SqlSessionFactory sqlSessionFactory) {
        MyBatisBatchItemWriter myBatisBatchItemWriter = new MyBatisBatchItemWriter();
        myBatisBatchItemWriter.setSqlSessionFactory(sqlSessionFactory);
        myBatisBatchItemWriter.setSqlSessionTemplate(new SqlSessionTemplate(sqlSessionFactory, ExecutorType.BATCH));
        myBatisBatchItemWriter.setStatementId("updateOrderSendStus");
        return myBatisBatchItemWriter;
    }

    @Bean
    @Scope("prototype")
    public ItemWriter getFlatFileItemWriter() throws URISyntaxException, MalformedURLException {
        FlatFileItemWriter flatFileItemWriter = new FlatFileItemWriter();
//        flatFileItemWriter.setResource(new PathResource("/Users/daijuancen/Desktop/outputfile-#{jobParameters['runDay']}.csv"));
        flatFileItemWriter.setResource(new PathResource("d:\\scheduel\\outputfile-#{jobParameters['runDay']}.csv"));
        DelimitedLineAggregator delimitedLineAggregator = new DelimitedLineAggregator();
        delimitedLineAggregator.setDelimiter(",");
        BeanWrapperFieldExtractor beanWrapperFieldExtractor = new BeanWrapperFieldExtractor();
        beanWrapperFieldExtractor.setNames(new String[] {"dDate","oddNumbers", "applyName","phone","dMoney",
                "detailAdd","identityCard","corpPhone","merchantName"});
        delimitedLineAggregator.setFieldExtractor(beanWrapperFieldExtractor);
        flatFileItemWriter.setLineAggregator(delimitedLineAggregator);

        return flatFileItemWriter;
    }

    @Bean
    public CreateExcelProcessor getCreateExcelProcessor() {
        return new CreateExcelProcessor();
    }

    @Bean
    public TransforToFtpTasklet getTransforToFtpTasklet() {
        return new TransforToFtpTasklet();
    }

    @Bean(name= "createTransToSmallLoanCompanyJob")
    public Job createTransforToSmallLoanCompanyJob(@Qualifier("step1") Step step1, @Qualifier("step2") Step step2) {
        Job job = jobs.get("transforToSmallLoanCompany")
                .incrementer(new RunIdIncrementer())
                .start(step1).next(step2)
                .listener(new TransToSmallLoanJobExecutionListener())
                .build();
        return job;
    }

    @Bean
    protected Step step1(@Qualifier("getItemReader") ItemReader<CustomerFlowDebit> reader,
                         CreateExcelProcessor createExcelProcessor,
                         @Qualifier("getItemWriter") ItemWriter<CustomerFlowDebit> dbWriter,
                         @Qualifier("getFlatFileItemWriter") ItemWriter<ExcelOrderModel> fileWriter) {
        CompositeItemWriter<BaseEntity> writer = new CompositeItemWriter<BaseEntity>();
        List delegates = Lists.newArrayList();
        delegates.add(dbWriter);
        delegates.add(fileWriter);
        writer.setDelegates(delegates);

        return steps.get("step1")
                .<CustomerFlowDebit, ExcelOrderModel> chunk(5)
                .reader(reader)
                .processor(createExcelProcessor)
                .writer(writer)
                .allowStartIfComplete(true)
                .build();
    }

    @Bean
    protected Step step2(TransforToFtpTasklet transforToFtpProcessor) {
        return steps.get("step2").tasklet(transforToFtpProcessor).allowStartIfComplete(true).build();

    }

}
