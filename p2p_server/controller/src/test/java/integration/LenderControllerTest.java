package integration;

import com.newland.financial.p2p.Application;
import com.newland.financial.p2p.controller.LenderController;
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
public class LenderControllerTest {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private LenderController lenderController;
    private MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(lenderController).build();
    }

    @Test
    public void testApplyFacePro () throws Exception {
        String reqJson = "{\n" +
                "\t\"userId\":\"zhu123\",\n" +
                "\t\"proId\":\"shls001\",\n" +
                "\t\"proName\":\"商户流水贷\",\n" +
                "\t\"applyName\":\"zhuhongyang\",\n" +
                "\t\"identityCard\":\"340521198902104612\",\n" +
                "\t\"phone\":\"15021327865\",\n" +
                "\t\"province\":\"上海\",\n" +
                "\t\"city\":\"上海\",\n" +
                "\t\"region\":\"浦东\",\n" +
                "\t\"detailAdd\":\"世博大道\",\n" +
                "\t\"starAccount\":\"15021327865\",\n" +
                "\t\"dMoney\":\"20\"\n" +
                "}\t";
        ResultActions ra = this.mockMvc.perform(MockMvcRequestBuilders
                .post("/LenderController/ApplyInterviewPro")
                .contentType(MediaType.APPLICATION_JSON).content(reqJson));
        MvcResult mr = ra.andReturn();
        String result = mr.getResponse().getContentAsString();
        logger.info("---------------------------------------------------------result01:" + result);
    }

    @Test
    public void testFindStagingPlan() throws Exception {
        String reqJson = "{\n" +
                "\t\"userId\":\"123\",\n" +
                "\t\"proId\":\"shls001\"\n" +
                "}";
        ResultActions ra = this.mockMvc.perform(MockMvcRequestBuilders
                .post("/LenderController/StagingPlan")
                .contentType(MediaType.APPLICATION_JSON).content(reqJson));
        MvcResult mr = ra.andReturn();
        String result = mr.getResponse().getContentAsString();
        logger.info("---------------------------------------------------------result01:" + result);
    }

    @Test
    public void testFindAllProStatus() throws Exception {
        String reqJson = "{\n" +
                "    \"userId\":\"123\"\n" +
                "}\t";
        ResultActions ra = this.mockMvc.perform(MockMvcRequestBuilders
                .post("/LenderController/AllProStatus")
                .contentType(MediaType.APPLICATION_JSON).content(reqJson));
        MvcResult mr = ra.andReturn();
        String result = mr.getResponse().getContentAsString();
        logger.info("---------------------------------------------------------result01:" + result);

        String reqJson1 = "{\n" +
                "    \"userId\":\"\"\n" +
                "}\t";
        ResultActions ra1 = this.mockMvc.perform(MockMvcRequestBuilders
                .post("/LenderController/AllProStatus")
                .contentType(MediaType.APPLICATION_JSON).content(reqJson1));
        MvcResult mr1 = ra1.andReturn();
        String result1 = mr1.getResponse().getContentAsString();
        logger.info("---------------------------------------------------------result01:" + result1);
    }

    @Test
    public void testFindAllRepay() throws Exception {
        String reqJson = "{\n" +
                "\t\"oddNumbers\":\"201708190012002604\"\n" +
                "}";
        ResultActions ra = this.mockMvc.perform(MockMvcRequestBuilders
                .post("/LenderController/FindAllRepay")
                .contentType(MediaType.APPLICATION_JSON).content(reqJson));
        MvcResult mr = ra.andReturn();
        String result = mr.getResponse().getContentAsString();
        logger.info("---------------------------------------------------------result01:" + result);
    }
}
