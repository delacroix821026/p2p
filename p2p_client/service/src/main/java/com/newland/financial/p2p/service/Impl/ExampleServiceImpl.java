package com.newland.financial.p2p.service.Impl;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.newland.financial.p2p.service.ExampleService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@Log
public class ExampleServiceImpl implements ExampleService {
    @Autowired
    RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "addFallback")
    public String add(String a) {
        log.info("Enter Add Service");
        return restTemplate.getForEntity("http://P2P-SERVER/add?a=10&b=20", String.class).getBody();
    }

    public String addFallback(String a) {
        log.info("ooxx");
        return "Error：addFallback";
    }
}
