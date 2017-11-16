package com.newland.financial.p2p.service.Impl;

import com.newland.financial.p2p.service.ISmsCodeService;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Component;

@Component
@Log4j
public class SmsCodeServiceHystrix implements ISmsCodeService {

    public Object getMsgCodeReqPram(String jsonStr) {
        return 1026;
    }
}
