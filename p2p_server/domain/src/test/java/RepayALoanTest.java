import com.newland.financial.p2p.common.exception.AlreadyRepayException;
import com.newland.financial.p2p.domain.entity.Lender;
import com.newland.financial.p2p.domain.entity.RepayALoan;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.math.BigDecimal;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RepayALoanTest {
    private static RepayALoan repayALoan;
    private static Lender lender;

    @BeforeClass
    public static void setUpClass() {
        repayALoan = new RepayALoan();
        lender = new Lender();
        lender.setCurrentLmt(new BigDecimal("100"));
        repayALoan.setPositionExchange(lender);
        repayALoan.setCrtRe(new BigDecimal("50"));
        repayALoan.setStatus(0);
    }
    @Test
    public void A_repay()throws AlreadyRepayException {
        repayALoan.repay();
        Assert.assertEquals("status not equals",1,repayALoan.getStatus());
        Assert.assertEquals("CurrentLmt Error",new BigDecimal("150"),lender.getCurrentLmt());
    }

    @Test(expected = AlreadyRepayException.class)
    public void B_repay() throws AlreadyRepayException{
        repayALoan.repay();
    }


}