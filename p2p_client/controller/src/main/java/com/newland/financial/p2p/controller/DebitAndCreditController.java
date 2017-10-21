package com.newland.financial.p2p.controller;

import com.newland.financial.p2p.service.IDebitAndCreditService;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Log4j
@RequestMapping("/debitAndCredit")
public class DebitAndCreditController {
    @Autowired
    private IDebitAndCreditService debitAndCreditService;
    /**
     * 分页查询贷款单信息(运营维护界面).
     */
    @RequestMapping(value = "/getDebitList", method = RequestMethod.POST)
    public Object getDebitList(@RequestBody String jsonStr) {
        log.info("*****client controller****");
        return debitAndCreditService.getDebitList(jsonStr);
    }
}
