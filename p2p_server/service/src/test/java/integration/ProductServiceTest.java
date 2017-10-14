package integration;

import com.newland.financial.p2p.Application;
import com.newland.financial.p2p.dao.IInterestDao;
import com.newland.financial.p2p.dao.IOrganizationDao;
import com.newland.financial.p2p.dao.IProductDao;
import com.newland.financial.p2p.domain.entity.Interest;
import com.newland.financial.p2p.domain.entity.Product;
import com.newland.financial.p2p.service.IProductService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ProductServiceTest {
    @Autowired
    private IProductService productService;
    @Autowired
    private IProductDao productDao;
    @Autowired
    private IInterestDao iInterestDao;
    @Autowired
    private IOrganizationDao organizationDao;
    @Before
    public void setUpClass() {

    }
    @Test
    public void A_insertProduct(){
        String jsonStr = "{\"proId\":\"cs001\",\"proName\":\"banana\",\"proLmt\":10000,\"interestList\":[{\"times\":3},{\"times\":6}],\"proNameOperator\":\"banana\",\"sponsor\":\"banana\",\"sprProName\":\"banana\",\"maxLmt\":100000,\"role\":\"1\",\"orgs\":[{\"organization\":\"cs007\"},{\"organization\":\"cs008\"}],\"repayMhd\":\"1\",\"interestMhd\":\"1\",\"cutMhd\":\"1\",\"advanceRepay\":\"1\",\"poundage\":\"1\",\"formula\":\"5+1=6\",\"isLatefee\":\"1\",\"latefee\":20,\"positiveOrNegative\":\"1\"}";
        productService.insertProduct(jsonStr);
        Product product = (Product) productService.getProduct("cs001");
        List<Interest> list = product.getInterestList();
        List list1 = product.getOrganizationsList();
        Assert.assertEquals("product name not same","banana",product.getProName());
        Assert.assertEquals("interest length not same",2,list.size());
        Assert.assertEquals("organization length not same",2,list1.size());
        Assert.assertEquals("proId not same","cs001",product.getProId());
        Assert.assertEquals("proLmt not same",new BigDecimal("10000"),product.getProLmt());
        Assert.assertEquals("proNameOperator not same","banana",product.getProNameOperator());
        Assert.assertEquals("sponsor not same","banana",product.getSponsor());
        Assert.assertEquals("sprProName not same","banana",product.getSprProName());
        Assert.assertEquals("maxLmt not same",new BigDecimal("100000"),product.getMaxLmt());
        Assert.assertEquals("role not same","1",product.getRole());
        Assert.assertEquals("repayMhd not same","1",product.getRepayMhd());
        Assert.assertEquals("interestMhd not same","1",product.getInterestMhd());
        Assert.assertEquals("cutMhd not same","1",product.getCutMhd());
        Assert.assertEquals("advanceRepay not same","1",product.getAdvanceRepay());
        Assert.assertEquals("poundage not same","1",product.getPoundage());
        Assert.assertEquals("formula not same","5+1=6",product.getFormula());
        Assert.assertEquals("isLatefee not same","1",product.getIsLatefee());
        Assert.assertEquals("latefee not same",new BigDecimal("20.00"),product.getLatefee());
    }

    @Test
    public void B_testUpdatePutAndDown() {
        String proId = "cs001";
        String putAndDown = "1";
        productService.updatePutAndDown(proId,putAndDown);
        Product product = (Product) productService.getProduct("cs001");
        Assert.assertEquals("putAndDown not same","1",product.getPutAndDown());
    }
    @Test
    public void deleteData(){
        productDao.deleteProduct("cs001");
        iInterestDao.deleteInterestByProId("cs001");
        organizationDao.deleteOrganization("cs001");
    }
}
