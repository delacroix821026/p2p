package integration;

import com.newland.financial.p2p.Application;
import com.newland.financial.p2p.controller.OrderController;
import com.newland.financial.p2p.dao.IOrderInfoDao;
import com.newland.financial.p2p.domain.entity.OrderInfo;
import lombok.extern.log4j.Log4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
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
import com.alibaba.fastjson.JSONObject;

import java.util.Date;

@WebAppConfiguration
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@Log4j
public class OrderControllerTest {
    @Autowired
    private OrderController orderController;
    private MockMvc mockMvc;

    @Autowired
    private IOrderInfoDao orderInfoDao;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(orderController).build();
    }

    @Test
    public void  testOrderController() throws Exception {
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setTxnTime(new Date());
        orderInfo.setContractsCode("123123123123");
        orderInfo.setContractsState("1");
        // 创建空白订单createOrderInfo
        String reqJson = " {\n" +
                "    \"merId\": \"SHFYJR001\",\n" +
                "    \"txnAmt\":100000,\n" +
                "    \"openId\":\"12345abcd\"\n" +
                "  }";
        ResultActions ra = null;
        ra = this.mockMvc.perform(MockMvcRequestBuilders
                .post("/order/SHFYJR001")
                .contentType(MediaType.APPLICATION_JSON).content(reqJson));
        MvcResult mr = ra.andReturn();
        String result = mr.getResponse().getContentAsString();
        orderInfo.setOrderId(result);
        log.info("=============orderId===========" + result);

        String json =  JSONObject.toJSONString(orderInfo);

        // 分期交易
        String reqJson1 = "{\n" +
                "\t\"orderId\":\""+result+"\",\n" +
                "\t\"txnterms\":\"12\",\n" +
                "\t\"accName\":\"测试九九\",\n" +
                "\t\"accNo\":\"370248192322610\",\n" +
                "\t\"accIdcard\":\"330901198808018886\",\n" +
                "\t\"accMobile\":\"15021327865\",\n" +
                "\t\"cvn2\":\"167\",\n" +
                "\t\"validity\":\"0822\",\n" +
                "\t\"smsCode\":\"111111\"\n" +
                "}";
        ResultActions ra1 = null;
        ra1 = this.mockMvc.perform(MockMvcRequestBuilders
                .put("/order/"+result+"/OrderMsgReq")
                .contentType(MediaType.APPLICATION_JSON).content(reqJson1));
        MvcResult mr1 = ra1.andReturn();
        String result1 = mr1.getResponse().getContentAsString();
        log.info("=============orderId===========" + result1);

        // 更新并获取订单信息
        String reqJson2 = json;
        ResultActions ra2 = null;
        ra2 = this.mockMvc.perform(MockMvcRequestBuilders
                .put("/order/"+result+"/OrderInfo")
                .contentType(MediaType.APPLICATION_JSON).content(reqJson2));
        MvcResult mr2 = ra2.andReturn();
        String result2 = mr2.getResponse().getContentAsString();
        log.info("=============orderId===========" + result2);

        // 获取订单信息
        ResultActions ra3 = null;
        ra3 = this.mockMvc.perform(MockMvcRequestBuilders
                .get("/order/"+result));
        MvcResult mr3 = ra3.andReturn();
        String result3 = mr3.getResponse().getContentAsString();
        log.info("=============orderId===========" + result3);

        // 更新订单信息
        String reqJson3 = json;
        ResultActions ra4 = null;
        ra4 = this.mockMvc.perform(MockMvcRequestBuilders
                .put("/order/"+result)
                .contentType(MediaType.APPLICATION_JSON).content(reqJson3));
        MvcResult mr4 = ra4.andReturn();
        String result4 = mr4.getResponse().getContentAsString();
        log.info("=============orderId===========" + result4);

        // 删除数据库测试数据
        orderInfoDao.deleteOrderInfo(result);
    }
}
