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
                .contentType(MediaType.APPLICATION_JSON).content("{\"proId\":\"kv222\"}"));
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
                .contentType(MediaType.APPLICATION_JSON).content("{\"proId\":\"cs999\",\"proName\":\"测试贷\",\"proLmt\":10000,\"interestList\":[{\"times\":3},{\"times\":6}],\"proNameOperator\":\"星贷aa\",\"sponsor\":\"小明公司\",\"sprProName\":\"消费贷\",\"maxLmt\":100000,\"role\":\"1\",\"orgs\":[{\"organization\":\"999\"},{\"organization\":\"1000\"}],\"repayMhd\":\"1\",\"interestMhd\":\"1\",\"cutMhd\":\"1\",\"advanceRepay\":\"1\",\"poundage\":\"1\",\"formula\":\"5+1=6\",\"isLatefee\":\"1\",\"latefee\":20,\"positiveOrNegative\":\"1\"}"));
        MvcResult mr = ra.andReturn();
        ResultActions ra1 = this.mockMvc.perform(MockMvcRequestBuilders
                .post("/ProductController/InsertProduct")
                .contentType(MediaType.APPLICATION_JSON).content("{\"proId\":\"cs999\",\"proName\":\"测试贷\",\"proLmt\":10000,\"interestList\":[{\"times\":3},{\"times\":6}],\"proNameOperator\":\"星贷aa\",\"sponsor\":\"小明公司\",\"sprProName\":\"消费贷\",\"maxLmt\":100000,\"role\":\"1\",\"orgs\":[{\"organization\":\"999\"},{\"organization\":\"1000\"}],\"repayMhd\":\"1\",\"interestMhd\":\"1\",\"cutMhd\":\"1\",\"advanceRepay\":\"1\",\"poundage\":\"1\",\"formula\":\"5+1=6\",\"isLatefee\":\"1\",\"latefee\":20,\"positiveOrNegative\":\"1\"}"));
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
        logger.info("result:" + result);
    }
}
