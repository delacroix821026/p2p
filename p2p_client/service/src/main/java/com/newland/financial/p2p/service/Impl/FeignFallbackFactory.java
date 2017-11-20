package com.newland.financial.p2p.service.Impl;

import com.newland.financial.p2p.service.FeignService;
import feign.hystrix.FallbackFactory;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Component;

@Log4j
@Component
public class FeignFallbackFactory implements FallbackFactory<FeignService> {
    public FeignService create(final Throwable cause) {
        return new FeignService() {
            public Integer add(Integer a, Integer b) {
                log.error(cause.getMessage());
                return 1026;
            }

            public Integer add1(Integer a, Integer b) {
                return 1026;
            }

            public Integer add2(Integer a, Integer b) {
                return 1026;
            }
        };
    }
}
