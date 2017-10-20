package com.newland.financial.p2p.service.impl;

import com.newland.financial.p2p.domain.entity.DebitAndCredit;
import lombok.extern.java.Log;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.context.annotation.Scope;

@Log
@Scope("prototype")
public class CreateExcelProcessor implements ItemProcessor<DebitAndCredit, DebitAndCredit> {
    @Override
    public DebitAndCredit process(DebitAndCredit debitAndCredit) throws Exception {
        log.info("CreateExcelProcessor:process-IsSend=====" + debitAndCredit.getDtId());
        return debitAndCredit;
    }
}
