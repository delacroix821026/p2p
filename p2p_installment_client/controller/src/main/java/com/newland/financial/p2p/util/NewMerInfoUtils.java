package com.newland.financial.p2p.util;

import com.alibaba.fastjson.JSONObject;
import com.newland.financial.p2p.domain.entity.MerInfo;
import lombok.extern.log4j.Log4j;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
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
}
