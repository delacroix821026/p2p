package com.newland.financial.p2p.controller;

import lombok.extern.java.Log;
import org.assertj.core.util.DateUtil;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author cendaijuan
 */
@RestController
@RefreshScope
@Log
public class Example {
    /***/
    @Value("${form}")
    private String form;

    /**
     * @return String
     */
    @RequestMapping("/getForm")
    String getForm() {
        return form;
    }

    /***/
    @Autowired
    private DiscoveryClient client;

    /**
     * @param a Integer
     * @param b Integer
     * @return Integer
     */
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public Integer add(@RequestParam final Integer a, @RequestParam final Integer b) {
        ServiceInstance instance = client.getLocalServiceInstance();
        Integer r = a + b;
        log.info("/add, host:" + instance.getHost()
                + ", service_id:" + instance.getServiceId() + ", result:" + r);
        return r;
    }

}