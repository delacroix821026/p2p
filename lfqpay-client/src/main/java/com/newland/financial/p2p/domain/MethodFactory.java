package com.newland.financial.p2p.domain;

import com.lfq.pay.client.MpiConstants;
import com.lfq.pay.client.SecureUtil;
import lombok.extern.log4j.Log4j;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Log4j
public class MethodFactory {
    /**
     * 封装创建订单请求报文参数.
     */
    public static Map<String, String> initOrderData(OrderMsgReq orm) throws IOException {
        Map<String, String> map = new HashMap<String, String>();
        // 通用信息
        map.put("version", "1.0.0"); // 固定值：1.0.0
        map.put("encoding", "utf-8"); // 编码
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
        map.put("smsCode", orm.getSmsCode()); // 手机验证码
        map.put("backUrl", "http://localhost:8080/lfq-pay/backurl.do"); // 异步通知地址

        String cvn = orm.getCvn2(); // CVN
        String name = orm.getAccName(); // 姓名
        String validDate = orm.getValidity(); // 有效期：MMYY
        String phone = orm.getAccMobile(); // 手机号码（接收交易/扣款短信）
        String idCard = orm.getAccIdcard(); // 证件号（身份证号）
        log.info("merPwd:" + orm.getMerPwd() + ",validDate:" + validDate + ",cvn:" + cvn);
        map.put("customerInfo", MethodFactory.generateCustomerInfo("01", idCard, name, phone, cvn, validDate, "utf-8")); // 身份信息
        return map;
    }

    /**
     * 持卡人身份信息.
     */
    public static String generateCustomerInfo(String type, String idCard, String name, String phone, String cvn, String validDate, String encoding) throws IOException {
        StringBuffer info = new StringBuffer("{").append(type).append(MpiConstants.COLON); // 证件类型
        info.append(idCard).append(MpiConstants.COLON);// 证件号码
        info.append(name).append(MpiConstants.COLON); // 姓名
        info.append(phone).append(MpiConstants.COLON); // 电话
        info.append(MpiConstants.COLON); // 校验码
        info.append(MpiConstants.COLON); // 密码
        info.append(cvn).append(MpiConstants.COLON); // CVN
        info.append(validDate).append("}"); // 有效期
        System.out.println("CustomerInfo拼接字符串：" + info);
        System.out.println("CustomerInfo Base64编码：" + new String(SecureUtil.base64Encode(info.toString().getBytes(encoding))));
        return new String(SecureUtil.base64Encode(info.toString().getBytes(encoding)));
    }

    /**
     * 查询订单请求报文封装.
     */
    public static Map<String, String> initQueryOrderDate(Map<String, String> m, String merPwd) {
        Map<String, String> map = new HashMap<String, String>();
        // 通用信息
        map.put("version", "1.0.0"); // 固定值：1.0.0
        map.put("encoding", "utf-8"); // 编码
        String txnTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        map.put("txnTime", txnTime); // 交易时间：yyyyMMddHHmmss
        map.put("txnType", "73"); // 查询:73
        map.put("merId", m.get("merId"));
        map.put("merPwd", SecureUtil.encryptWithDES(txnTime, merPwd));
        map.put("merName", m.get("merName"));
        map.put("merAbbr", m.get("merAbbr"));
        map.put("orderId", m.get("orderId"));
        map.put("contractsCode", m.get("contractsCode"));
        log.info("=============交易后查询================");
        for (String key : map.keySet()) {
            log.info("key= " + key + " and value= " + map.get(key));
        }
        log.info("=============交易后查询================");
        return map;

    }

