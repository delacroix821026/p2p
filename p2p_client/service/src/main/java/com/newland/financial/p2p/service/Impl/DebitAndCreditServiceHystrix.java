package com.newland.financial.p2p.service.Impl;

import com.newland.financial.p2p.service.IDebitAndCreditService;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Component;

@Component
@Log4j
public class DebitAndCreditServiceHystrix implements IDebitAndCreditService {
    public Object getDebitList(String jsonStr) {
        log.info("DebitAndCreditServiceHystrix:getDebitList");
        return 1026;
    }
}
