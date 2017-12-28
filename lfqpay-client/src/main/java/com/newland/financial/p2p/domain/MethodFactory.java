package com.newland.financial.p2p.domain;

import com.lfq.pay.client.MpiConstants;
import com.lfq.pay.client.MpiUtil;
import com.lfq.pay.client.SecureUtil;
import com.newland.financial.p2p.common.exception.BaseRuntimeException;
import lombok.extern.log4j.Log4j;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

/**
 * 组装报文类.
 *
 * @author Mxia
 */
@Log4j
public class MethodFactory {
    /**
     * 封装创建订单请求报文参数.
     *
     * @param orm 请求报文
     * @return 封装好的请求报文
     * @throws IOException an error
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
        //map.put("backUrl", "http://localhost:8080/lfq-pay/backurl.do"); // 异步通知地址

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
     * 持卡人信息封装.
     *
     * @param type      交易类型
     * @param idCard    身份证
     * @param name      姓名
     * @param phone     电话
     * @param cvn       信用卡背面3位数
     * @param validDate 有效期
     * @param encoding  编码格式
     * @return 加签后的字符串
     * @throws IOException an error
     */
    public static String generateCustomerInfo(String type, String idCard, String name, String phone, String cvn, String validDate, String encoding) throws IOException {
        StringBuffer info = new StringBuffer("{").append(type).append(MpiConstants.COLON); // 证件类型
        info.append(idCard).append(MpiConstants.COLON); // 证件号码
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
     *
     * @param m      请求参数
     * @param merPwd 商户密码
     * @return 封装好的查询报文
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
     * @param mapA 请求信息
     * @param mapB 请求信息
     * @param orm  请求报文
     * @return 订单信息
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
            orderInfo.setStus("1");
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
            log.info("Exception:3000");
            orderInfo.setRespCode(respCodeA);
            orderInfo.setRespMsg(mapA.get("respMsg"));
            orderInfo.setStus("0");
            orderInfo.setPoundage(0l);
        }
        log.info("=============交易后最终返回内容orderInfo begin================");
        log.info(orderInfo.toString());
        log.info("=============交易后最终返回内容orderInfo end================");
        return orderInfo;
    }

    /**
     * 单个订单查询封装返回对象.
     *
     * @param map 请求信息
     * @return 订单信息
     */
    public static OrderInfo installOrderInfoA(Map<String, String> map) {
        for (String key : map.keySet()) {
            log.info("key= " + key + " and value= " + map.get(key));
        }
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
            if (map.containsKey("cancelAmount")) {
                // 存在该键说明无法订单还未还清
                orderInfo.setCancelAmount(Long.parseLong(map.get("cancelAmount")));
            } else {
                // 不存在该键说明订单已还清
                orderInfo.setCancelAmount(0L);
            }
            if (map.containsKey("cancelInterest")) {
                orderInfo.setCancelInterest(Long.parseLong(map.get("cancelInterest")));
            } else {
                orderInfo.setCancelInterest(0L);
            }
            log.info("==============单个查询结果================" + orderInfo.toString());
            return orderInfo;
        } else {
            log.info("===Exception:3000===");
            throw new BaseRuntimeException("3000", map.get("respMsg"));
        }
    }

    /**
     * 商户入网报文封装.
     *
     * @param merInfo 商户信息
     * @return 报文
     */
    public static Map<String, String> installMerchantInfo(MerInfo merInfo) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        Map<String, String> map = new HashMap<String, String>();
        map.put("name", merInfo.getMerName()); //商户名称
        map.put("registeredAddress", merInfo.getRegisteredAddress()); //注册地址
        map.put("addr", merInfo.getAddr()); //营业地址
        map.put("corporateRepresentative", merInfo.getCorporateRepresentative()); //法人代表
        map.put("lxr", merInfo.getLxr()); //联系人
        map.put("tel", merInfo.getTel()); //移动电话
        map.put("mobile", merInfo.getMobile()); //固话
        map.put("email", merInfo.getEmail()); //电子邮箱
        //map.put("fax", "传真号码"); //传真
        if (!"1".equals(merInfo.getNature())) {
            log.info("租赁到期时间：" + merInfo.getNature());
            map.put("lease", merInfo.getLease()); // 租赁到期时间，自用可以为空，格式：yyyy-MM-dd
        }
        map.put("nature", merInfo.getNature()); // 营业用地性质：1：自用；2：租用
        map.put("sitearea", String.valueOf(merInfo.getSitearea())); // 营业用地面积
        map.put("startBusiness", merInfo.getStartBusiness()); // 开业时间，格式：yyyy-MM-dd
        map.put("businessHours", String.valueOf(merInfo.getBusinessHourse())); // 营业时间
        map.put("industriesId", merInfo.getIndustriesId()); //经营产品
        map.put("industriesExplai", merInfo.getIndustriesExplai()); //经验产品说明
        map.put("bankcardTurnover", String.valueOf(merInfo.getBankcardTurnover())); // 预计月平均银行卡营业额，单位：元
        map.put("salesslipTurnover", String.valueOf(merInfo.getSalesslipTurnover())); // 预计每张签购单平均交易额，单位：元
        map.put("lfqTurnover", String.valueOf(merInfo.getLfqTurnover())); // 预计月平均乐百分营业额，单位：元
