package com.newland.financial.p2p.controller;


import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *短信验证码Controller.
 * @author Gregory
 */
@Controller
@Log4j
@RequestMapping("/SmsCodeController")
public class SmsCodeController {

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
    @ResponseBody
    @RequestMapping(value = "/sendSms",
            method = {RequestMethod.POST, RequestMethod.GET})
    public Object sendSms(@RequestBody String jsonStr){


        return null;
    }

    /**
     *验签后响应前端.
     * @param jsonStr 请求参数：<BR>
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
     * &nbsp;"respCode":"响应码"<BR>
     * &nbsp;"respMsg":"响应信息"<BR>
     * &nbsp;"respTime":"响应时间"<BR>
     * &nbsp;"queryId":"请求编号"<BR>
     * }
     * @return 返回参数：<BR>
     * {<BR>
     * &nbsp;"respMsg":"响应信息",<BR>
     * &nbsp;"respCode":"响应码"<BR>
     * &nbsp;}<BR>
     * }
     */
    @ResponseBody
    @RequestMapping(value = "/sendSmsResp",
            method = {RequestMethod.POST, RequestMethod.GET})
    public Object sendSmsResp(@RequestBody String jsonStr){


        return null;
    }
}
