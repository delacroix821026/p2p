package com.newland.financial.p2p.service.Impl;


import com.newland.financial.p2p.service.IRepayService;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Component;

@Component
@Log4j
public class RepayServiceHystrix implements IRepayService {

    public String updateRepayInfo(String jsonStr){
        return 1026+"";
    }
}
