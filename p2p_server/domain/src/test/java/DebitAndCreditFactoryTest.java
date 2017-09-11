import com.newland.financial.p2p.common.exception.OverloadException;
import com.newland.financial.p2p.domain.entity.*;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DebitAndCreditFactoryTest {
    private static Product product;
    private static Lender lender;
    @BeforeClass
    public static void setUpClass() {
        //System.out.println("before");
        product = new Product();
        product.setProId("4");
        product.setProName("星贷");
        product.setProLmt(new BigDecimal("2000"));
        product.setInterestList(new ArrayList<Interest>());

        Interest interest = new Interest();
        interest.setTimes(1);
        interest.setIttId("2");
        interest.setIProName("星贷");
        interest.setIProId("4");
        interest.setIntRate(new BigDecimal("1.6"));
        product.getInterestList().add(interest);

        lender = new Lender();
        lender.setCurrentLmt(new BigDecimal(1000));
        lender.setUserId("2222222");
        lender.setTotleLmt(new BigDecimal(1000));
    }
    @Test//正常情况
    public void A_createDebitAndCredit()throws OverloadException {
        //System.out.println("1         " +lender.getCurrentLmt());
        DebitAndCredit debitAndCredit = DebitAndCreditFactory.createDebitAndCredit(product,lender,new BigDecimal(500),"2");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH,1);
        Date date = calendar.getTime();
        Assert.assertEquals("currentLmt Error",new BigDecimal("500"),lender.getCurrentLmt());
        Assert.assertEquals("lastPayDate Error", simpleDateFormat.format(date),simpleDateFormat.format(debitAndCredit.getLastRePayDate()));
    }

    @Test(expected = OverloadException.class)//超过个人额度限制
    public void B_createDebitAndCredit ()throws OverloadException {
//        System.out.println("2        "+ lender.getCurrentLmt());
        DebitAndCreditFactory.createDebitAndCredit(product,lender,new BigDecimal("501"),"2");
    }

    @Test(expected = OverloadException.class)//超过产品额度限制
    public void C_createDebitAndCredit ()throws OverloadException {
//        System.out.println("3");
        DebitAndCreditFactory.createDebitAndCredit(product,lender,new BigDecimal("1001"),"2");
    }
}