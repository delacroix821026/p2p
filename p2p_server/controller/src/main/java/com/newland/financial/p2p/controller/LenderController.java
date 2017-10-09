package com.newland.financial.p2p.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.newland.financial.p2p.common.exception.AlreadyRepayException;
import com.newland.financial.p2p.domain.entity.Lender;
import com.newland.financial.p2p.service.IDebitAndCreditService;
import com.newland.financial.p2p.service.ILenderService;
import com.newland.financial.p2p.common.exception.OverloadException;
import com.newland.financial.p2p.service.IRepayALoanService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;

/**
 * @author cendaijuan
 * */
@Controller
@RequestMapping("/LenderController")
public class LenderController {
    /**日志对象.*/
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    /**lenderService对象.*/
    @Autowired
    private ILenderService lenderService;
    /**debitAndCreditService对象.*/
    @Autowired
    private IDebitAndCreditService debitAndCreditService;
    /**repayALoanService对象.*/
    @Autowired
    private IRepayALoanService repayALoanService;

    /**
     * 根据传入的用户id查询用户信息.
     * @return 返回参数ReturnResult包含:msgCode:0失败,1成功;
     * result： com.newland.financial.p2p.common.entity.Lender<BR>
     * {<BR>
     *  &nbsp;msgcode：x, <BR>
     *  &nbsp;result:<BR>
     *  &nbsp;&nbsp;{ userId:xx,loId:xx,lenderName:xx,phone:xx,
     *  identityCard:xx,totleLmt:xx,currentLmt:xx,
     *  repay:xx,inAct:xx,inBank:xx,outAct:xx,outBank:xx,userName:xx,
     *  industry:xx,companyAddress:xx,registTime:xx}<BR>
     *  }
     * @param jsonStr 接受的json字符串,{"lenderId":"xxxx"}
     * */
    @ResponseBody
    @RequestMapping(value = "/GetLender",
            method = {RequestMethod.POST, RequestMethod.GET})
    public Object getLender(@RequestBody String jsonStr) {
        logger.info("jsonStr：" + jsonStr);
        JSONObject paramJSON = JSON.parseObject(jsonStr);
        String lenderId = paramJSON.getString("lenderId");
        logger.info("LenderController GetLender:lenderId--" + lenderId);
        Lender lender = lenderService.getLender(lenderId);
        logger.info(lender.toString());
        return lender;
    }

    /**
     * 进行贷款.
     * @return 返回参数ReturnResult包含:msgCode:0失败,1成功;
     * result： null;
     * @param jsonStr 接受的json字符串中包含{"userId":"xx","productId":xx,"money":xx,"interestId":xx}
     * @throws  OverloadException 超过额度.
     * */
    @ResponseBody
    @RequestMapping(value = "/Debit",
            method = {RequestMethod.POST, RequestMethod.GET})
    public Object debit(@RequestBody String jsonStr)
            throws OverloadException {
        logger.info("jsonStr：" + jsonStr);
        JSONObject paramJSON = JSON.parseObject(jsonStr);
        String userId = paramJSON.getString("userId");
        String productId = paramJSON.getString("productId");
        BigDecimal money = paramJSON.getBigDecimal("money");
        String interestId = paramJSON.getString("interestId");
        logger.info("LenderController GetLender:userId--" + userId);
        logger.info("LenderController GetLender:productId--" + productId);
        logger.info("LenderController GetLender:productId--" + money);
        logger.info("LenderController GetLender:productId--" + interestId);

        debitAndCreditService.createDebitAndCredit(
                userId, productId, money, interestId);

        return null;
    }

    /**
     * 进行还款.
     * @param jsonStr 接受的json字符串,包含{"repayId":"xxxx"}
     * @return 返回参数ReturnResult包含:msgCode:0失败,1成功;
     * result： null;
     * @throws  AlreadyRepayException 重复还款异常
     * */
    @ResponseBody
    @RequestMapping(value = "/Repay",
            method = {RequestMethod.POST, RequestMethod.GET})
    public Object repay(@RequestBody String jsonStr)
            throws AlreadyRepayException {
        logger.info("jsonStr：" + jsonStr);
        JSONObject paramJSON = JSON.parseObject(jsonStr);
        String repayId = paramJSON.getString("repayId");

        logger.info("LenderController Repay:repayId--" + repayId);

        repayALoanService.repay(repayId); //按照传入的还款单编号进行还款

        return null;
    }

    /**
     * 查询用户的贷款信息.
     * @param jsonStr 接受的json字符串,{"userId":"xxxx"}
     * @return 返回参数ReturnResult包含:msgCode:0失败,1成功;
     * result： List<BR>
     * {<BR>
     *  &nbsp;msgcode：x, <BR>
     *  &nbsp;result:<BR>
     *  &nbsp;&nbsp;{ positionExchange:xx,dtId:xx,unPay:xx,yetPay:xx,
     *  lastRePayDate:xx,dittId:xx,dittRa:xx,dproId:xx,dtimes:xx,daft:xx,
     *  dlnrId:xx,dbef:xx,dproName:xx,dmoney:xx,dproLmt:xx,ddate:xx},<BR>
     *  &nbsp;&nbsp;{ positionExchange:xx,dtId:xx,unPay:xx,yetPay:xx,lastRePayDate:xx,
     *  dittId:xx,dittRa:xx,dproId:xx,dtimes:xx,daft:xx,dlnrId:xx,
     *  dbef:xx,dproName:xx,dmoney:xx,dproLmt:xx,ddate:xx}...<BR>
     *  }
     * */
    @ResponseBody
    @RequestMapping(value = "/FindAllDebit",
            method = {RequestMethod.POST, RequestMethod.GET})
    public Object findAllDebit(@RequestBody String jsonStr) {
        logger.info("jsonStr：" + jsonStr);
        JSONObject paramJSON = JSON.parseObject(jsonStr);
        String userId = paramJSON.getString("userId");
        logger.info("LenderController FindAllDebit:userId--" + userId);
        return debitAndCreditService.findDebitAndCreditHistory(userId);
    }

