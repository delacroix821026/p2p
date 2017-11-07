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
import lombok.extern.log4j.Log4j;
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
@Log4j
@RequestMapping("/LenderController")
public class LenderController {
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
        log.debug("jsonStr：" + jsonStr);
        JSONObject paramJSON = JSON.parseObject(jsonStr);
        String lenderId = paramJSON.getString("lenderId");
        log.debug("LenderController GetLender:lenderId--" + lenderId);
        Lender lender = lenderService.getLender(lenderId);
        log.debug(lender.toString());
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
        log.debug("jsonStr：" + jsonStr);
        JSONObject paramJSON = JSON.parseObject(jsonStr);
        String userId = paramJSON.getString("userId");
        String productId = paramJSON.getString("productId");
        BigDecimal money = paramJSON.getBigDecimal("money");
        Integer interestId = Integer.parseInt(paramJSON.getString("interestId"));
        log.debug("LenderController GetLender:userId--" + userId);
        log.debug("LenderController GetLender:productId--" + productId);
        log.debug("LenderController GetLender:productId--" + money);
        log.debug("LenderController GetLender:productId--" + interestId);
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
        log.debug("jsonStr：" + jsonStr);
        JSONObject paramJSON = JSON.parseObject(jsonStr);
        String repayId = paramJSON.getString("repayId");
        log.debug("LenderController Repay:repayId--" + repayId);
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
        log.debug("jsonStr：" + jsonStr);
        JSONObject paramJSON = JSON.parseObject(jsonStr);
        String userId = paramJSON.getString("userId");
        log.debug("LenderController FindAllDebit:userId--" + userId);
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
        log.debug("jsonStr：" + jsonStr);
        JSONObject paramJSON = JSON.parseObject(jsonStr);
        String userId = paramJSON.getString("userId");
        log.debug("LenderController FindTotalMoney:userId--" + userId);
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
        log.debug("jsonStr：" + jsonStr);
        JSONObject paramJSON = JSON.parseObject(jsonStr);
        String userId = paramJSON.getString("userId");
        log.debug("LenderController FindTotalMoney:userId--" + userId);
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
        log.debug("jsonStr：" + jsonStr);
        JSONObject paramJSON = JSON.parseObject(jsonStr);
        String userId = paramJSON.getString("userId");
        log.debug("LenderController Clear:lenderId--" + userId);
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
     * @param jsonStr 请求参数：<BR>
     *{<BR>
     *&nbsp;"userId":"123123",用户编号<BR>
     *&nbsp;"proId":"shls001",产品编号<BR>
     *&nbsp;"proName":"商户流水贷",产品名称<BR>
     *&nbsp;"applyName":"Mxia",申请人姓名<BR>
     *&nbsp;"identityCard":"340521198902104612",申请人身份证号<BR>
     *&nbsp;"phone":"15021327865",手机号码<BR>
     *&nbsp;"province":"上海",省<BR>
     *&nbsp;"city":"上海",市<BR>
     *&nbsp;"region":"浦东",区或县<BR>
     *&nbsp;"detailAdd":"世博大道",详细地址<BR>
     *&nbsp;"starAccount":"15021327865",绑定的星官家账号<BR>
     *&nbsp;"dMoney":"20",贷款额度<BR>
     *&nbsp;"merchantNum":"121212123"绑定的商户号<BR>
     *}
     * @return Object 返回参数：true or false
     * @throws AgeDiscrepancyException 年龄不符合
     */
    @ResponseBody
    @RequestMapping(value = "/ApplyInterviewPro",
            method = {RequestMethod.POST, RequestMethod.GET})
    public Object applyFacePro(@RequestBody final String jsonStr) throws AgeDiscrepancyException {
        log.debug("jsonStr：" + jsonStr);
        JSONObject paramJSON = JSON.parseObject(jsonStr);
        String dLnrId = paramJSON.getString("userId");
        if (dLnrId == null || dLnrId.length() == 0) {
            return false;
        }
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
        String merchantNum = paramJSON.getString("merchantNum");
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
        customerFlowDebit.setMerchantNum(merchantNum);
        customerFlowDebit.setEffective("1");
        return lenderService.insertDebitInfo(customerFlowDebit);
    }

