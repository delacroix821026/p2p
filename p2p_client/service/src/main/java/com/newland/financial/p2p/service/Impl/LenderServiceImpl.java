package com.newland.financial.p2p.service.Impl;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.newland.financial.p2p.service.ILenderService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@Log
public class LenderServiceImpl implements ILenderService {
    @Autowired
    RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "addFallback")
    public Object getLender(String jsonStr) {
        log.info("*****client service*****");
        return restTemplate.postForEntity("http://P2P-SERVER/LenderController/GetLender", jsonStr, Object.class).getBody();
    }

    @HystrixCommand(fallbackMethod = "addFallback")
    public Object debit(String jsonStr) {
        log.info("*****client service*****");
        return restTemplate.postForEntity("http://P2P-SERVER/LenderController/Debit", jsonStr, Object.class).getBody();
    }

    @HystrixCommand(fallbackMethod = "addFallback")
    public Object repay(String jsonStr) {
        log.info("*****client service*****");
        return restTemplate.postForEntity("http://P2P-SERVER/LenderController/Repay", jsonStr, Object.class).getBody();
    }

    @HystrixCommand(fallbackMethod = "addFallback")
    public Object findAllDebit(String jsonStr) {
        log.info("*****client service*****");
        return restTemplate.postForEntity("http://P2P-SERVER/LenderController/FindAllDebit", jsonStr, Object.class).getBody();
    }

    @HystrixCommand(fallbackMethod = "addFallback")
    public Object findAllRepay(String jsonStr) {
        log.info("*****client service*****");
        return restTemplate.postForEntity("http://P2P-SERVER/LenderController/FindAllRepay", jsonStr, Object.class).getBody();
    }

    @HystrixCommand(fallbackMethod = "addFallback")
    public Object findTotalMoney(String jsonStr) {
        log.info("*****client service*****");
        return restTemplate.postForEntity("http://P2P-SERVER/LenderController/FindTotalMoney", jsonStr, Object.class).getBody();
    }

    @HystrixCommand(fallbackMethod = "addFallback")
    public Object getDebitAndRepaySummary(String jsonStr) {
        log.info("*****client service*****");
        return restTemplate.postForEntity("http://P2P-SERVER/LenderController/getDebitAndRepaySummary", jsonStr, Object.class).getBody();
    }

    public String addFallback(String a) {
        log.info("ooxx");
        return "Error：addFallback";
    }
}
