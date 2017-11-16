package com.newland.financial.p2p.controller;

import com.newland.financial.p2p.service.ISmsCodeService;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.HashMap;
import java.util.Map;

/**
 *发送短信验证码.
 * @author Gregory
 */
@Controller
@Log4j
@RequestMapping("/SmsCodeClient")
public class SmsCodeController {

    private ISmsCodeService smsCodeService;

    /**
     * 短信验证码接口.
     *
     * @param jsonStr
     * @return
     */
    @RequestMapping(value = "/smsCode", method = RequestMethod.POST)
    public Object sendMsg(@RequestBody String jsonStr) {
        log.debug("------------------------------smsCode----------------------------");
        log.debug("jsonStr：" + jsonStr);

        Object msgCodeReqPram = smsCodeService.getMsgCodeReqPram(jsonStr);
        //请求参数未通过校验或没有对应的商户信息
        Map<String,Object> resp = new HashMap<String,Object>();
        if(msgCodeReqPram == null){
            resp.put("respCode","0220");
            resp.put("respMsg","商户不存在");
            return resp;
        }
        //请求乐百分短信接口
        log.debug("-----------------------------------------请求乐百分短信接口：");
        return null;
    }
}
