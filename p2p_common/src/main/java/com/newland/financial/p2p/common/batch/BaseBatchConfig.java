package com.newland.financial.p2p.common.batch;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.batch.MyBatisPagingItemReader;
import org.springframework.batch.item.ItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BaseBatchConfig {
    @Bean
    public ItemReader getBaseItemReader(SqlSessionFactory sqlSessionFactory) {
        MyBatisPagingItemReader myBatisPagingItemReader = new MyBatisPagingItemReader();
        myBatisPagingItemReader.setSqlSessionFactory(sqlSessionFactory);
        myBatisPagingItemReader.setPageSize(1000);
    }

}
