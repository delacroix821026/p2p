package com.newland.financial.p2p.controller;

import com.newland.financial.p2p.service.ISmsCodeService;
import com.newland.financial.p2p.service.ISmsIfqService;
import com.newland.financial.p2p.util.RespMessage;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 发送短信验证码.
 *
 * @author Gregory
 */
@RestController
@Log4j
@RequestMapping("/smscode")
public class SmsCodeController {
    /**
     * 内部服务.
     */
    @Autowired
    private ISmsCodeService smsCodeService;
    /**
     * 外发接口.
     */
    @Autowired
    private ISmsIfqService smsIfqService;

    /**
     * 生成短信接口请求报文.
     * @return 返回参数：<BR>
     * {<BR>
     * &nbsp;"merId":"商户代码",<BR>
     * &nbsp;"mobile":"手机号码"<BR>
     * &nbsp;"respCode":"响应码"<BR>
     * &nbsp;"respMsg":"响应信息"<BR>
     * &nbsp;"respTime":"响应时间"<BR>
     * &nbsp;}<BR>
     * }
     */
    @RequestMapping(value = "/{merchantId}/{mobile}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Object sendSmsCode(@PathVariable(name = "merchantId") String merchantId, @PathVariable(name = "mobile") String mobile ) {
        log.info("------------------------------client-->SmsCodeController-----------------------------");
        log.info("merchantId:" + merchantId + ", mobile:" + mobile);

        Object msgCodeReqPram = smsCodeService.getMsgCodeReqPram(merchantId,mobile);
        //请求参数未通过校验或没有对应的商户信息
        if (msgCodeReqPram == null) {
            return RespMessage.setRespMap("0423", "商户不存在");
        }
        //请求乐百分短信接口
        log.info("-----------------------------------------请求乐百分短信接口：");
        return smsIfqService.backSMSCodeRequest(msgCodeReqPram);
    }

}
