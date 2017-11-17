package com.newland.financial.p2p.service.Impl;

import com.newland.financial.p2p.service.IEgwService;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Component;

@Component
@Log4j
public class EgwServiceHystrix implements IEgwService {

    public Object backSMSCodeRequest(Object jsonStr) {
        return 1026;
    }

    public String signature(String jsonStr){
        return 1026+"";
    }
}
