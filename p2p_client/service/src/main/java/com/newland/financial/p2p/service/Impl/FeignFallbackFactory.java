package com.newland.financial.p2p.service.Impl;

import com.newland.financial.p2p.service.FeignService;
import feign.FeignException;
import feign.hystrix.FallbackFactory;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Component;

@Log4j
@Component
public class FeignFallbackFactory implements FallbackFactory<FeignService>, FeignService {
    private Throwable cause;
    public FeignService create(final Throwable cause) {
        this.cause = cause;
        return this;

    }
    public Integer add(Integer a, Integer b) {
        log.error("aaaa" + cause.getMessage());

        return 1026;
    }

    public Integer add1(Integer a, Integer b) {
        log.error("bbbb" + cause.getMessage());
        return 1027;
    }

    public Integer add2(Integer a, Integer b) {
        return 1026;
    }
}
