package com.newland.financial.p2p.common.batch;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.batch.MyBatisBatchItemWriter;
import org.mybatis.spring.batch.MyBatisPagingItemReader;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BaseBatchConfig {
    @Bean
    @StepScope
    public ItemReader getItemReader(SqlSessionFactory sqlSessionFactory, @Value("#{jobParameters['queryId']}") String queryId) {
        MyBatisPagingItemReader myBatisPagingItemReader = new MyBatisPagingItemReader();
        myBatisPagingItemReader.setSqlSessionFactory(sqlSessionFactory);
        myBatisPagingItemReader.setPageSize(10);
        myBatisPagingItemReader.setQueryId(queryId);
        return myBatisPagingItemReader;
    }

    @Bean
    @StepScope
    public ItemWriter getItemWriter(SqlSessionFactory sqlSessionFactory, @Value("#{jobParameters['statementId']}") String statementId) {
        MyBatisBatchItemWriter myBatisBatchItemWriter = new MyBatisBatchItemWriter();
        myBatisBatchItemWriter.setSqlSessionFactory(sqlSessionFactory);
        myBatisBatchItemWriter.setSqlSessionTemplate(new SqlSessionTemplate(sqlSessionFactory, ExecutorType.BATCH));
        myBatisBatchItemWriter.setStatementId(statementId);
        return myBatisBatchItemWriter;
    }
}
