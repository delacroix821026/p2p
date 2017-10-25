package com.newland.financial.p2p.service.impl;

import com.google.common.collect.Lists;
import com.newland.financial.p2p.batch.BaseCompositeItemWriter;
import com.newland.financial.p2p.domain.entity.CustomerFlowDebit;
import com.newland.financial.p2p.domain.entity.ExcelOrderModel;
import lombok.extern.log4j.Log4j;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.PathResource;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.List;

@Configuration
@Log4j
public class TransToSmallLoanConfig {
    @Value("${SMALLLOAN_FILE_ADDRESS}")
    private String address;

    @Autowired
    private JobBuilderFactory jobs;

    @Autowired
    private StepBuilderFactory steps;

    @Autowired
    private JobLauncher jobLauncher;

    @Bean("getFlatFileItemWriter")
    @StepScope
    public ItemWriter getFlatFileItemWriter(@Value("#{jobParameters['runDay']}") String runDay) throws URISyntaxException, MalformedURLException {
        //String runDay = "";
        log.info("runDay:" + address +"outputfile-"+ runDay + ".csv");
        FlatFileItemWriter flatFileItemWriter = new FlatFileItemWriter();
        flatFileItemWriter.setResource(new PathResource(address +"outputfile-"+ runDay + ".csv"));
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
    protected Step step1(ItemReader<CustomerFlowDebit> reader,
                         CreateExcelProcessor createExcelProcessor,
                         @Qualifier("getItemWriter") ItemWriter<ExcelOrderModel> dbWriter,
                         @Qualifier("getFlatFileItemWriter") ItemWriter<ExcelOrderModel> fileWriter) {
        BaseCompositeItemWriter<ExcelOrderModel> writer = new BaseCompositeItemWriter<ExcelOrderModel>();
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
