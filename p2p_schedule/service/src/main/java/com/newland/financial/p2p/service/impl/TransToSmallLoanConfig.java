package com.newland.financial.p2p.service.impl;

import com.google.common.collect.Lists;
import com.newland.financial.p2p.batch.BaseCompositeItemWriter;
import com.newland.financial.p2p.domain.entity.CustomerFlowDebit;
import com.newland.financial.p2p.domain.entity.ExcelOrderModel;
import lombok.extern.log4j.Log4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
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

/**
 * batch配置类.
 * @author gregory
 */
@Configuration
@Log4j
public class TransToSmallLoanConfig {
    /**
     * 临时文件地址.
     */
    @Value("${SMALLLOAN_FILE_ADDRESS}")
    private String address;
    /**
     * jobs.
     */
    @Autowired
    private JobBuilderFactory jobs;
    /**
     * steps.
     */
    @Autowired
    private StepBuilderFactory steps;
    /**
     * jobLauncher.
     */
    @Autowired
    private JobLauncher jobLauncher;

    /**
     * 生成csv文件.
     *
     * @param runDay 文件日期
     * @return ItemWriter
     * @throws URISyntaxException   URISyntaxException
     * @throws MalformedURLException    MalformedURLException
     */
    @Bean("getFlatFileItemWriter")
    @StepScope
    public ItemWriter getFlatFileItemWriter(@Value("#{jobParameters['runDay']}") String runDay) throws URISyntaxException, MalformedURLException {
        //String runDay = "";
        log.debug("runDay:" + address + "outputfile-" + runDay + ".csv");
        FlatFileItemWriter flatFileItemWriter = new FlatFileItemWriter();
        flatFileItemWriter.setResource(new PathResource(address + "outputfile-" + runDay + ".csv"));
        DelimitedLineAggregator delimitedLineAggregator = new DelimitedLineAggregator();
        delimitedLineAggregator.setDelimiter(",");
        BeanWrapperFieldExtractor beanWrapperFieldExtractor = new BeanWrapperFieldExtractor();
        beanWrapperFieldExtractor.setNames(new String[]{"dDate", "oddNumbers", "applyName", "phone", "dMoney",
                "province","city","region","detailAdd", "identityCard","merchantNum"});
        delimitedLineAggregator.setFieldExtractor(beanWrapperFieldExtractor);
        flatFileItemWriter.setLineAggregator(delimitedLineAggregator);

        return flatFileItemWriter;
    }

    /**
     * 对象转换Bean.
     * @return  CreateExcelProcessor
     */
    @Bean
    public CreateExcelProcessor getCreateExcelProcessor() {
        return new CreateExcelProcessor();
    }

    /**
     * ftp传输Bean.
     * @return  TransforToFtpTasklet
     */
    @Bean
    public TransforToFtpTasklet getTransforToFtpTasklet() {
        return new TransforToFtpTasklet();
    }

    /**
     * 移动临时文件Bean.
     * @return  MoveToTempTasklet
     */
    @Bean
    public MoveToTempTasklet getMoveToTempTasklet() {
        return new MoveToTempTasklet();
    }

    /**
     * 删除过期文件Bean.
     * @return DelOverTimeFileTasklet
     */
    @Bean
    public DelOverTimeFileTasklet getDelOverTimeFileTasklet() {
        return new DelOverTimeFileTasklet();
    }

    /**
     * 批处理流程定义.
     * @param step1 生成csv文件
     * @param step2 发送csv到ftp
     * @param step3 移动临时文件
     * @param step4 删除过期文件
     * @return Job
     */
    @Bean(name = "createTransToSmallLoanCompanyJob")
    public Job createTransforToSmallLoanCompanyJob(@Qualifier("step1") Step step1, @Qualifier("step2") Step step2,
                                                   @Qualifier("step3") Step step3, @Qualifier("step4") Step step4) {
        Job job = jobs.get("transforToSmallLoanCompany")
                .incrementer(new RunIdIncrementer())
                .start(step1).next(step2).next(step3).next(step4)
                .listener(new TransToSmallLoanJobExecutionListener())
                .build();
        return job;
    }

    /**
     * 生成csv文件.
     * @param reader               reader
     * @param createExcelProcessor createExcelProcessor
     * @param dbWriter             dbWriter
     * @param fileWriter           fileWriter
     * @return Step
     */
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
                .<CustomerFlowDebit, ExcelOrderModel>chunk(5)
                .reader(reader)
                .processor(createExcelProcessor)
                .writer(writer)
                .allowStartIfComplete(true)
                .build();
    }

    /**
     * 发送csv到ftp.
     * @param transforToFtpProcessor ftp传输对象
     * @return Step Step
     */
    @Bean
    protected Step step2(TransforToFtpTasklet transforToFtpProcessor) {
        return steps.get("step2").tasklet(transforToFtpProcessor).allowStartIfComplete(true).build();

    }

    /**
     * 移动临时文件.
     * @param moveToTempTasklet 文件移动对象
     * @return Step
     */
    @Bean
    protected Step step3(MoveToTempTasklet moveToTempTasklet) {
        return steps.get("step3").tasklet(moveToTempTasklet).allowStartIfComplete(true).build();

    }

    /**
     * 删除过期文件.
     * @param delOverTimeFileTasklet 文件删除对象
     * @return Step
     */
    @Bean
    protected Step step4(DelOverTimeFileTasklet delOverTimeFileTasklet) {
        return steps.get("step4").tasklet(delOverTimeFileTasklet).allowStartIfComplete(true).build();
    }

}
