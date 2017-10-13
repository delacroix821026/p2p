package com.newland.financial.p2p.controller;

import com.newland.financial.p2p.service.FeignService;
import com.newland.financial.p2p.service.IProductService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author cendaijuan
 * */
@RestController
@Log
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private IProductService productService;

    @RequestMapping(value = "/GetProduct/{paramA}", method = RequestMethod.POST)
    public Object getProduct(@PathVariable(name="paramA") String paramA, @RequestBody String jsonStr){
        log.info("controller add Enter: A:" +paramA);
        return productService.getProduct(jsonStr);
    }

    @RequestMapping(value = "/InsertProduct/{paramA}", method = RequestMethod.POST)
    public Object insertProduct(@PathVariable(name="paramA") String paramA, @RequestBody String jsonStr){
        log.info("controller add Enter: A:" +paramA);
        return productService.insertProduct(jsonStr);
    }

    @RequestMapping(value = "/PutOrDown/{paramA}", method = RequestMethod.POST)
    public Object putOrDown(@PathVariable(name="paramA") String paramA, @RequestBody String jsonStr){
        log.info("controller add Enter: A:" +paramA);
        return productService.putOrDown(jsonStr);
    }
}
