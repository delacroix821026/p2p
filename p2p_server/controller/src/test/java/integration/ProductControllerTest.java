package integration;

import com.newland.financial.p2p.Application;
import com.newland.financial.p2p.controller.ProductController;
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
public class    ProductControllerTest {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private ProductController productController;
    private MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(productController).build();
    }


    @Test
    public void testGetProduct()throws Exception{
        ResultActions ra = this.mockMvc.perform(MockMvcRequestBuilders
                .post("/ProductController/GetProduct")
                .contentType(MediaType.APPLICATION_JSON).content("{\"proId\":\"kv333\"}"));
        MvcResult mr = ra.andReturn();
        ResultActions ra1 = this.mockMvc.perform(MockMvcRequestBuilders
                .post("/ProductController/GetProduct")
                .contentType(MediaType.APPLICATION_JSON).content("{\"proId\":\"\"}"));
        MvcResult mr1= ra1.andReturn();
        String result1 = mr.getResponse().getContentAsString();
        String result12 = mr1.getResponse().getContentAsString();
        logger.info("result1:" + result1);
        logger.info("result12:" + result12);
    }

    @Test
    public void testInsertProduct() throws Exception {
        ResultActions ra = this.mockMvc.perform(MockMvcRequestBuilders
                .post("/ProductController/InsertProduct")
                .contentType(MediaType.APPLICATION_JSON).content("{\"proId\":\"cs999\",\"proName\":\"apple\",\"proLmt\":10000,\"interestList\":[{\"times\":3},{\"times\":6}],\"proNameOperator\":\"apple\",\"sponsor\":\"apple\",\"sprProName\":\"apple\",\"maxLmt\":100000,\"role\":\"1\",\"orgs\":[{\"organization\":\"999\"},{\"organization\":\"1000\"}],\"repayMhd\":\"1\",\"interestMhd\":\"1\",\"cutMhd\":\"1\",\"advanceRepay\":\"1\",\"poundage\":\"1\",\"formula\":\"5+1=6\",\"isLatefee\":\"1\",\"latefee\":20,\"positiveOrNegative\":\"1\"}"));
        MvcResult mr = ra.andReturn();
        ResultActions ra1 = this.mockMvc.perform(MockMvcRequestBuilders
                .post("/ProductController/InsertProduct")
                .contentType(MediaType.APPLICATION_JSON).content("{\"proId\":\"cs999\",\"proName\":\"apple\",\"proLmt\":10000,\"interestList\":[{\"times\":3},{\"times\":6}],\"proNameOperator\":\"apple\",\"sponsor\":\"apple\",\"sprProName\":\"apple\",\"maxLmt\":100000,\"role\":\"1\",\"orgs\":[{\"organization\":\"999\"},{\"organization\":\"1000\"}],\"repayMhd\":\"1\",\"interestMhd\":\"1\",\"cutMhd\":\"1\",\"advanceRepay\":\"1\",\"poundage\":\"1\",\"formula\":\"5+1=6\",\"isLatefee\":\"1\",\"latefee\":20,\"positiveOrNegative\":\"1\"}"));
        MvcResult mr1 = ra1.andReturn();
        String result1 = mr.getResponse().getContentAsString();
        String result12 = mr1.getResponse().getContentAsString();
        logger.info("result1:" + result1);
        logger.info("result12:" + result12);
    }

    @Test
    public void testPutOrDown() throws Exception {
        ResultActions ra = this.mockMvc.perform(MockMvcRequestBuilders
                .post("/ProductController/PutOrDown")
                .contentType(MediaType.APPLICATION_JSON).content("{\"proId\":\"cs999\",\"putAndDown\":\"1\"}"));
        MvcResult mr = ra.andReturn();
        String result = mr.getResponse().getContentAsString();

        ResultActions ra1 = this.mockMvc.perform(MockMvcRequestBuilders
                .post("/ProductController/PutOrDown")
                .contentType(MediaType.APPLICATION_JSON).content("{\"proId\":\"\",\"putAndDown\":\"1\"}"));
        MvcResult mr1 = ra1.andReturn();
        String result1 = mr1.getResponse().getContentAsString();
        logger.info("result:" + result1);
    }

    @Test
    public void testUpdateProd01() throws Exception {
        String reqJson = "{\n" +
                "    \"advanceRepay\":\"\",\n" +
                "    \"cutMhd\":\"\",\n" +
                "    \"formula\":\"\",\n" +
                "    \"interestMhd\":\"\",\n" +
                "    \"isLatefee\":\"\",\n" +
                "    \"latefee\":0.35,\n" +
                "    \"maxLmt\":456789,\n" +
                "    \"positiveOrNegative\":\"1\",\n" +
                "    \"poundage\":\"\",\n" +
                "    \"proId\":\"7\",\n" +
                "    \"proLmt\":\"1000\",\n" +
                "    \"proName\":\"test7\",\n" +
                "    \"proNameOperator\":\"\",\n" +
                "    \"putAndDown\":\"1\",\n" +
                "    \"repayMhd\":\"\",\n" +
                "    \"role\":\"2\",\n" +
                "    \"sponsor\":\"\",\n" +
                "    \"sprProName\":\"\",\n" +
                "    \"interestList\":[\n" +
                "        {\"times\":6},\n" +
                "        {\"times\":7}\n" +
                "    ],\n" +
                "    \"orgs\":[\n" +
                "        {\"id\":0,\"organization\":\"2\"},\n" +
                "        {\"id\":0,\"organization\":\"6\"}\n" +
                "    ]\n" +
                "}";
        ResultActions ra = this.mockMvc.perform(MockMvcRequestBuilders
                .post("/ProductController/UpdateProd")
                .contentType(MediaType.APPLICATION_JSON).content(reqJson));
        MvcResult mr = ra.andReturn();
        String result = mr.getResponse().getContentAsString();
        logger.info("---------------------------------------------------------result01:" + result);
    }

    @Test
    public void testUpdateProd02() throws Exception {
        String reqJson = "{\n" +
                "    \"advanceRepay\":\"\",\n" +
                "    \"cutMhd\":\"\",\n" +
                "    \"formula\":\"\",\n" +
                "    \"interestMhd\":\"\",\n" +
                "    \"isLatefee\":\"\",\n" +
                "    \"latefee\":0.35,\n" +
                "    \"maxLmt\":456789,\n" +
                "    \"positiveOrNegative\":\"1\",\n" +
                "    \"poundage\":\"\",\n" +
                "    \"proId\":\"\",\n" +
                "    \"proLmt\":\"1000\",\n" +
                "    \"proName\":\"test7\",\n" +
                "    \"proNameOperator\":\"\",\n" +
                "    \"putAndDown\":\"1\",\n" +
                "    \"repayMhd\":\"\",\n" +
                "    \"role\":\"2\",\n" +
                "    \"sponsor\":\"\",\n" +
                "    \"sprProName\":\"\",\n" +
                "    \"interestList\":[\n" +
                "        {\"times\":6},\n" +
                "        {\"times\":7}\n" +
                "    ],\n" +
                "    \"orgs\":[\n" +
                "        {\"id\":0,\"organization\":\"2\"},\n" +
                "        {\"id\":0,\"organization\":\"6\"}\n" +
                "    ]\n" +
                "}";
        ResultActions ra = this.mockMvc.perform(MockMvcRequestBuilders
                .post("/ProductController/UpdateProd")
                .contentType(MediaType.APPLICATION_JSON).content(reqJson));
        MvcResult mr = ra.andReturn();
        String result = mr.getResponse().getContentAsString();
        logger.info("---------------------------------------------------------result02:" + result);
    }

    @Test
    public void testGetProdList() throws Exception {
        String reqJson = "{\n" +
                "    \"sponsor\":null,\n" +
                "    \"role\":\"\",\n" +
                "    \"proId\":null,\n" +
                "    \"createTimeEnd\":\"\",\n" +
                "    \"count\":3,\n" +
                "    \"page\":1,\n" +
                "    \"proName\":null,\n" +
                "    \"createTimeBeg\":\"123\"}\t";
        ResultActions ra = this.mockMvc.perform(MockMvcRequestBuilders
                .post("/ProductController/GetProdList")
                .contentType(MediaType.APPLICATION_JSON).content(reqJson));
        MvcResult mr = ra.andReturn();
        String result = mr.getResponse().getContentAsString();
        logger.info("---------------------------------------------------------GetProdList:" + result);
    }


    @Test
    public void getAppProducts() throws Exception {
        String reqJson = "{\n" +
                "\t\"role\":\"2\",\n" +
                "\t\"organization\":\"100\",\n" +
                "\t\"count\":3,\n" +
                "\t\"page\":null\n" +
                "}";
        ResultActions ra = this.mockMvc.perform(MockMvcRequestBuilders
                .post("/ProductController/getAppProducts")
                .contentType(MediaType.APPLICATION_JSON).content(reqJson));
        MvcResult mr = ra.andReturn();
        String result = mr.getResponse().getContentAsString();
        logger.info("---------------------------------------------------------getAppProducts:" + result);
    }

    @Test
    public void testCheckStatus() throws Exception {
        String reqJson = "{\n" +
                "\t\"userId\":\"123123\",\n" +
                "\t\"page\":1,\n" +
                "\t\"count\":1\n" +
                "}\n";
        ResultActions ra = this.mockMvc.perform(MockMvcRequestBuilders
                .post("/ProductController/CheckStatus")
                .contentType(MediaType.APPLICATION_JSON).content(reqJson));
        MvcResult mr = ra.andReturn();
        String result = mr.getResponse().getContentAsString();
        logger.info("---------------------------------------------------------getAppProducts:" + result);
    }
}
