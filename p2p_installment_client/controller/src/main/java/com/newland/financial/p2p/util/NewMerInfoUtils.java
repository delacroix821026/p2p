package com.newland.financial.p2p.util;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.newland.financial.p2p.domain.entity.MerInfo;
import com.newland.financial.p2p.domain.entity.Refund;
import lombok.extern.log4j.Log4j;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Log4j
public class NewMerInfoUtils {
    public static MerInfo getNewMerInfo(MerInfo merInfo, JSONObject jsonStr) {
        MerInfo mer = merInfo;
        try {
            mer.setMerAbbr(URLDecoder.decode(jsonStr.getString("merAbbr"), "UTF-8"));
            mer.setRegisteredAddress(URLDecoder.decode(jsonStr.getString("registeredAddress"), "UTF-8"));
            mer.setCorporateRepresentative(URLDecoder.decode(jsonStr.getString("corporateRepresentative"), "UTF-8"));
            mer.setLxr(URLDecoder.decode(jsonStr.getString("lxr"), "UTF-8"));
            mer.setMobile(jsonStr.getString("mobile"));
            mer.setBusinessHourse(jsonStr.getInteger("businessHours"));
            mer.setBankName(URLDecoder.decode(jsonStr.getString("bankName"), "UTF-8"));
            mer.setBankNum(jsonStr.getString("bankNum"));
            mer.setHolderName(URLDecoder.decode(jsonStr.getString("holderName"), "UTF-8"));
            mer.setCardNum(jsonStr.getString("cardNum"));
            mer.setLevel(jsonStr.getString("level"));
        } catch (UnsupportedEncodingException e) {
            log.error("编码格式错误");
        }
        log.info("组装后的merinfo：" + mer.toString());
        return mer;
    }

    public static Refund getNewRefund(String jsonStr) {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, String> map = null;
        try {
            map = mapper.readValue(jsonStr, Map.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Refund refund = new Refund();
        String respCode = map.get("respCode");
        refund.setRespCode(respCode);
        refund.setRespMsg(map.get("respMsg"));
        if (!"0000".equals(respCode)) {
            return refund;
        }
        if (map.get("cancelAmount") != null) {
            refund.setCancelAmount(Long.parseLong(map.get("cancelAmount")));
        }
        refund.setState(map.get("state"));
        refund.setOrderId(map.get("orderId"));
        return refund;
    }

    public static String castMapToString(String jsonStr) {
        String str = null;
        try {
            str = URLDecoder.decode(jsonStr, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String[] s1 = str.split("&");
        StringBuffer sbf = new StringBuffer();
        sbf.append("{");
        for (int i = 0; i < s1.length; i++) {
            String s2 = s1[i];
            int index = s2.indexOf("=");
            String key = s2.substring(0, index);
            String value = s2.substring(index + 1);
            if (i == s1.length - 1) {
                sbf.append("\"" + key  + "\":\"" + value + "\"");
            } else {
                sbf.append("\"" + key  + "\":\"" + value + "\",");
            }

        }
        sbf.append("}");
        return sbf.toString();
    }
}
