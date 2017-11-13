/*package unit;

import com.newland.financial.p2p.common.exception.OverloadException;
import com.newland.financial.p2p.domain.entity.Lender;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.math.BigDecimal;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class LenderTest {
    private Lender lender;
    @Before
    public void setUpClass() {
        lender = new Lender();
        lender.setCurrentLmt(new BigDecimal(50));
    }
    @Test
    public void A_canDebit() throws OverloadException {
        lender.canDebit(new BigDecimal(50));
    }

    @Test(expected = OverloadException.class)
    public void B_canDebitThrowEx() throws OverloadException {
        lender.canDebit(new BigDecimal(51));
    }

    @Test
    public void B_changePosition() {
        lender.changePosition(new BigDecimal(-25));
        Assert.assertEquals("release need be 25!", new BigDecimal(25), lender.getCurrentLmt());
    }
}*/