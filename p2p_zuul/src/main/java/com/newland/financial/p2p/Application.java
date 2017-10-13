package com.newland.financial.p2p;

import com.newland.financial.p2p.filter.SessionFilter;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.ohuyo.libra.client.filter.SlaveClientFilter;

import javax.servlet.FilterConfig;

@EnableZuulProxy
@SpringBootApplication
@EnableDiscoveryClient
@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 86400*30)
@ImportResource("classpath*:beans.xml")
public class Application {
    public static void main(String[] args) {
        new SpringApplicationBuilder(Application.class).web(true).run(args);
    }

    @Bean
    public SessionFilter addSessionFilter() {
        return new SessionFilter();
    }

    @Bean(name = "sessionFilter")
    public SlaveClientFilter addSlaveClientFilter() {
        SlaveClientFilter slaveClientFilter = new SlaveClientFilter();
        return slaveClientFilter;
    }

    @Bean
    public FilterRegistrationBean addSlaveClientFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(addSlaveClientFilter());
        registration.addUrlPatterns("/p2p/*");
        registration.addInitParameter("paramName", "paramValue");
        registration.setName("sessionFilter");
        return registration;
    }
}
