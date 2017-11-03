package integration;

import com.newland.financial.p2p.Application;
import com.newland.financial.p2p.domain.entity.RepayALoan;
import com.newland.financial.p2p.service.IRepayALoanService;
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
public class RepayALoanServiceTest {
    @Autowired
    private IRepayALoanService repayALoanService;

    @Before
    public void setUpClass() {

    }

    @Test
    public void testFindRepayAloanInfo() {
        String userId = "123";
        String proId = "shls001";
        repayALoanService.findRepayAloanInfo(userId, proId);
    }

    @Test
    public void testGetRepayALoanList() {
        String oddNumbers = "201708190012002604";
        List<RepayALoan> list = repayALoanService.getRepayALoanList(oddNumbers);
        Assert.assertEquals("list size not same",0,list.size());
    }
}
