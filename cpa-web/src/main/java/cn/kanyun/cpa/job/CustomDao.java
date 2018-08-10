package cn.kanyun.cpa.job;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import javax.annotation.Resource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * @author Kanyun
 * @date 2018/8/9
 * @describe: 自定义CRUD操作, 使用JDBCTemplate，使用编程式事物
 */
@Slf4j
public class CustomDao {

    /**
     * 注入jdbcTemplate
     */
    @Resource
    private JdbcTemplate jdbcTemplate;

    /**
     * 数据库连接对象
     */
    private static Connection conn = null;

    /**
     * PlatformTransactionManager用于具体执行事务操作
     * 根据底层所使用的不同的持久化API或框架，PlatformTransactionManager 的主要实现类大致如下:
     * DataSourceTransactionManager：适用于JDBC和iBatis进行持久化操作的情况
     * HibernateTransactionManager：适用于使用Hiebenate进行数据持久化操作的情况
     * JpaTransactionManager：适用于使用JPA进行数据持久化操作的情况
     * 另外还有JtaTransactionManager 、JdoTransactionManager、JmsTransactionManager等等。
     */
    private static PlatformTransactionManager transactionManager;
    /**
     * 事务状态类，通过PlatformTransactionManager的getTransaction方法根据事务定义获取；
     * 返回的TransactionStatus对象可能代表一个新的或已经存在的事务(如果在当前调用堆栈有一个符合条件的事务)
     * 获取事务状态后，Spring根据传播行为来决定如何开启事务
     */
    private static TransactionStatus transactionStatus;

    public static void beginTransaction() {
//      TransactionDefinition它包含了事务的静态属性，比如：事务传播行为、超时时间等
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
//        实例化事物管理器,这里没有采用注入的方式,如果使用注入,则就不必再次实例化了
        transactionManager = new DataSourceTransactionManager();
        def.setIsolationLevel(TransactionDefinition.ISOLATION_READ_COMMITTED);
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        transactionStatus = transactionManager.getTransaction(def);
    }

    /**
     * @describe: 执行自定义的DAO
     * @params:
     * @Author: Kanyun
     * @Date: 2018/8/9 18:04
     */
    public void execCustomDao() {
        try {
            beginTransaction();
            conn = jdbcTemplate.getDataSource().getConnection();
            execSQL(null);
            transactionManager.commit(transactionStatus);
        } catch (SQLException e) {
            transactionManager.rollback(transactionStatus);
            e.printStackTrace();
        }
    }


    public void execSQL(List<String> sqls) {
        log.info("执行SQL");
    }
}
