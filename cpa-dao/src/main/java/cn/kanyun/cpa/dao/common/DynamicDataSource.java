package cn.kanyun.cpa.dao.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * 定义一个类继承AbstractRoutingDataSource实现determineCurrentLookupKey方法，该方法可以实现数据库的动态切换
 * 实现动态数据源主要是为了读写分离（数据库mysql需要设计成主从结构）,即增删改用master数据源,查使用Slaver数据源，这样减轻了
 * 一个数据库的压力，同时也避免了数据库中由于表级锁、行级锁的存在，导致查询速度慢，或者插入慢等情况
 * 那么在什么时候用Master(插入的数据源)，什么时候用Slaver（查询的数据源）。
 * AbstractRoutingDataSource就是这个控制器，只要我们实现它，然后重写他的抽象方法
 * 那么知道什么时候是插入、什么时候是查询；:在service方法中，自定义注解>>反射读取注解>>判断数据源>>分配数据源到 AbstractRoutingDataSource >>Spring给你装配到DAO
 * Created by Administrator on 2017/12/7 0007.
 * <p>
 * 该类会根据DataSourceContextHolder的TheadLocal得到存放在其中的数据源，内部加载的时候会根据存放的数据源得到数据源实例
 * 事务管理配置一定要配置在往DynamicDataSourceHolder 中注入数据源key之前，否则会报 Could not open JDBC Connection for transaction; nested exception is java.lang.IllegalStateException: Cannot determine target DataSource for lookup key [null] 找不到数据源错误
 */

/**
 * 该类会根据DataSourceContextHolder的TheadLocal得到存放在其中的数据源，内部加载的时候会根据存放的数据源得到数据源实例
 * 事务管理配置一定要配置在往DynamicDataSourceHolder 中注入数据源key之前，否则会报 Could not open JDBC Connection for transaction; nested exception is java.lang.IllegalStateException: Cannot determine target DataSource for lookup key [null] 找不到数据源错误
 */

/**
 * 这个抽象类是Spring为动态切换数据源准备的。要使用这个抽象类，
 * 一个是要重写determineCurrentLookupKey方法，告诉它当前要用哪个数据源；
 * 一是在Spring的配置文件中将几个数据源注入到targetDataSources属性中。
 * 本文中，该属性的形式为:HashMap<String,DataSource>
 * @author Kanyun
 */
public class DynamicDataSource extends AbstractRoutingDataSource {
    private static final Logger logger = LoggerFactory.getLogger(DynamicDataSource.class);


    @Override
    protected Object determineCurrentLookupKey() {
        logger.info("动态数据源===========" + DataSourceContextHolder.getDataSourceType());
        return DataSourceContextHolder.getDataSourceType();
    }
}
