package com.newland.financial.p2p;

import lombok.extern.java.Log;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.ImportResource;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author cendaijuan
 */
@EnableScheduling
@EnableBatchProcessing
@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
@EnableConfigurationProperties
@ImportResource(value = "classpath*:datasource-spring.xml")
@Log
public class Application {
    /**
     * @param args 参数
     */
    public static void main(final String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
