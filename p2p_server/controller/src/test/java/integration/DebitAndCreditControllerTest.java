package integration;

import com.newland.financial.p2p.Application;
import com.newland.financial.p2p.controller.DebitAndCreditController;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
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

@WebAppConfiguration
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class DebitAndCreditControllerTest {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private DebitAndCreditController debitAndCreditController;
    private MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(debitAndCreditController).build();
    }

    @Test
    public void testGetDebitList() throws Exception {
        String reqJson = "{\n" +
                "\t\"proId\":\"shls001\",\n" +
                "\t\"proName\":\"商户流水贷\",\n" +
                "\t\"oddNumbers\":\"\",\n" +
                "\t\"contractNumber\":\"\",\n" +
                "\t\"createTimeBeg\":null,\n" +
                "\t\"createTimeEnd\":null,\n" +
                "\t\"count\":3,\n" +
                "\t\"page\":1\n" +
                "}";
        ResultActions ra = null;
        ra = this.mockMvc.perform(MockMvcRequestBuilders
                .post("/DebitAndCreditController/getDebitList")
                .contentType(MediaType.APPLICATION_JSON).content(reqJson));
        MvcResult mr = ra.andReturn();
        String result = mr.getResponse().getContentAsString();
        logger.info("---------------------------------------------------------testGetDebitList:" + result);
    }
}
