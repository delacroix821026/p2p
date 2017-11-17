package com.newland.financial.p2p.domain;

import com.lfq.pay.client.MpiConstants;
import com.lfq.pay.client.SecureUtil;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class MethodFactory {
    /**
     * 公共参数
     */
    public static   Map<String, String> initCommonData() throws IOException {
        Map<String, String> map = new HashMap<String, String>();
        // 通用信息
        map.put("version", "1.0.0"); // 固定值：1.0.0
        map.put("encoding", "utf-8"); // 编码
        return map;
    }

    /**
     * 持卡人身份信息
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
}
