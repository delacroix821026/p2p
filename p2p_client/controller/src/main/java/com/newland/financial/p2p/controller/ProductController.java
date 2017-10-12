package com.newland.financial.p2p.controller;

import com.newland.financial.p2p.service.FeignService;
import com.newland.financial.p2p.service.IProductService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author cendaijuan
 * */
@RestController
@Log
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private IProductService productService;

/*    @RequestMapping(value = "/GetProductList", method = RequestMethod.POST)
    public Object getProductList(){
        return productService.getProductList();
    }*/

    @RequestMapping(value = "/GetProduct", method = RequestMethod.POST)
    public Object getProduct(@RequestBody String jsonStr){
        return productService.getProduct(jsonStr);
    }

    @RequestMapping(value = "/InsertProduct", method = RequestMethod.POST)
    public Object insertProduct(@RequestBody String jsonStr){
        return productService.insertProduct(jsonStr);
    }

    @RequestMapping(value = "/PutOrDown", method = RequestMethod.POST)
    public Object putOrDown(@RequestBody String jsonStr){
        return productService.putOrDown(jsonStr);
    }
}
