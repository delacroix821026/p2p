package com.newland.financial.p2p.service.Impl;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.newland.financial.p2p.service.IProductService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@Log
public class ProductServiceImpl implements IProductService {
    @Autowired
    RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "addFallback")
    public Object getProductList() {
        return restTemplate.postForEntity("http://P2P-SERVER/ProductController/GetProductList", null, Object.class).getBody();
    }

    @HystrixCommand(fallbackMethod = "addFallback")
    public Object getProduct(String jsonStr) {
        return restTemplate.postForEntity("http://P2P-SERVER/ProductController/GetProduct", jsonStr, Object.class).getBody();
    }

    public String addFallback() {
        log.info("ooxx");
        return "Error：addFallback";
    }
    public String addFallback(String a) {
        log.info("ooxx");
        return "Error：addFallback";
    }
}
