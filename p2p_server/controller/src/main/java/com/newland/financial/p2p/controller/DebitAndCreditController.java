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
     * 请求参数：
     * {<BR>
     * &nbsp;&nbsp;"proId":"shls001",产品编号<BR>
     * &nbsp;&nbsp;"proName":"商户流水贷",产品名称<BR>
     * &nbsp;&nbsp;"oddNumbers":"201710211613069120",申请单号<BR>
     * &nbsp;&nbsp;"contractNumber":"1111111111",合同号<BR>
     * &nbsp;&nbsp;"createTimeBeg":"2017-11-02",开始时间<BR>
     * &nbsp;&nbsp;"createTimeEnd":"2017-11-02",结束时间<BR>
     * &nbsp;&nbsp;"count":3,<BR>
     * &nbsp;&nbsp;"page":1<BR>
     * }
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