    /**
     *用户对应某一产品的还款分期计划.
     * @param jsonStr 请求参数：<BR>
     * {<BR>
     * &nbsp;"userId":"123",用户编号<BR>
     * &nbsp;"proId":"shls001"产品编号<BR>
     * }
     * @return 返回还款单<BR>
     * [<BR>
     *&nbsp;{<BR>
     *&nbsp;&nbsp;"needRepay": 2040,累计待还<BR>
     *&nbsp;&nbsp;"repayed": 1020,累计已还<BR>
     *&nbsp;&nbsp;"repayInMonth": 2040,本期应还<BR>
     *&nbsp;},<BR>
     *&nbsp;[<BR>
     *&nbsp;&nbsp;{<BR>
     *&nbsp;&nbsp;&nbsp;"reId": "1",还款单编号<BR>
     *&nbsp;&nbsp;&nbsp;"reLndId": "123",用户编号<BR>
     *&nbsp;&nbsp;&nbsp;"reProId": "shls001",产品编号<BR>
     *&nbsp;&nbsp;&nbsp;"reProNmae": "商户流水贷",产品名称<BR>
     *&nbsp;&nbsp;&nbsp;"crtRe": 1000,<BR>
     *&nbsp;&nbsp;&nbsp;"createDate": 1503072000000,指定还款日期<BR>
     *&nbsp;&nbsp;&nbsp;"expireDate": null,<BR>
     *&nbsp;&nbsp;&nbsp;"status": 1,还款记录状态，1已还，0未还<BR>
     *&nbsp;&nbsp;&nbsp;"crtInterest": 10,<BR>
     *&nbsp;&nbsp;&nbsp;"serMoney": 10,<BR>
     *&nbsp;&nbsp;&nbsp;"totleMoney": 1020,该期需要还款金额（包含其他费用）<BR>
     *&nbsp;&nbsp;&nbsp;"debitId": "1"对应贷款单编号<BR>
     *&nbsp;&nbsp;},<BR>
     *&nbsp;&nbsp;{...},<BR>
     *&nbsp;&nbsp;{...}<BR>
     *&nbsp;]<BR>
     *]
     */
    @ResponseBody
    @RequestMapping(value = "/StagingPlan",
            method = {RequestMethod.POST, RequestMethod.GET})
    public Object findStagingPlan(@RequestBody final String jsonStr) {
        log.debug("jsonStr：" + jsonStr);
        JSONObject paramJSON = JSON.parseObject(jsonStr);
        String userId = paramJSON.getString("userId");
        String proId = paramJSON.getString("proId");
        return repayALoanService.findRepayAloanInfo(userId, proId);
    }

    /**
     *用户对应所有产品的贷款状态.
     * @param jsonStr 请求参数：<BR>
     *{<BR>
     *&nbsp;"userId":"123"用户编号<BR>
     *}
     * @return 返回参数：<BR>
     *[<BR>
     *&nbsp;{<BR>
     *&nbsp;&nbsp;"proId": "shls001",产品编号<BR>
     *&nbsp;&nbsp;"proName": "商户流水贷",产品名称<BR>
     *&nbsp;&nbsp;"loanMoney": 3000,借款金额<BR>
     *&nbsp;&nbsp;"totleRepay": 0,应还总额<BR>
     *&nbsp;&nbsp;"needRepay": null,<BR>
     *&nbsp;&nbsp;"repayed": 0,已还总额<BR>
     *&nbsp;&nbsp;"repayInMonth": null,<BR>
     *&nbsp;&nbsp;"loanDate": null,放款日期<BR>
     *&nbsp;&nbsp;"lastRePayDate": null,最后还款日期<BR>
     *&nbsp;&nbsp;"status": "1"申请状态：1申请中，2还款计划<BR>
     *&nbsp;}<BR>
     *]
     */
    @ResponseBody
    @RequestMapping(value = "/AllProStatus",
            method = {RequestMethod.POST, RequestMethod.GET})
    public Object findAllProStatus(@RequestBody String jsonStr) {
        log.debug("jsonStr：" + jsonStr);
        JSONObject paramJSON = JSON.parseObject(jsonStr);
        String userId = paramJSON.getString("userId");
        if (userId == null || userId.length() == 0) {
            return false;
        }
        return debitAndCreditService.findAllProStatus(userId);
    }

    /**
     * 根据申请单号查询用户该单的还款信息.
     *
     * @param jsonStr 申请单号：
     *{<BR>
     *&nbsp;"oddNumbers":"201708190012002604"申请单号<BR>
     * }
     * @return 返回参数:<BR>
     *[<BR>
     *&nbsp;{<BR>
     *&nbsp;&nbsp;"reId": "1",还款单编号<BR>
     *&nbsp;&nbsp;"reLndId": "123",用户编号<BR>
     *&nbsp;&nbsp;"reProId": "shls001",产品编号<BR>
     *&nbsp;&nbsp;"reProNmae": "商户流水贷",产品名称<BR>
     *&nbsp;&nbsp;"crtRe": 1000,应还本金<BR>
     *&nbsp;&nbsp;"createDate": 1503072000000,指定还款日期<BR>
     *&nbsp;&nbsp;"expireDate": null,实际还款日期<BR>
     *&nbsp;&nbsp;"status": 1,还款单状态 0未还，1已还<BR>
     *&nbsp;&nbsp;"crtInterest": 10,利息<BR>
     *&nbsp;&nbsp;"serMoney": 10,手续费<BR>
     *&nbsp;&nbsp;"otherMoney": 0,其他费用<BR>
     *&nbsp;&nbsp;"totleMoney": 1020,总应还金额<BR>
     *&nbsp;&nbsp;"debitId": "1",对应贷款单编号<BR>
     *&nbsp;},<BR>
     * &nbsp;{...},<BR>
     * &nbsp;{...}<BR>
     *]
     */
    @ResponseBody
    @RequestMapping(value = "/FindAllRepay",
            method = {RequestMethod.POST, RequestMethod.GET})
    public Object findAllRepay(@RequestBody final String jsonStr) {
        log.debug("jsonStr：" + jsonStr);
        JSONObject paramJSON = JSON.parseObject(jsonStr);
        String oddNumbers = paramJSON.getString("oddNumbers");
        log.debug("LenderController FindAllRepay:oddNumbers--" + oddNumbers);
        return repayALoanService.getRepayALoanList(oddNumbers);
    }

}