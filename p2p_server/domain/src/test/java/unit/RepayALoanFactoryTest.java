/*package unit;

import com.newland.financial.p2p.domain.entity.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RepayALoanFactoryTest {
    private Lender lender;
    private Product product;
    @Before
    public void setUpClass() {
        lender = new Lender();
        lender.setUserId("2222222");
        product = new Product();
        product.setProId("4");
        product.setProName("星贷");
        product.setProLmt(new BigDecimal("10000"));
        List<Interest> list = new ArrayList<Interest>();
        Interest interest = new Interest();
        interest.setIntRate(new BigDecimal("1.6"));
        interest.setIProId("4");
        interest.setIProName("星贷");
//        interest.setIttId("2");
        interest.setTimes(1);
        list.add(interest);
        product.setInterestList(list);
        product.setPayDate(new Date());
    }

    @Test
    public void createRepayAloan(){

        List<RepayALoan> list =  RepayALoanFactory.createRepayAloan(product,lender,new BigDecimal("5000"),2,"1");
        RepayALoan repayALoan = list.get(0);
        BigDecimal money = repayALoan.getCrtRe();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date d1 = repayALoan.getCreateDate();
        //System.out.println("repayALoan date"+ sdf.format(d1));
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH,1);
        Date d2 = calendar.getTime();
        //System.out.println("test date"+ sdf.format(d2));
        Assert.assertEquals("the date not equals",sdf.format(d2),sdf.format(d1));
        Assert.assertEquals("the repayMoney not equals",new BigDecimal("5000.00"),money);
    }
}*/