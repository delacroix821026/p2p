package com.newland.financial.p2p.service.Impl;

import com.newland.financial.p2p.service.IProductService;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

@Component
@Log4j
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

    public Object updateProd(@RequestBody String jsonStr){
        log.info("ProductServiceHystrix:updateProd");
        return 1026;
    }

    public Object getProdList(@RequestBody String jsonStr){
        log.info("ProductServiceHystrix:getProdList");
        return 1026;
    }

    public Object getAppPro(@RequestBody String jsonStr){
        log.info("ProductServiceHystrix:getAppPro");
        return 1026;
    }

    public Object getAllProStus(String jsonStr) {
        log.info("ProductServiceHystrix:getAllProStus");
        return 1026;
    }
}
