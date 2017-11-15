package com.newland.financial.p2p.controller;


import com.lfq.pay.client.MpiUtil;
import com.lfq.pay.client.SecureUtil;
import junit.framework.TestCase;
import lombok.extern.log4j.Log4j;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 *短信验证码Controller.
 * @author Gregory
 */
@Controller
@Log4j
@RequestMapping("/SmsCodeController")
public class SmsCodeController {
    public static final String ADDRESS_TEST = "https://tt.lfqpay.com:343";
    /**
     *生成短信接口请求报文.
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
     * &nbsp;"respCode":"响应码"<BR>
     * &nbsp;"respMsg":"响应信息"<BR>
     * &nbsp;"respTime":"响应时间"<BR>
     * &nbsp;"queryId":"请求编号"<BR>
     * &nbsp;}<BR>
     * }
     */
    @ResponseBody
    @RequestMapping(value = "/sendSms",
            method = {RequestMethod.POST, RequestMethod.GET})
    public Object backSMSCodeRequest(@RequestBody String jsonStr){
        String requestUrl = ADDRESS_TEST + "/lfq-pay/gateway/api/backSMSCodeRequest.do";

        Map<String, String> data = new HashMap<String, String>();
        String merPwd = "12345678";
        String txnTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());

        data.put("version", "1.0.0"); // 固定值：1.0.0
        data.put("encoding", "utf-8"); // 编码
        data.put("txnType", "13"); // 短信验证码：13

        data.put("merId", "GZW-001"); // 商户编号
        data.put("merName", "交易测试"); // 商户名称
        data.put("merAbbr", "交易测试"); // 商户简称
        data.put("merPwd", SecureUtil.encryptWithDES(txnTime, merPwd)); // 商户密码

        data.put("txnTime", txnTime); // 交易时间：yyyyMMddHHmmss
        data.put("mobile", "15900543650"); // 手机号码

        boolean b = MpiUtil.sign(data, "utf-8"); // 签名

        for(Map.Entry<String, String> entry : data.entrySet()){
            System.out.println(entry.getKey()+": "+entry.getValue());
        }

        String resp = MpiUtil.send(requestUrl, data, "utf-8", 60 * 1000, 60 * 1000);
        System.out.println("发送报文：" + data);
        System.out.println("返回报文：" + resp);
        ObjectMapper mapper = new ObjectMapper();
        Map<String, String> map = null;
        try {
            map = mapper.readValue(resp, Map.class);
            System.out.println("验签结果：" + MpiUtil.validate(map, "utf-8"));
            System.out.println("返回状态码：" + map.get("respCode"));
            System.out.println("返回信息：" + map.get("respMsg"));
            System.out.println("合同号：" + map.get("contractsCode"));
            TestCase.assertEquals(map.get("respCode"), "0000");
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return resp;
    }


}
