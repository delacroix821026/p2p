package com.newland.financial.p2p.controller;

import com.newland.financial.p2p.service.ILenderService;
import lombok.extern.log4j.Log4j;
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
@Log4j
@RequestMapping("/lender")
public class LenderController {
    @Autowired
    private ILenderService lenderService;
    /**
     * 用以申请贷款的接口（面签产品）.
     */
    @RequestMapping(value = "/applyProDebit", method = RequestMethod.POST)
    public Object applyProDebit(@RequestBody String jsonStr) {
        log.info("*****client controller****");
        return lenderService.applyProDebit(jsonStr);
    }
    /**
     * 用以查询某用户 某一款产品的分期计划.
     */
    @RequestMapping(value = "/findStagingPlan", method = RequestMethod.POST)
    public Object findStagingPlan(@RequestBody String jsonStr) {
        log.info("*****client controller****");
        return lenderService.findStagingPlan(jsonStr);
    }
    /**
     * 某用户对应所有产品的贷款状态.
     */
    @RequestMapping(value = "/findAllProStatus", method = RequestMethod.POST)
    public Object findAllProStatus(@RequestBody String jsonStr) {
        log.info("*****client controller****");
        return lenderService.findAllProStatus(jsonStr);
    }
    /**
     * 根据申请单号查询用户该单的还款信息.
     */
    @RequestMapping(value = "/findAllRepay", method = RequestMethod.POST)
    public Object findAllRepay(@RequestBody String jsonStr) {
        log.info("*****client controller****");
        return lenderService.findAllRepay(jsonStr);
    }
}