//		map.put("createDate", "2014-12-14"); // 可以为空，格式：yyyy-MM-dd
        map.put("bankName", merInfo.getBankName()); //开户行名称
        map.put("bankNum", merInfo.getBankNum()); //开户行行号
        map.put("holderName", merInfo.getHolderName()); //账户户名
        map.put("cardNum", merInfo.getCardNum()); //银行账号
        //map.put("parentName", "母公司");
        // map.put("unionpayMerchantNum", "123123");
        //map.put("remark", "remark");
        map.put("level", merInfo.getLevel()); // 证件类型：1：新营业执照；2：旧的营业执照
        return map;
    }

    /**
     * 商户接入发送请求
     *
     * @param requestUrl 请求地址
     * @param data       请求参数
     */
    public static String execute(String requestUrl, Map<String, String> data) {
        String resp = null;
        try {
            resp = MpiUtil.send(requestUrl, data, "utf-8", 60 * 1000, 60 * 1000);
            log.info("发送信息：" + data);
            log.info("返回信息：" + resp);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resp;
    }

    /**
     * 退款报文封装.
     *
     * @param re 退款信息
     * @return 报文
     */
    public static Map<String, String> installRefundReqMsg(RefundMsgReq re) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("txnType", re.getTxnType()); // 订单退款：04
        map.put("contractsCode", re.getContractsCode()); // 合同号
        String merPwd = re.getMerPwd();
        String txnTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        // 通用信息
        map.put("version", re.getVersion()); // 固定值：1.0.0
        map.put("encoding", re.getEncoding()); // 编码
        map.put("merId", re.getMerId()); // 商户编号
        map.put("merName", re.getMerName()); // 商户名称
        map.put("merAbbr", re.getMerAbbr()); // 商户简称
        // 加密传送密码
        map.put("merPwd", SecureUtil.encryptWithDES(txnTime, merPwd)); // 商户密码
        map.put("txnTime", txnTime); // 交易时间：yyyyMMddHHmmss
        return map;
    }

    /**
     * 退款请求返回报文封装.
     *
     * @param map 请求信息
     * @return 退款单
     */
    public static Refund installRefund(Map<String, String> map) {
        Refund refund = new Refund();
        String respCode = map.get("respCode");
        refund.setRespCode(respCode);
        refund.setRespMsg(map.get("respMsg"));
        if (!"0000".equals(respCode)) {
            log.info("===Exception:3000===");
            throw new BaseRuntimeException("3000", map.get("respMsg"));
        }
        //refund.setRefundId(UUID.randomUUID().toString().replaceAll("-", ""));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        // 生成退款单号
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        Random r = new Random();
        String str = new DecimalFormat("00").format(r.nextInt(100));
        Date date1 = new Date();
        StringBuffer s = new StringBuffer("T" + sdf1.format(date1));
        s.append(str);
        refund.setRefundId(new String(s));
        try {
            refund.setTxnTime(sdf.parse(map.get("txnTime")));
        } catch (ParseException e) {
            log.error("时间转换错误");
        }
        refund.setMerId(map.get("merId"));
        refund.setContractsCode(map.get("contractsCode"));
        refund.setCancelAmount(Long.parseLong(map.get("cancelAmount")));
        refund.setState(map.get("state"));
        refund.setOrderId(map.get("orderId"));
        return refund;
    }

}