    /**
     * 查询用户的还款信息.
     * @param jsonStr 接受的json字符串,{"userId":"xxxx"}
     * @return 返回参数ReturnResult包含:msgCode:0失败,1成功;
     * result： List<BR>
     * {<BR>
     *  &nbsp;msgcode：x, <BR>
     *  &nbsp;result:<BR>
     *  &nbsp;&nbsp;{ positionExchange:xx,reId:xx,reLndId:xx,reMtd:xx,reProId:xx,
     *  reProNmae:xx,reProLmt:xx,crtRe:xx,createDate:xx,expireDate:xx,reBef:xx,
     *  reAft:xx,status:xx,crtInterest:xx,serMoney:xx,otherMoney:xx,totleMoney:xx,debitId:xx}<BR>
     *  &nbsp;&nbsp;{ positionExchange:xx,reId:xx,reLndId:xx,reMtd:xx,reProId:xx,reProNmae:xx,
     *  reProLmt:xx,crtRe:xx,createDate:xx,expireDate:xx,reBef:xx,reAft:xx,status:xx,
     *  crtInterest:xx,serMoney:xx,otherMoney:xx,totleMoney:xx,debitId:xx}...<BR>
     * }
     * */
    @ResponseBody
    @RequestMapping(value = "/FindAllRepay",
            method = {RequestMethod.POST, RequestMethod.GET})
    public Object findAllRepay(@RequestBody String jsonStr) {
        logger.info("jsonStr：" + jsonStr);
        JSONObject paramJSON = JSON.parseObject(jsonStr);
        String userId = paramJSON.getString("userId");
        logger.info("LenderController FindAllRepay:userId--" + userId);

        return repayALoanService.getRepayALoanList(userId);
    }

    /**
     * 当月所需还款金额.
     * @param jsonStr 接受的json字符串,{"userId":"xxxx"}
     * @return 返回参数ReturnResult包含:msgCode:0失败,1成功;
     * result： Integer;
     * */
    @ResponseBody
    @RequestMapping(value = "/FindTotalMoney",
            method = {RequestMethod.POST, RequestMethod.GET})
    public Object findTotalMoney(@RequestBody String jsonStr) {
        logger.info("jsonStr：" + jsonStr);
        JSONObject paramJSON = JSON.parseObject(jsonStr);
        String userId = paramJSON.getString("userId");
        logger.info("LenderController FindTotalMoney:userId--" + userId);

        return repayALoanService.getTotalMoney(userId);
    }

    /**
     * 查询已还金额，未还金额，本月应还金额.
     * @param jsonStr 接受的json字符串,{"userId":"xxxx"}
     * @return 返回参数ReturnResult包含:msgCode:0失败,1成功;
     * result： com.newland.financial.p2p.domain.entity.DebitAndRepaySummary;<BR>
     * {<BR>
     *  &nbsp;msgcode：x, <BR>
     *  &nbsp;result:<BR>
     *  &nbsp;&nbsp;{ needRepay:xx,repayed:xx,repayInMonth:xx}<BR>
     *  }
     * */
    @ResponseBody
    @RequestMapping(value = "/getDebitAndRepaySummary",
            method = {RequestMethod.POST, RequestMethod.GET})
    public Object getDebitAndRepaySummary(@RequestBody String jsonStr) {
        logger.info("jsonStr：" + jsonStr);
        JSONObject paramJSON = JSON.parseObject(jsonStr);
        String userId = paramJSON.getString("userId");
        logger.info("LenderController FindTotalMoney:userId--" + userId);

        return repayALoanService.getDebitAndRepaySummary(userId);
    }

    /**
     * 测试时用于清楚数据，不传lenderId默认清楚全部
     * */
    /*@ResponseBody
    @RequestMapping(value = "/Clear",
    method = {RequestMethod.POST, RequestMethod.GET})
    public void clearData(@RequestBody String jsonStr) {
        logger.info("jsonStr：" + jsonStr);
        JSONObject paramJSON = JSON.parseObject(jsonStr);
        String lenderId = paramJSON.getString("lenderId");
        logger.info("LenderController Clear:lenderId--" + lenderId);
        lenderService.clear(lenderId);
    }*/

    /**
     * 测试用于改变还款单status数值.
     * @param jsonStr String包含userId用户编号
     * @return Object
     * */
    @ResponseBody
    @RequestMapping(value = "/UpdateStatus",
            method = {RequestMethod.POST, RequestMethod.GET})
    public Object clearData(@RequestBody String jsonStr) {
        logger.info("jsonStr：" + jsonStr);
        JSONObject paramJSON = JSON.parseObject(jsonStr);
        String userId = paramJSON.getString("userId");
        logger.info("LenderController Clear:lenderId--" + userId);
        repayALoanService.updateSta(userId);
        return "Status changed";
    }

}
