package com.newland.financial.p2p.controller;

import com.newland.financial.p2p.common.exception.AlreadyRepayException;
import com.newland.financial.p2p.common.exception.OverloadException;
import com.newland.financial.p2p.service.ILenderService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

/**
 * @author cendaijuan
 */
@RestController
@Log
@RequestMapping("/lender")
public class LenderController {
    @Autowired
    private ILenderService lenderService;

    @RequestMapping(value = "/GetLender", method = RequestMethod.POST)
    public Object getLender(@RequestBody final String jsonStr) {
        log.info("*****client controller****");
        return lenderService.getLender(jsonStr);
    }

    @RequestMapping(value = "/Debit", method = RequestMethod.POST)
    public Object debit(@RequestBody final String jsonStr)
            throws OverloadException {
        log.info("*****client controller****");
        lenderService.debit(jsonStr);
        return null;
    }

    @RequestMapping(value = "/Repay", method = RequestMethod.POST)
    public Object repay(@RequestBody final String jsonStr)
            throws AlreadyRepayException {
        log.info("*****client controller****");
        lenderService.repay(jsonStr);
        return null;
    }

    @RequestMapping(value = "/FindAllDebit", method = RequestMethod.POST)
    public Object findAllDebit(@RequestBody final String jsonStr) {
        log.info("*****client controller****");
        return lenderService.findAllDebit(jsonStr);
    }

    @RequestMapping(value = "/FindAllRepay", method = RequestMethod.POST)
    public Object findAllRepay(@RequestBody final String jsonStr) {
        log.info("*****client controller****");
        return lenderService.findAllRepay(jsonStr);
    }

    @RequestMapping(value = "/FindTotalMoney", method = RequestMethod.POST)
    public Object findTotalMoney(@RequestBody final String jsonStr) {
        log.info("*****client controller****");
        return lenderService.findTotalMoney(jsonStr);
    }

    @RequestMapping(value = "/getDebitAndRepaySummary", method = RequestMethod.POST)
    public Object getDebitAndRepaySummary(@RequestBody final String jsonStr) {
        log.info("*****client controller****");
        return lenderService.getDebitAndRepaySummary(jsonStr);
    }
}
