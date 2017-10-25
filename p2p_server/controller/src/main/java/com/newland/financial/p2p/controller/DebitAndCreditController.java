package com.newland.financial.p2p.controller;

import com.newland.financial.p2p.service.IDebitAndCreditService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 处理贷款单的Controller.
 * @author Mxia
 */
@Controller
@RequestMapping("/DebitAndCreditController")
public class DebitAndCreditController {
    /**日志对象.*/
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    /**service层对象.*/
    @Autowired
    private IDebitAndCreditService debitAndCreditService;
    /**
     *分页查询贷款单信息.
     * @param jsonStr 分页信息
     * @return 结果集
     */
    @ResponseBody
    @RequestMapping(value = "/getDebitList",
            method = {RequestMethod.POST, RequestMethod.GET})
    public Object getDebitList(@RequestBody String jsonStr) {
        logger.info("jsonStr：" + jsonStr);
        return debitAndCreditService.getDebitList(jsonStr);
    }
}
