package com.newland.financial.p2p.service;

import com.newland.financial.p2p.service.Impl.FeignServiceHystrix;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "p2p-server${DEVLOPER_NAME:}", fallback = FeignServiceHystrix.class)
public interface FeignService {
    @RequestMapping(method = RequestMethod.GET, value = "/add")
    Integer add(@RequestParam(value = "a") Integer a, @RequestParam(value = "b") Integer b);

}
