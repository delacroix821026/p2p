package com.newland.financial.p2p.controller;

import com.newland.financial.p2p.common.exception.BaseRuntimeException;
import com.newland.financial.p2p.service.ISmsCodeService;
import com.newland.financial.p2p.service.ISmsIfqService;
import com.newland.financial.p2p.service.Impl.SmsIfqServiceFallBackFactory;
import feign.Client;
import feign.Contract;
import feign.Request;
import feign.Retryer;
import feign.codec.Decoder;
import feign.codec.Encoder;
import feign.codec.ErrorDecoder;
import feign.hystrix.FallbackFactory;
import feign.hystrix.HystrixFeign;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


/**
 * 发送短信验证码.
 *
 * @author Gregory
 */
@RestController
@Log4j
@RequestMapping("/smscode")
public class SmsCodeController {
    @Autowired
    public SmsCodeController(ErrorDecoder errorDecoder, Decoder decoder, Encoder encoder, Client client, Contract contract, @Value("${DEVLOPER_NAME}") String devlopName) {

        smsIfqService = HystrixFeign.builder()
                .encoder(encoder)
                .decoder(decoder)
                .contract(contract)
                .client(client)
                .options(new Request.Options(13 * 1000, 5 * 1000))
                .retryer(new Retryer.Default(100, 1000, 1))
                .errorDecoder(errorDecoder)
                .target(ISmsIfqService.class, "http://lfqpay-client" + devlopName, (FallbackFactory<? extends ISmsIfqService>) new SmsIfqServiceFallBackFactory());
    }
    /**
     * 内部服务.
     */
    @Autowired
    private ISmsCodeService smsCodeService;
    /**
     * 外发接口.
     */
    /*@Autowired*/
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
            log.info("===Exception:0423===");
            throw new BaseRuntimeException("0423");
        }
        //请求乐百分短信接口
        log.info("-----------------------------------------请求乐百分短信接口：");
        return smsIfqService.backSMSCodeRequest(msgCodeReqPram);
    }

}
