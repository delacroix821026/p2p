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
    public String signature(String jsonStr) {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, String> map = null;
        boolean checkSign = false;
        try {
            map = mapper.readValue(jsonStr, Map.class);
            checkSign = MpiUtil.validate(map, "utf-8");
            log.info("验签结果：" + checkSign);
            TestCase.assertEquals(map.get("respCode"), "0000");
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (checkSign) {
            return "true";
        }
        return "false";
    }
}
