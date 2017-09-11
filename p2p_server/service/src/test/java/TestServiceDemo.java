import com.newland.financial.p2p.Application;
import com.newland.financial.p2p.domain.entity.Lender;
import com.newland.financial.p2p.service.ILenderService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class TestServiceDemo {

    @Autowired
    private ILenderService lenderService;

    @Before
    public void setUpClass() {

    }

    @Test
    public void testService() {
        Lender lender = lenderService.getLender("2222222");
        System.out.println("service::::::::::::::::::Lender-------------------------" + lender.toString());
        //assertEquals("lender ID must be 2222222",lender.getUserId(), "2222222");
    }
}
