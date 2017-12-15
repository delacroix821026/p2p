package com.newland.financial.p2p.util;


import java.util.HashMap;
import java.util.Map;

public class RespMessage {

    public static Map<String, String> setRespMap(String respCode, String respMsg) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("respCode", respCode);
        map.put("respMsg", respMsg);
        return map;
    }
}
