package com.newland.financial.p2p.service.Impl;

import com.newland.financial.p2p.service.FeignService;
import lombok.extern.java.Log;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

@Component
@Log
public class FeignServiceHystrix implements FeignService {
    public Integer add(@RequestParam(value = "a") Integer a, @RequestParam(value = "b") Integer b) {
        log.info("FeignServiceHystrix:add");
        return 1026;
    }

    public Integer add1(Integer a, Integer b) {
        log.info("FeignServiceHystrix:add1");
        return 3333;
    }

    public Integer add2(Integer a, Integer b) {
        return 4444;
    }


}
