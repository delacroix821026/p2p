package integration;

import com.newland.financial.p2p.Application;
import com.newland.financial.p2p.domain.entity.DebitAndRepaySummary;
import com.newland.financial.p2p.service.IDebitAndCreditService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DebitAndCreditServiceTest {
    @Autowired
    private IDebitAndCreditService debitAndCreditService;

    @Before
    public void setUpClass() {

    }

    @Test
    public void testGetDebitList() {
        String jsonStr = "{\n" +
                "\t\"proId\":\"shls001\",\n" +
                "\t\"proName\":\"商户流水贷\",\n" +
                "\t\"oddNumbers\":\"201710211613069120\",\n" +
                "\t\"contractNumber\":\"1111111111\",\n" +
                "\t\"createTimeBeg\":1506787200,\n" +
                "\t\"createTimeEnd\":1509292800,\n" +
                "\t\"count\":null,\n" +
                "\t\"page\":null\n" +
                "}";
        debitAndCreditService.getDebitList(jsonStr);
    }

    @Test
    public void testFindAllProStatus() {
        String userId = "123";
        debitAndCreditService.findAllProStatus(userId);
        String userId1 = "zhu123";
        debitAndCreditService.findAllProStatus(userId1);
    }
}
