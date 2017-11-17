package com.newland.financial.p2p.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lfq.pay.client.MpiUtil;
import com.lfq.pay.client.SecureUtil;
import com.netflix.discovery.converters.Auto;
import com.newland.financial.p2p.domain.CodeMsgReq;
import com.newland.financial.p2p.service.ISmsCodeServie;
import junit.framework.TestCase;
import lombok.extern.log4j.Log4j;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
    private ISmsCodeServie iSmsCodeServie;

    /**
     *生成短信接口请求报文.
     * @param codeMsgReq 请求参数：<BR>
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
     * }
     * @return CodeMsgResp 返回参数：<BR>
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
     * &nbsp;}<BR>
     * }
     */
    @RequestMapping(value = "/backSMSCodeRequest",
            method = {RequestMethod.POST, RequestMethod.GET})
    public Object backSMSCodeRequest(@RequestBody CodeMsgReq codeMsgReq){
        log.info("-------------------------外发乐百分:"+ codeMsgReq.toString());
        return iSmsCodeServie.backSMSCodeRequest(codeMsgReq);
    }


}
