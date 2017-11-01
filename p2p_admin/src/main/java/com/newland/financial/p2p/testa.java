package com.newland.financial.p2p;

import java.util.Iterator;
import java.util.Map;

public class testa {
    public static void main(String[] args) {
        Map<String, String> map = System.getenv();
        for(Iterator<String> itr = map.keySet().iterator(); itr.hasNext();){
            String key = itr.next();
            System.out.println(key + "=" + map.get(key));
        }
    }
}
