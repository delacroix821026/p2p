package integration;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.newland.financial.p2p.Application;
import com.newland.financial.p2p.controller.LenderController;
import com.newland.financial.p2p.dao.IRepayALoanDao;
import com.newland.financial.p2p.domain.entity.*;
import com.newland.financial.p2p.service.IDebitAndCreditService;
import com.newland.financial.p2p.service.IRepayALoanService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * @author xiazunhua
 * */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@WebAppConfiguration
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class LenderControllerTest {
   /* protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private LenderController lenderController;
    @Autowired
    private IRepayALoanService repayALoanService;
    @Autowired
    private IDebitAndCreditService debitAndCreditService;
    @Autowired
    private IRepayALoanDao repayALoanDao;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(lenderController).build();
    }

    @Test//事先插入测试的数据
    public void A_setUpClass(){
        Lender lender = new Lender();
        lender.setUserId("7777777");
        lender.setLenderName("test");
        lender.setTotleLmt(new BigDecimal("200000"));
        lender.setCurrentLmt(new BigDecimal("100000"));
        debitAndCreditService.insertLender(lender);

        Interest interest = new Interest();
        interest.setIttId("77");
        interest.setIProId("5");
        interest.setIProName("threeTimes");
        interest.setTimes(3);
        interest.setIntRate(new BigDecimal("1.6"));
        debitAndCreditService.insertInterest(interest);

        Product product = new Product();
        List<Interest> list = new ArrayList<Interest>();
        list.add(interest);
        product.setProId("5");
        product.setProName("测试贷");
        product.setProLmt(new BigDecimal("100000"));
        product.setInterestList(list);
        debitAndCreditService.insertProduct(product);
    }

    @Test//传入正确的userId,获取相关数据库信息
    public void B_testGetLender() throws Exception {
        ResultActions ra = this.mockMvc.perform(MockMvcRequestBuilders
                .post("/LenderController/GetLender")
                .contentType(MediaType.APPLICATION_JSON).content("{\"lenderId\":7777777}"));
        MvcResult mr = ra.andReturn();
        String result = mr.getResponse().getContentAsString();
        logger.info("aaaaaaaaaaaaaaaa:" + result);
        JSONObject jsonObject = JSON.parseObject(result);
        Lender lender = JSON.parseObject(jsonObject.getString("result"), Lender.class);
        Assert.assertEquals("ID need be 7777777!", "7777777", lender.getUserId());
    }

    @Test//传入错误的userId，抛出空指针
    public void C_testGetLenderNotFound() throws Exception {
        ResultActions ra = this.mockMvc.perform(MockMvcRequestBuilders
                .post("/LenderController/GetLender")
                .contentType(MediaType.APPLICATION_JSON).content("{\"lenderId\":null}"));
        MvcResult mr = ra.andReturn();
        String result = mr.getResponse().getContentAsString();
        logger.info("aaaaaaaaaaaaaaaa:" + result);
    }

    @Test//正常贷款
    public void D_testDebit()throws Exception {
        ResultActions ra = this.mockMvc.perform(MockMvcRequestBuilders
                .post("/LenderController/Debit")
                .contentType(MediaType.APPLICATION_JSON).content("{\"userId\":7777777,\"productId\":5,\"money\":6000,\"interestId\":77}"));
        MvcResult mr = ra.andReturn();
        String result = mr.getResponse().getContentAsString();
        logger.info("aaaaaaaaaaaaaaaa:" + result);

        List<DebitAndCredit> list = debitAndCreditService.findDebitAndCreditHistory("7777777");
        DebitAndCredit db = list.get(0);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH,3);
        String date1 = simpleDateFormat.format(calendar.getTime());
        String date2 = simpleDateFormat.format(db.getLastRePayDate());
        Assert.assertEquals("生成的贷款单数量不一致",1,list.size());
        Assert.assertEquals("用户Id不一致","7777777",db.getDLnrId());
        Assert.assertEquals("贷款金额不一致",new BigDecimal("6000.0000"),db.getDMoney());
        Assert.assertEquals("最后还款日期不一致",date1,date2);

        List<RepayALoan> list1 = repayALoanService.getRepayALoanList("7777777");
        Assert.assertEquals("the size not equals",3,list1.size());
        Assert.assertEquals("the userId not equals","7777777",list1.get(0).getReLndId());
    }

    @Test//超出额度,抛出异常
    public void E_testDebitOverLoad()throws Exception {
        ResultActions ra = this.mockMvc.perform(MockMvcRequestBuilders
                .post("/LenderController/Debit")
                .contentType(MediaType.APPLICATION_JSON).content("{\"userId\":7777777,\"productId\":5,\"money\":10000000,\"interestId\":77}"));
        MvcResult mr = ra.andReturn();
        String result = mr.getResponse().getContentAsString();
        logger.info("aaaaaaaaaaaaaaaa:" + result);
    }

    @Test
    public void F_testRepay()throws Exception{
        List<RepayALoan> list = repayALoanDao.findByUserId("7777777");
        RepayALoan repayALoan = list.get(0);
        String repayId = repayALoan.getReId();
        logger.info("repayId============"+repayId);
        ResultActions ra = this.mockMvc.perform(MockMvcRequestBuilders
                .post("/LenderController/Repay")
                .contentType(MediaType.APPLICATION_JSON).content("{\"repayId\":"+"\""+repayId+"\""+"}"));
        MvcResult mr = ra.andReturn();
        String result = mr.getResponse().getContentAsString();
        logger.info("aaaaaaaaaaaaaaaa:" + result);
    }

    @Test
    public void G_testFindAllDebit()throws Exception{
        ResultActions ra = this.mockMvc.perform(MockMvcRequestBuilders
                .post("/LenderController/FindAllDebit")
                .contentType(MediaType.APPLICATION_JSON).content("{\"userId\":7777777}"));
        MvcResult mr = ra.andReturn();
        String result = mr.getResponse().getContentAsString();
        logger.info("aaaaaaaaaaaaaaaa:" + result);
    }

    @Test
    public void H_testFindAllRepay()throws Exception{
        ResultActions ra = this.mockMvc.perform(MockMvcRequestBuilders
                .post("/LenderController/FindAllRepay")
                .contentType(MediaType.APPLICATION_JSON).content("{\"userId\":7777777}"));
        MvcResult mr = ra.andReturn();
        String result = mr.getResponse().getContentAsString();
        logger.info("aaaaaaaaaaaaaaaa:" + result);
        JSONObject jsonObject = JSON.parseObject(result);
        List<RepayALoan> list = JSON.parseObject(jsonObject.getString("result"), ArrayList.class);
        Assert.assertEquals("还款单数量不一致",3,list.size());
    }

    @Test
    public void J_testFindTotalMoney()throws Exception{
        ResultActions ra = this.mockMvc.perform(MockMvcRequestBuilders
                .post("/LenderController/FindTotalMoney")
                .contentType(MediaType.APPLICATION_JSON).content("{\"userId\":7777777}"));
        MvcResult mr = ra.andReturn();
        String result = mr.getResponse().getContentAsString();
        logger.info("aaaaaaaaaaaaaaaa:" + result);
        JSONObject jsonObject = JSON.parseObject(result);
        BigDecimal bigDecimal = JSON.parseObject(jsonObject.getString("result"), BigDecimal.class);
        Assert.assertEquals("本月还款金额不一致",new BigDecimal("0"),bigDecimal);

    }

    @Test
    public void K_testGetDebitAndRepaySummary()throws Exception{
        ResultActions ra = this.mockMvc.perform(MockMvcRequestBuilders
                .post("/LenderController/getDebitAndRepaySummary")
                .contentType(MediaType.APPLICATION_JSON).content("{\"userId\":7777777}"));
        MvcResult mr = ra.andReturn();
        String result = mr.getResponse().getContentAsString();
        logger.info("aaaaaaaaaaaaaaaa:" + result);
        JSONObject jsonObject = JSON.parseObject(result);
        DebitAndRepaySummary das = JSON.parseObject(jsonObject.getString("result"), DebitAndRepaySummary.class);
        Assert.assertEquals("未还总金额不一致",new BigDecimal("4000.0000"),das.getNeedRepay());
        Assert.assertEquals("以还总金额不一致",new BigDecimal("2000.0000"),das.getRepayed());
        Assert.assertEquals("本月应还金额不一致",new BigDecimal("0"),das.getRepayInMonth());
    }

    @Test//改变status值
    public void L_tesClearData()throws Exception{
        ResultActions ra = this.mockMvc.perform(MockMvcRequestBuilders
                .post("/LenderController/UpdateStatus")
                .contentType(MediaType.APPLICATION_JSON).content("{\"userId\":7777777}"));
        MvcResult mr = ra.andReturn();
        String result = mr.getResponse().getContentAsString();
        logger.info("aaaaaaaaaaaaaaaa:" + result);
    }

    @Test//清楚测试数据
    public void M_deleteDatas(){
        debitAndCreditService.deleteInterest("77");
        debitAndCreditService.deleteLender("7777777");
        debitAndCreditService.deleteProduct("5");
        debitAndCreditService.deleteDebitAndCredit("7777777");
        debitAndCreditService.deleteRepayALoan("7777777");
    }*/
}
