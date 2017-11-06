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
     * @param jsonStr 分页信息<BR>
     * 请求参数：<BR>
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
     * @return 结果集<BR>
     * {<BR>
     *&nbsp;&nbsp;"pageNum": 1,<BR>
     *&nbsp;&nbsp;"pageSize": 3,<BR>
     *&nbsp;&nbsp;"total": 1,<BR>
     *&nbsp;&nbsp;"pages": 1,<BR>
     *&nbsp;&nbsp;"list": [<BR>
     *&nbsp;&nbsp;&nbsp;&nbsp;{<BR>
     *&nbsp;&nbsp;&nbsp;&nbsp;"dtId": "ed3127920e2f4208acf66c05b21908fa",贷款单编号<BR>
     *&nbsp;&nbsp;&nbsp;&nbsp;"lastRePayDate": null,最后还款日期<BR>
     *&nbsp;&nbsp;&nbsp;&nbsp;"stus": "1",状态：0已结清，1申请中，2还款计划，3拒绝<BR>
     *&nbsp;&nbsp;&nbsp;&nbsp;"loanDate": null,放款日期<BR>
     *&nbsp;&nbsp;&nbsp;&nbsp;"loanMoney": null,放款金额<BR>
     *&nbsp;&nbsp;&nbsp;&nbsp;"totleInterest": null,总利息<BR>
     *&nbsp;&nbsp;&nbsp;&nbsp;"serviceCharge": null,总手续费<BR>
     *&nbsp;&nbsp;&nbsp;&nbsp;"lineOfCredit": null,授信额度<BR>
     *&nbsp;&nbsp;&nbsp;&nbsp;"merchantNum": null,绑定的商户号<BR>
     *&nbsp;&nbsp;&nbsp;&nbsp;"applyName": "zhuhongyang",贷款人姓名<BR>
     *&nbsp;&nbsp;&nbsp;&nbsp;"identityCard": "340521198902104612",身份证<BR>
     *&nbsp;&nbsp;&nbsp;&nbsp;"phone": "15021327865",电话<BR>
     *&nbsp;&nbsp;&nbsp;&nbsp;"province": "上海",省<BR>
     *&nbsp;&nbsp;&nbsp;&nbsp;"city": "上海",市<BR>
     *&nbsp;&nbsp;&nbsp;&nbsp;"region": "浦东",区或县<BR>
     *&nbsp;&nbsp;&nbsp;&nbsp;"detailAdd": "世博大道",详细地址<BR>
     *&nbsp;&nbsp;&nbsp;&nbsp;"oddNumbers": "201710211613069120",申请单号<BR>
     *&nbsp;&nbsp;&nbsp;&nbsp;"starAccount": "15021327865",星管家绑定账号<BR>
     *&nbsp;&nbsp;&nbsp;&nbsp;"contractNumber": "1111111111",合同号<BR>
     *&nbsp;&nbsp;&nbsp;&nbsp;"totleNeedRepay": null,总应还金额<BR>
     *&nbsp;&nbsp;&nbsp;&nbsp;"yetRepay": null,已还总额<BR>
     *&nbsp;&nbsp;&nbsp;&nbsp;"needRepay": null,待还总额<BR>
     *&nbsp;&nbsp;&nbsp;&nbsp;"repayedCorpus": null,已还本金<BR>
     *&nbsp;&nbsp;&nbsp;&nbsp;"repayedInterest": null,已还利息<BR>
     *&nbsp;&nbsp;&nbsp;&nbsp;"unpayCorpus": null,待还本金<BR>
     *&nbsp;&nbsp;&nbsp;&nbsp;"unpayInterest": null,待还利息<BR>
     *&nbsp;&nbsp;&nbsp;&nbsp;"feeOverdue": null,应还滞纳金<BR>
     *&nbsp;&nbsp;&nbsp;&nbsp;"repayedOverdue": null,已还滞纳金<BR>
     *&nbsp;&nbsp;&nbsp;&nbsp;"repayedSerFee": null,已还手续费<BR>
     *&nbsp;&nbsp;&nbsp;&nbsp;"dlnrId": "zhu123",用户编号<BR>
     *&nbsp;&nbsp;&nbsp;&nbsp;"dproName": "商户流水贷",产品名称<BR>
     *&nbsp;&nbsp;&nbsp;&nbsp;"dmoney": 200000,申请金额<BR>
     *&nbsp;&nbsp;&nbsp;&nbsp;"dproId": "shls001",产品编号<BR>
     *&nbsp;&nbsp;&nbsp;&nbsp;"ddate": 1508573587000,申请日期<BR>
     *&nbsp;&nbsp;&nbsp;&nbsp;"dtimes": null,分期数<BR>
     *&nbsp;&nbsp;&nbsp;&nbsp;"dittRa": null,月利率<BR>
     *&nbsp;&nbsp;&nbsp;&nbsp;}<BR>
     *&nbsp;&nbsp;],<BR>
     *}
     */
    @ResponseBody
    @RequestMapping(value = "/getDebitList",
            method = {RequestMethod.POST, RequestMethod.GET})
    public Object getDebitList(@RequestBody String jsonStr) {
        logger.info("jsonStr：" + jsonStr);
        return debitAndCreditService.getDebitList(jsonStr);
    }
}
