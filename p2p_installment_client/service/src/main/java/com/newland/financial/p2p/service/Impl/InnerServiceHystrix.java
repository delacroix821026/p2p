package com.newland.financial.p2p.service.Impl;

import com.newland.financial.p2p.service.IInnerService;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Component;

@Component
@Log4j
public class InnerServiceHystrix implements IInnerService {

    public Object getMsgCodeReqPram(String jsonStr) {
        return 1026;
    }

    public String updateRepayInfo(String jsonStr){
        return 1026+"";
    }
}