package com.newland.financial.p2p.service.Impl;

import com.newland.financial.p2p.service.IProductService;
import lombok.extern.java.Log;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

@Component
@Log
public class ProductServiceHystrix implements IProductService {
    public Object getProduct(@RequestBody String jsonStr) {
        log.info("ProductServiceHystrix:getProduct");
        return 1026;
    }

    public Object insertProduct(@RequestBody String jsonStr) {
        log.info("ProductServiceHystrix:insertProduct");
        return 1026;
    }

    public Object putOrDown(@RequestBody String jsonStr) {
        log.info("ProductServiceHystrix:putOrDown");
        return 1026;
    }
}
