package com.newland.financial.p2p.controller;

import lombok.extern.java.Log;
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
    @RequestMapping(value = "/add1", method = RequestMethod.GET)
    public Integer add(@RequestParam final Integer a, @RequestParam final Integer b) {
        ServiceInstance instance = client.getLocalServiceInstance();
        Integer r = a + b;
        log.info("/add, host:" + instance.getHost()
                + ", service_id:" + instance.getServiceId() + ", result:" + r);
        return testInteger6;
    }
    @RequestMapping(value = "/add2", method = RequestMethod.GET)
    public Integer add2(@RequestParam final Integer a, @RequestParam final Integer b) {
        ServiceInstance instance = client.getLocalServiceInstance();
        Integer r = a + b;
        log.info("/add, host:" + instance.getHost()
                + ", service_id:" + instance.getServiceId() + ", result:" + r);
        return testInteger7;
    }

    private Integer testInteger = new Integer(10);
    private Integer testInteger1 = new Integer(20);
    private Integer testInteger2 = new Integer(30);
    private Integer testInteger3 = new Integer(50);
    private Integer testInteger4 = new Integer(60);
    private Integer testInteger5 = new Integer(70);
    private Integer testInteger6 = new Integer(80);
    private Integer testInteger7 = new Integer(90);
}