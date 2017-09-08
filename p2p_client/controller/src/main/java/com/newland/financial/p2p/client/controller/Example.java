package com.newland.financial.p2p.client.controller;


import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RefreshScope
@Log
public class Example {
    @Value("${form}")
    private String form;

    @Autowired
    RestTemplate restTemplate;

    @RequestMapping("/getForm")
    String getForm() {
        return form;
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String add() {
        return restTemplate.getForEntity("http://P2P-SERVER/add?a=10&b=20", String.class).getBody();
    }


}