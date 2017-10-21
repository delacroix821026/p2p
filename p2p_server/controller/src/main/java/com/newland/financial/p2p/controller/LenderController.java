package com.newland.financial.p2p.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.newland.financial.p2p.common.exception.AgeDiscrepancyException;
import com.newland.financial.p2p.common.exception.AlreadyRepayException;
import com.newland.financial.p2p.domain.entity.CustomerFlowDebit;
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
 */
@Controller
@RequestMapping("/LenderController")
public class LenderController {
    /**
     * 日志对象.
     */
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    /**
     * lenderService对象.
     */
    @Autowired
    private ILenderService lenderService;
    /**
     * debitAndCreditService对象.
     */
    @Autowired
    private IDebitAndCreditService debitAndCreditService;
    /**
     * repayALoanService对象.
     */
    @Autowired
    private IRepayALoanService repayALoanService;

    /**
     * 根据传入的用户id查询用户信息.
     *
     * @param jsonStr 接受的json字符串,{"lenderId":"xxxx"}
     * @return 返回参数ReturnResult包含:msgCode:0失败,1成功;
     * result： com.newland.financial.p2p.common.entity.Lender<BR>
     * {<BR>
     * &nbsp;msgcode：x, <BR>
     * &nbsp;result:<BR>
     * &nbsp;&nbsp;{ userId:xx,loId:xx,lenderName:xx,phone:xx,
     * identityCard:xx,totleLmt:xx,currentLmt:xx,
     * repay:xx,inAct:xx,inBank:xx,outAct:xx,outBank:xx,userName:xx,
     * industry:xx,companyAddress:xx,registTime:xx}<BR>
     * }
     */
    @ResponseBody
    @RequestMapping(value = "/GetLender",
            method = {RequestMethod.POST, RequestMethod.GET})
    public Object getLender(@RequestBody final String jsonStr) {
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
     *
     * @param jsonStr 接受的json字符串中包含{"userId":"xx","productId":xx,"money":xx,"interestId":xx}
     * @return 返回参数ReturnResult包含:msgCode:0失败,1成功;
     * result： null;
     * @throws OverloadException 超过额度.
     */
    @ResponseBody
    @RequestMapping(value = "/Debit",
            method = {RequestMethod.POST, RequestMethod.GET})
    public Object debit(@RequestBody final String jsonStr)
            throws OverloadException {
        logger.info("jsonStr：" + jsonStr);
        JSONObject paramJSON = JSON.parseObject(jsonStr);
        String userId = paramJSON.getString("userId");
        String productId = paramJSON.getString("productId");
        BigDecimal money = paramJSON.getBigDecimal("money");
        Integer interestId = Integer.parseInt(paramJSON.getString("interestId"));
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
     *
     * @param jsonStr 接受的json字符串,包含{"repayId":"xxxx"}
     * @return 返回参数ReturnResult包含:msgCode:0失败,1成功;
     * result： null;
     * @throws AlreadyRepayException 重复还款异常
     */
    @ResponseBody
    @RequestMapping(value = "/Repay",
            method = {RequestMethod.POST, RequestMethod.GET})
    public Object repay(@RequestBody final String jsonStr)
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
     *
     * @param jsonStr 接受的json字符串,{"userId":"xxxx"}
     * @return 返回参数ReturnResult包含:msgCode:0失败,1成功;
     * result： List<BR>
     * {<BR>
     * &nbsp;msgcode：x, <BR>
     * &nbsp;result:<BR>
     * &nbsp;&nbsp;{ positionExchange:xx,dtId:xx,unPay:xx,yetPay:xx,
     * lastRePayDate:xx,dittId:xx,dittRa:xx,dproId:xx,dtimes:xx,daft:xx,
     * dlnrId:xx,dbef:xx,dproName:xx,dmoney:xx,dproLmt:xx,ddate:xx},<BR>
     * &nbsp;&nbsp;{ positionExchange:xx,dtId:xx,unPay:xx,yetPay:xx,lastRePayDate:xx,
     * dittId:xx,dittRa:xx,dproId:xx,dtimes:xx,daft:xx,dlnrId:xx,
     * dbef:xx,dproName:xx,dmoney:xx,dproLmt:xx,ddate:xx}...<BR>
     * }
     */
    @ResponseBody
    @RequestMapping(value = "/FindAllDebit",
            method = {RequestMethod.POST, RequestMethod.GET})
    public Object findAllDebit(@RequestBody final String jsonStr) {
        logger.info("jsonStr：" + jsonStr);
        JSONObject paramJSON = JSON.parseObject(jsonStr);
        String userId = paramJSON.getString("userId");
        logger.info("LenderController FindAllDebit:userId--" + userId);
        return debitAndCreditService.findDebitAndCreditHistory(userId);
    }


    /**
     * 当月所需还款金额.
     *
     * @param jsonStr 接受的json字符串,{"userId":"xxxx"}
     * @return 返回参数ReturnResult包含:msgCode:0失败,1成功;
     * result： Integer;
     */
    @ResponseBody
    @RequestMapping(value = "/FindTotalMoney",
            method = {RequestMethod.POST, RequestMethod.GET})
    public Object findTotalMoney(@RequestBody final String jsonStr) {
        logger.info("jsonStr：" + jsonStr);
        JSONObject paramJSON = JSON.parseObject(jsonStr);
        String userId = paramJSON.getString("userId");
        logger.info("LenderController FindTotalMoney:userId--" + userId);

        return repayALoanService.getTotalMoney(userId);
    }

    /**
     * 查询已还金额，未还金额，本月应还金额.
     *
     * @param jsonStr 接受的json字符串,{"userId":"xxxx"}
     * @return 返回参数ReturnResult包含:msgCode:0失败,1成功;
     * result： com.newland.financial.p2p.domain.entity.DebitAndRepaySummary;<BR>
     * {<BR>
     * &nbsp;msgcode：x, <BR>
     * &nbsp;result:<BR>
     * &nbsp;&nbsp;{ needRepay:xx,repayed:xx,repayInMonth:xx}<BR>
     * }
     */
    @ResponseBody
    @RequestMapping(value = "/getDebitAndRepaySummary",
            method = {RequestMethod.POST, RequestMethod.GET})
    public Object getDebitAndRepaySummary(@RequestBody final String jsonStr) {
        logger.info("jsonStr：" + jsonStr);
        JSONObject paramJSON = JSON.parseObject(jsonStr);
        String userId = paramJSON.getString("userId");
        logger.info("LenderController FindTotalMoney:userId--" + userId);

        return repayALoanService.getDebitAndRepaySummary(userId);
    }


    /**
     * 测试用于改变还款单status数值.
     *
     * @param jsonStr String包含userId用户编号
     * @return Object
     */
    @ResponseBody
    @RequestMapping(value = "/UpdateStatus",
            method = {RequestMethod.POST, RequestMethod.GET})
    public Object clearData(@RequestBody final String jsonStr) {
        logger.info("jsonStr：" + jsonStr);
        JSONObject paramJSON = JSON.parseObject(jsonStr);
        String userId = paramJSON.getString("userId");
        logger.info("LenderController Clear:lenderId--" + userId);
        try {
            repayALoanService.updateSta(userId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Status changed";
    }
    /*面签接口*/

    /**
     * 申请面签产品.
     * @param jsonStr 申请产品包含的信息
     * @return Object
     */
    @ResponseBody
    @RequestMapping(value = "/ApplyInterviewPro",
            method = {RequestMethod.POST, RequestMethod.GET})
    public Object applyFacePro(@RequestBody final String jsonStr) throws AgeDiscrepancyException {
        logger.info("jsonStr：" + jsonStr);
        JSONObject paramJSON = JSON.parseObject(jsonStr);
        String dLnrId = paramJSON.getString("userId");
        String dProId = paramJSON.getString("proId");
        String dProName = paramJSON.getString("proName");
        String applyName = paramJSON.getString("applyName");
        String identityCard = paramJSON.getString("identityCard");
        String phone = paramJSON.getString("phone");
        String province = paramJSON.getString("province");
        String city = paramJSON.getString("city");
        String region = paramJSON.getString("region");
        String detailAdd = paramJSON.getString("detailAdd");
        String starAccount = paramJSON.getString("starAccount");
        StringBuffer money = new StringBuffer(paramJSON.getString("dMoney"));
        money.append("0000");
        String dMoney = new String(money);
        CustomerFlowDebit customerFlowDebit = new CustomerFlowDebit();
        customerFlowDebit.setDLnrId(dLnrId);
        customerFlowDebit.setApplyName(applyName);
        customerFlowDebit.setIdentityCard(identityCard);
        customerFlowDebit.setPhone(phone);
        customerFlowDebit.setProvince(province);
        customerFlowDebit.setCity(city);
        customerFlowDebit.setRegion(region);
        customerFlowDebit.setDetailAdd(detailAdd);
        customerFlowDebit.setStarAccount(starAccount);
        customerFlowDebit.setDMoney(new BigDecimal(dMoney));
        customerFlowDebit.setDProId(dProId);
        customerFlowDebit.setDProName(dProName);
        return lenderService.insertDebitInfo(customerFlowDebit);
    }

    /**
     *用户对应某一产品的还款分期计划.
     * @param jsonStr 包含userId,proId
     * @return 分期计划
     */
    @ResponseBody
    @RequestMapping(value = "/StagingPlan",
            method = {RequestMethod.POST, RequestMethod.GET})
    public Object findStagingPlan(@RequestBody final String jsonStr) {
        logger.info("jsonStr：" + jsonStr);
        JSONObject paramJSON = JSON.parseObject(jsonStr);
        String userId = paramJSON.getString("userId");
        String proId = paramJSON.getString("proId");
        return repayALoanService.findRepayAloanInfo(userId,proId);
    }

    /**
     *用户对应所有产品的贷款状态.
     * @param jsonStr 包含userId
     * @return 返回所有产品贷款状态
     */
    @ResponseBody
    @RequestMapping(value = "/AllProStatus",
            method = {RequestMethod.POST, RequestMethod.GET})
    public Object findAllProStatus(@RequestBody String jsonStr) {
        logger.info("jsonStr：" + jsonStr);
        JSONObject paramJSON = JSON.parseObject(jsonStr);
        String userId = paramJSON.getString("userId");
        if (userId == null || userId.length() == 0){
            return false;
        }
        return debitAndCreditService.findAllProStatus(userId);
    }

    /**
     * 根据申请单号查询用户该单的还款信息.
     *
     * @param jsonStr 申请单号
     * @return 返回参数ReturnResult包含:msgCode:0失败,1成功;
     * result： List<BR>
     */
    @ResponseBody
    @RequestMapping(value = "/FindAllRepay",
            method = {RequestMethod.POST, RequestMethod.GET})
    public Object findAllRepay(@RequestBody final String jsonStr) {
        logger.info("jsonStr：" + jsonStr);
        JSONObject paramJSON = JSON.parseObject(jsonStr);
        String oddNumbers = paramJSON.getString("oddNumbers");
        logger.info("LenderController FindAllRepay:oddNumbers--" + oddNumbers);

        return repayALoanService.getRepayALoanList(oddNumbers);
    }

}