    /**
     * 创建订单时需要使用的返回封装对象方法.
     *
     * @param mapA
     * @param mapB
     * @param orm
     * @return
     */
    public static OrderInfo installOrderInfo(Map<String, String> mapA, Map<String, String> mapB, OrderMsgReq orm) {
        String respCodeA = mapA.get("respCode");
        log.info("====创建订单结果====" + respCodeA);
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setOrderId(orm.getOrderId());
        orderInfo.setMerName(orm.getMerName());
        orderInfo.setMerId(orm.getMerId());
        orderInfo.setTxnAmt(Long.parseLong(orm.getTxnAmt()));
        orderInfo.setTxnterms(Integer.parseInt(orm.getTxnTerms()));
        if ("0000".equals(respCodeA)) {
            // 创建订单成功
            log.info("创建订单成功");
            String respCodeB = mapB.get("respCode");
            log.info("====查询订单结果====" + respCodeB);
            if ("0000".equals(respCodeB)) {
                //查询订单成功
                log.info("查询订单成功");
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
                String txnTime = mapA.get("txnTime");
                Date date = null;
                try {
                    date = sdf.parse(txnTime);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                orderInfo.setTxnTime(date);
                orderInfo.setContractsCode(mapA.get("contractsCode"));
                orderInfo.setAmount(Long.parseLong(mapA.get("amount")));
                orderInfo.setPoundage(Long.parseLong(mapA.get("poundage")));
                orderInfo.setContractsState(mapB.get("contractsState"));
                orderInfo.setSumTerms(Integer.parseInt(mapB.get("sumTerms")));
                orderInfo.setSumAmount(Long.parseLong(mapB.get("sumAmount")));
                orderInfo.setRemainAmount(Long.parseLong(mapB.get("remainAmount")));
                orderInfo.setCancelAmount(Long.parseLong(mapB.get("cancelAmount")));
                orderInfo.setCancelInterest(Long.parseLong(mapB.get("cancelInterest")));
                orderInfo.setRespCode(respCodeB);
                orderInfo.setRespMsg(mapB.get("respMsg"));
            } else {
                //查询订单失败
                log.info("查询订单失败");
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
                String txnTime = mapA.get("txnTime");
                Date date = null;
                try {
                    date = sdf.parse(txnTime);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                orderInfo.setTxnTime(date);
                orderInfo.setContractsCode(mapA.get("contractsCode"));
                orderInfo.setAmount(Long.parseLong(mapA.get("amount")));
                orderInfo.setPoundage(Long.parseLong(mapA.get("poundage")));
                orderInfo.setRespCode(respCodeB);
                orderInfo.setRespMsg(mapB.get("respMsg"));
            }
        } else {
            // 创建订单失败
            log.info("创建订单失败");
            orderInfo.setRespCode(respCodeA);
            orderInfo.setRespMsg(mapA.get("respMsg"));
        }
        log.info("=============交易后最终返回内容orderInfo begin================");
        log.info(orderInfo.toString());
        log.info("=============交易后最终返回内容orderInfo end================");
        return orderInfo;
    }

    /**
     * 单个订单查询封装返回对象.
     * @param map
     * @return
     */
    public static OrderInfo installOrderInfoA(Map<String, String> map) {
        OrderInfo orderInfo = new OrderInfo();
        String respCode = map.get("respCode");
        orderInfo.setOrderId(map.get("orderId"));
        orderInfo.setRespCode(map.get("respCode"));
        orderInfo.setRespMsg(map.get("respMsg"));
        orderInfo.setMerName(map.get("merName"));
        log.info("=========8:订单查询结果========：" + respCode);
        if ("0000".equals(respCode)) {
            log.info("=========9:查询成功========");
            orderInfo.setContractsState(map.get("contractsState"));
            orderInfo.setSumTerms(Integer.parseInt(map.get("sumTerms")));
            orderInfo.setSumAmount(Long.parseLong(map.get("sumAmount")));
            orderInfo.setRemainAmount(Long.parseLong(map.get("remainAmount")));
            orderInfo.setCancelAmount(Long.parseLong(map.get("cancelAmount")));
            orderInfo.setCancelInterest(Long.parseLong(map.get("cancelInterest")));
        }
        log.info("==============10:单个查询结果begin================");
        log.info(orderInfo.toString());
        log.info("==============11:单个查询结果end================");
        return orderInfo;
    }

}
