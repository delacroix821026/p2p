package com.newland.financial.p2p.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.newland.financial.p2p.domain.entity.IProduct;
import com.newland.financial.p2p.service.IProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * @author cendaijuan
 * */
@Controller
@RequestMapping("/ProductController")
public class ProductController {
    /**日志对象.*/
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    /**productService对象.*/
    @Autowired
    private IProductService productService;

    /**
     * 获取所有产品信息.
     * @return 返回参数ReturnResult包含:msgCode:0失败,1成功;
     * result： List;<BR>
     * {<BR>
     *  &nbsp;msgcode：x, <BR>
     *  &nbsp;result:<BR>
     *  &nbsp;&nbsp;{ proId:xx,proName:xx,proLmt:xx,payDate:xx,interestList:xx},<BR>
     *  &nbsp;&nbsp;{ proId:xx,proName:xx,proLmt:xx,payDate:xx,interestList:xx}<BR>
     *  }
     * */
    @ResponseBody
    @RequestMapping(value = "/GetProductList",
            method = {RequestMethod.POST, RequestMethod.GET})
    public Object getProductList() {
        logger.info("ProductController GetProductList");
        return productService.getProductList();
    }

    /**
     * 获取指定产品信息.
     * @param jsonStr 接受的json字符串,{"productId":"xxxx"}
     * @return 返回参数ReturnResult包含:msgCode:0失败,1成功;
     * result： com.newland.financial.p2p.domain.entity.Product;<BR>
     * {<BR>
     *  &nbsp;msgcode：x, <BR>
     *  &nbsp;result:<BR>
     * &nbsp;&nbsp;{ proId:xx,proName:xx,proLmt:xx,payDate:xx,interestList:xx}<BR>
     *  }
     * */
    @ResponseBody
    @RequestMapping(value = "/GetProduct",
            method = {RequestMethod.POST, RequestMethod.GET})
    public Object getProduct(@RequestBody final String jsonStr) {
        logger.info("jsonStr：" + jsonStr);
        JSONObject paramJSON = JSON.parseObject(jsonStr);
        String productId = paramJSON.getString("productId");
        logger.info("ProductController GetProductList:productId--" + productId);
        IProduct product = productService.getProduct(productId);
        logger.info(product.toString());
        return product;
    }
}