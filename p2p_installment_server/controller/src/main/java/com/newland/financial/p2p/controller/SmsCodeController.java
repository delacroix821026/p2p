package com.newland.financial.p2p.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.newland.financial.p2p.domain.entity.CodeMsgReq;
import com.newland.financial.p2p.domain.entity.MerInfo;
import com.newland.financial.p2p.domain.entity.OrderInfo;
import com.newland.financial.p2p.service.IMerchantService;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 *短信验证码Controller.
 * @author Gregory
 */
@RestController
@Log4j
@RequestMapping("/SmsCodeController")
public class SmsCodeController {

    @Autowired
    private IMerchantService merchantService;

    /**
     *生成短信接口请求报文.
     * @param jsonStr 请求参数：<BR>
     * {<BR>
     * &nbsp;"merId":"商户代码",<BR>
     * &nbsp;"mobile":"手机号码"<BR>
     * }
     * @return 返回参数：<BR>
     * {<BR>
     * &nbsp;"version":"1.0.0",<BR>
     * &nbsp;"encoding":"编码方式",<BR>
     * &nbsp;"certId":"证书ID",<BR>
     * &nbsp;"signature":"签名",<BR>
     * &nbsp;"txnType":"交易类型:13：短信验证码",<BR>
     * &nbsp;"txnTime":"发送时间",<BR>
     * &nbsp;"merId":"商户代码",<BR>
     * &nbsp;"merPwd":"商户密码",<BR>
     * &nbsp;"merName":"商户名称",<BR>
     * &nbsp;"merAbbr":"商户简称",<BR>
     * &nbsp;"mobile":"手机号码"<BR>
     * &nbsp;}<BR>
     * }
     */
    @RequestMapping(value = "/sendSmsCode",
            method = {RequestMethod.POST, RequestMethod.GET})
    public Object sendSmsCode(@RequestBody String jsonStr){
        JSONObject jsonObject = JSON.parseObject(jsonStr);
        String merId = jsonObject.getString("merId");
        String mobile = jsonObject.getString("mobile");
        log.info(jsonStr);
        //校验商户号
        if(merId == null | "".equals(merId)){
            log.info("--------------------------商户号为空");
            return null;
        }
        //没有对应商户信息直接返回
        CodeMsgReq codeMsgReq = merchantService.getMerInfo(merId);
        if(codeMsgReq == null){
            log.info("--------------------------没有对应商户信息");
            return null;
        }
        //封装完整CodeMsgReq后返回
        codeMsgReq.setMobile(mobile);
        log.info("--------------------------codeMsgReq："+ codeMsgReq.toString());
        return codeMsgReq;
    }


}
