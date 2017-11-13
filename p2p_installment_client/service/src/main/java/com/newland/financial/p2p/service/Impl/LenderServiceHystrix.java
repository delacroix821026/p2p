package com.newland.financial.p2p.service.Impl;

import com.newland.financial.p2p.service.ILenderService;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Component;

@Component
@Log4j
public class LenderServiceHystrix implements ILenderService {
    public Object applyProDebit(String jsonStr) {
        log.info("LenderServiceHystrix:applyProDebit");
        return 1026;
    }

    public Object findStagingPlan(String jsonStr) {
        log.info("LenderServiceHystrix:findStagingPlan");
        return 1026;
    }

    public Object findAllProStatus(String jsonStr) {
        log.info("LenderServiceHystrix:findAllProStatus");
        return 1026;
    }

    public Object findAllRepay(String jsonStr) {
        log.info("LenderServiceHystrix:findAllRepay");
        return 1026;
    }
}
