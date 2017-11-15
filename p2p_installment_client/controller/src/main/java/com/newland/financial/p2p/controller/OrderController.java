package com.newland.financial.p2p.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.newland.financial.p2p.service.IOrderService;
import com.newland.financial.p2p.service.ISendService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Gregory
 */
@RestController
@Log
@RequestMapping("/Order")
public class OrderController {
    @Autowired
    private IOrderService orderService;
    @Autowired
    private ISendService sendService;

    /**
     * 短信验证码接口.
     *
     * @param jsonStr
     * @return
     */
    @RequestMapping(value = "/sendmsg", method = RequestMethod.POST)
    public Object sendMsg(@RequestBody String jsonStr) {
        log.info("========send msg=======");
        log.info("jsonStr：" + jsonStr);
        String jsonStr1 = (String) orderService.getValidateInfo(jsonStr);
        String jsonStr2 = (String) sendService.sendMsgToLbf(jsonStr1);
        String jsonStr3 = (String) orderService.getRespInfo(jsonStr);
        return null;
    }

}
