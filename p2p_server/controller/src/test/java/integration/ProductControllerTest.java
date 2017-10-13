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

/*
    @Test
    public void testGetProductList()throws Exception{
        ResultActions ra = this.mockMvc.perform(MockMvcRequestBuilders
                .post("/ProductController/GetProductList")
                .contentType(MediaType.APPLICATION_JSON).content("{}"));
        MvcResult mr = ra.andReturn();
        String result = mr.getResponse().getContentAsString();
        logger.info("aaaaaaaaaaaaaaaa:" + result);
    }
*/

    @Test
    public void testGetProduct()throws Exception{
        ResultActions ra = this.mockMvc.perform(MockMvcRequestBuilders
                .post("/ProductController/GetProduct")
                .contentType(MediaType.APPLICATION_JSON).content("{\"proId\":\"4\"}"));
        MvcResult mr = ra.andReturn();
        String result = mr.getResponse().getContentAsString();
        logger.info("aaaaaaaaaaaaaaaa:" + result);
    }
}
