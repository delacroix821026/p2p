package unit;

import com.newland.financial.p2p.domain.entity.Interest;
import com.newland.financial.p2p.domain.entity.Product;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.math.BigDecimal;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ProductTest {
    private Product product;
    @Before
    public void setUpClass() {
        product = new Product();
        product.setProId("P1");
        product.getInterestList();
        product.setInterestList(new ArrayList<Interest>());
        Interest interest1 = new Interest();
//        interest1.setIttId("I1");
        interest1.setIProId(product.getProId());
        interest1.setIntRate(new BigDecimal(19));
        interest1.setTimes(3);
        interest1.setIProName("三期");

        Interest interest2 = new Interest();
        interest2.setIProId(product.getProId());
//        interest2.setIttId("I2");
        interest2.setIntRate(new BigDecimal(19));
        interest2.setTimes(6);
        interest2.setIProName("六期");

        Interest interest3 = new Interest();
        interest3.setIProId(product.getProId());
//        interest3.setIttId("I3");
        interest3.setIntRate(new BigDecimal(19));
        interest3.setTimes(12);
        interest3.setIProName("十二期");

        product.getInterestList().add(interest1);
        product.getInterestList().add(interest2);
        product.getInterestList().add(interest3);
    }

    @Test
    public void getInterestB() {
        System.out.println("B");
        assertEquals("getInterest need be 19",product.getInterest("I1"), new BigDecimal(19));

    }

    @Test
    public void getInterestANoFound() {
        System.out.println("A");
        assertEquals("getInterest need be 0",product.getInterest("Ixxxx1"), new BigDecimal(0));
    }
}
