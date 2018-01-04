import cn.kanyun.cpa.dao.common.DynamicDataSource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.SQLException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class TestDynamicDataSource {
    @Autowired
    ApplicationContext context;
    @Test
    public void testContext() throws SQLException {
        DynamicDataSource ds = (DynamicDataSource) context.getBean("dynamicDataSource");
        System.out.println("loginTimeOutValue:"+ds.getLoginTimeout());
    }
}
