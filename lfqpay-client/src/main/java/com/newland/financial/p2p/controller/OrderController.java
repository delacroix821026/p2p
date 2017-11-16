package com.newland.financial.p2p.controller;

import com.lfq.pay.client.MpiUtil;
import com.lfq.pay.client.SecureUtil;
import com.newland.financial.p2p.domain.MethodFactory;
import com.newland.financial.p2p.domain.OrderMsgReq;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static com.newland.financial.p2p.Utils.IfqUtil.execute;

/**
 * 订单处理Controller.
 *
 * @author Mxia
 */

@Controller
@Log4j
@RequestMapping("/ybforder")
public class OrderController {
    // 外网测试地址
    public static final String ADDRESS_TEST = "https://tt.lfqpay.com:343";
    // 本地开发地址
    public static final String ADDRESS_DEVELOP = "https://tt.lfqpay.com:343";

    @ResponseBody
    @RequestMapping(value = "/sendOrderMsg", method = {RequestMethod.POST, RequestMethod.GET})
    public Object queryOrderInfo(@RequestBody OrderMsgReq ob) throws IOException {
        OrderMsgReq orm = ob;
        String requestUrl = ADDRESS_DEVELOP + "/lfq-pay/gateway/api/backTransRequest.do";
        //设置公共参数.
        Map<String, String> map = MethodFactory.initCommonData();
        String txnTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        map.put("txnTime", txnTime); // 交易时间：yyyyMMddHHmmss
        // 加密传送密码
        map.put("merPwd", SecureUtil.encryptWithDES(txnTime, orm.getMerPwd())); // 商户密码
        map.put("merId", orm.getMerId()); // 商户编号
        map.put("merName", orm.getMerName()); // 商户名称
        map.put("merAbbr", orm.getMerAbbr()); // 商户简称
        map.put("txnType", "01"); // 交易：01
        map.put("txnTerms", orm.getTxnTerms()); // 期数
        map.put("txnAmt", orm.getTxnAmt()); // 订单总金额，单位：分
        map.put("accNo", orm.getAccNo()); // 卡号
        map.put("orderId", orm.getOrderId()); // 订单号
        map.put("backUrl", "http://localhost:8080/lfq-pay/backurl.do"); // 异步通知地址

        String cvn = orm.getCvn2(); // CVN
        String name = orm.getAccName(); // 姓名
        String validDate = orm.getValidity(); // 有效期：MMYY
        String phone = orm.getAccMobile(); // 手机号码（接收交易/扣款短信）
        String idCard = orm.getAccIdcard(); // 证件号（身份证号）
        map.put("customerInfo", MethodFactory.generateCustomerInfo("01", idCard, name, phone, cvn, validDate, "utf-8")); // 身份信息
        MpiUtil.sign(map, "utf-8"); // 签名

        execute(requestUrl, map);
        return null;
    }


}
