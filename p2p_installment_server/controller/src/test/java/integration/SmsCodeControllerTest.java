package integration;

import com.newland.financial.p2p.Application;
import com.newland.financial.p2p.controller.OrderController;
import com.newland.financial.p2p.dao.IOrderInfoDao;
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

@WebAppConfiguration
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@Log4j
public class SmsCodeControllerTest {
    @Autowired
    private OrderController SmsCodeController;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(SmsCodeController).build();
    }

    @Test
    public void testSendSmsCode() throws Exception {
        //第一种情况merId和mobile均正确
        ResultActions ra = null;
        ra = this.mockMvc.perform(MockMvcRequestBuilders
                .get("/smscode/GZW-001/15021327865"));
        MvcResult mr = ra.andReturn();
        String result = mr.getResponse().getContentAsString();
        log.info("=============orderId===========" + result);

        //第二种情况merId为空
        ResultActions ra1 = null;
        ra1 = this.mockMvc.perform(MockMvcRequestBuilders
                .get("/smscode//15021327865"));
        MvcResult mr1 = ra1.andReturn();
        String result1 = mr1.getResponse().getContentAsString();
        log.info("=============orderId===========" + result1);

        //错误的merId
        ResultActions ra2 = null;
        ra2 = this.mockMvc.perform(MockMvcRequestBuilders
                .get("/smscode/aaaaaa/15021327865"));
        MvcResult mr2 = ra2.andReturn();
        String result2 = mr2.getResponse().getContentAsString();
        log.info("=============orderId===========" + result2);
    }
}
