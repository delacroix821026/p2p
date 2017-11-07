package com.newland.financial.p2p.batch;

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

/**
 * 结果集查询，更新发送状态.
 *
 * @author Gregory
 */
@Configuration
public class BaseBatchConfig {
    /**
     * 查询订单流水.
     *
     * @param sqlSessionFactory sqlSessionFactory
     * @param queryId           sql ID
     * @return  ItemReader
     */
    @Bean
    @StepScope
    public ItemReader getItemReader(SqlSessionFactory sqlSessionFactory, @Value("#{jobParameters['queryId']}") String queryId) {
        MyBatisPagingItemReader myBatisPagingItemReader = new MyBatisPagingItemReader();
        myBatisPagingItemReader.setSqlSessionFactory(sqlSessionFactory);
        myBatisPagingItemReader.setPageSize(10);
        myBatisPagingItemReader.setQueryId(queryId);
        return myBatisPagingItemReader;
    }

    /**
     * 更新发送状态.
     *
     * @param sqlSessionFactory sqlSessionFactory
     * @param statementId       sql ID
     * @return  ItemWriter
     */
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
