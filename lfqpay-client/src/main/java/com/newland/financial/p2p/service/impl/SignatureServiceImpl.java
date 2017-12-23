package com.newland.financial.p2p.service.impl;

import com.lfq.pay.client.MpiUtil;
import com.newland.financial.p2p.service.ISignatureService;
import junit.framework.TestCase;
import lombok.extern.log4j.Log4j;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

/**
 * 验签接口.
 *
 * @author Gregory
 */
@Log4j
@Service
public class SignatureServiceImpl implements ISignatureService {

    /**
     * 验签.
     *
     * @param jsonStr 验签字段
     * @return 成功：true,失败：false
     */
    public String signature(String jsonStr) throws UnsupportedEncodingException {
        String str = URLDecoder.decode(jsonStr, "UTF-8");
        String[] s1 = str.split("&");
        Map<String, String> m = new HashMap<String, String>();
        log.info("================解码后=====================");
        for (String s2 : s1) {
            log.info(s2);
            int index = s2.indexOf("=");
            String key = s2.substring(0, index);
            String value = s2.substring(index + 1);
            m.put(key, value);
        }
        log.info("================装换成map后=====================");
        for (String key : m.keySet()) {
            log.info(key + "=" + m.get(key));
        }
        boolean checkSign = false;
        checkSign = MpiUtil.validate(m, "utf-8");
        log.info("验签结果：" + checkSign);
        TestCase.assertEquals(m.get("respCode"), "0000");
        if (checkSign) {
            return "true";
        }
        return "false";
    }
}
