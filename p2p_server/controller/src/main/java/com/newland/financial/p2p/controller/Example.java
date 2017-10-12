package com.newland.financial.p2p.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.newland.financial.p2p.domain.entity.Product;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
/**
 *@author cendaijuan
 */
@RestController
@RefreshScope
@Log
public class Example {
    /***/
    @Value("${form}")
    private String form;
    /**
     *@return String
     */
    @RequestMapping("/getForm")
    String getForm() {
        return form;
    }
    /***/
    @Autowired
    private DiscoveryClient client;
    /**
     * @param a Integer
     * @param b Integer
     * @return Integer
     * */
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public Integer add(@RequestParam final Integer a, @RequestParam final Integer b) {
        ServiceInstance instance = client.getLocalServiceInstance();
        Integer r = a + b;
        log.info("/add, host:" + instance.getHost()
                + ", service_id:" + instance.getServiceId() + ", result:" + r);
        return r;
    }
    public static void main(String[] args) {
        String jsonStr = "{\"proId\": \"kv111\",\"proName\": \"星贷\",\"proLmt\": 10000,\"proInterest\": [{\"times\": 3,\"intRate\": 1.1},{\"times\": 6,\"intRate\": 1.2}],\"proNameOperator\": \"星贷aa\",\"sponsor\": \"小明公司\",\"sprProName\": \"消费贷\",\"maxLmt\": 100000,\"role\": \"1\",\"orgs\":[{\"organization\": \"005\",\"orgaName\": \"新大陆\",\"parentId\": \"001\",\"orgStus\": \"valid\"},{\"organization\": \"006\",\"orgaName\": \"旧大陆\",\"parentId\": \"001\",\"orgStus\": \"valid\"}],\"repayMhd\": \"1\",\"interestMhd\": \"1\",\"cutMhds\": [{\"cutMhd\": \"1\"},{\"cutMhd\": \"2\"}],\"advanceRepay\": \"1\",\"poundage\": \"1\",\"formula\": \"5+1=6\",\"isLatefee\": \"1\",\"latefee\": 20}";
        JSONObject paramJSON = JSON.parseObject(jsonStr);
        Product pro = paramJSON.toJavaObject(Product.class);
        System.out.println(pro.toString());
    }

}