package integration;

import com.newland.financial.p2p.Application;
import com.newland.financial.p2p.common.exception.AgeDiscrepancyException;
import com.newland.financial.p2p.dao.ICustomerFlowDebitDao;
import com.newland.financial.p2p.dao.IDebitAndCreditDao;
import com.newland.financial.p2p.domain.entity.CustomerFlowDebit;
import com.newland.financial.p2p.service.ILenderService;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class LenderServiceTest {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private ILenderService lenderService;
    @Autowired
    private IDebitAndCreditDao debitAndCreditDao;
    @Autowired
    private ICustomerFlowDebitDao customerFlowDebitDao;
    private CustomerFlowDebit customerFlowDebit = new CustomerFlowDebit();

    @Before
    public void setUpClass() {
        customerFlowDebit.setDProId("shls002");
        customerFlowDebit.setDProName("shanghuliushuidai");
        customerFlowDebit.setDLnrId("cs111");
        customerFlowDebit.setApplyName("test");
        customerFlowDebit.setIdentityCard("340521198902104612");
        customerFlowDebit.setPhone("11111111111");
        customerFlowDebit.setProvince("shanghai");
        customerFlowDebit.setCity("shanghai");
        customerFlowDebit.setRegion("shanghai");
        customerFlowDebit.setDetailAdd("shanghai");
        customerFlowDebit.setStarAccount("11111111111");
        customerFlowDebit.setDMoney(new BigDecimal("200000"));
    }

    @Test
    public void testInsertDebitInfo() throws AgeDiscrepancyException {
        lenderService.insertDebitInfo(customerFlowDebit);
        Map<String, Object> map = new HashMap<String, Object>();
        String proId = "shls002";
        map.put("proId", proId);
        List<CustomerFlowDebit> list = customerFlowDebitDao.findAll(map);
        for (CustomerFlowDebit cs : list) {
            Assert.assertEquals("proid not same","shls002",cs.getDProId());
            Assert.assertEquals("proName not same","shanghuliushuidai",cs.getDProName());
            Assert.assertEquals("identitycard not same","340521198902104612",cs.getIdentityCard());
            Assert.assertEquals("dmoney not same",new BigDecimal("200000.00"),cs.getDMoney());
        }
        lenderService.insertDebitInfo(customerFlowDebit);
    }

    @After
    public void deleteTestData() {
        logger.info("================deletedata====================");
        debitAndCreditDao.deleteDebitAndCredit("cs111");
        customerFlowDebitDao.deleteByUserId("cs111");
    }
}
