package com.newland.financial.p2p.controller;

import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author cendaijuan
 */
@RestController
@RefreshScope
@Log4j
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
        log.debug("/add, host:" + instance.getHost()
                + ", service_id:" + instance.getServiceId() + ", result:" + r);
        log.info("p2p_server come in==========");
        return r;
    }

}