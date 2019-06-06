package cn.kanyun.cpa.dao.common.tenant;

import com.alibaba.druid.pool.DruidDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import javax.sql.DataSource;
import java.util.Map;

/**
 * 数据源提供类,本质上是一个Map[主要是把所有的数据源进行缓存],也可以查询数据库
 * 但是这样的话,每一个请求都需要查询数据库再组装数据源,所以一般是查出所有的数据源
 * 进行缓存,如何查出所有的数据源？可以通过查询数据库,也可以读取配置文件中配置的所有数据源
 *
 * @author Kanyun
 * @date 2019/6/4
 */
@Slf4j
public class TenantDataSourceProvider implements ApplicationContextAware {

    private static ApplicationContext applicationContext;


    @Override
    public void setApplicationContext(ApplicationContext context) throws BeansException {
        context = applicationContext;
    }

    /**
     * 获取applicationContext对象
     *
     * @return
     */
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    /**
     * 根据bean的id来查找对象
     *
     * @param id
     * @return
     */
    public static Object getBeanById(String id) {
        return applicationContext.getBean(id);
    }

    /**
     * 根据bean的class来查找对象
     *
     * @param c
     * @return
     */
    public static Object getBeanByClass(Class c) {
        return applicationContext.getBean(c);
    }

    /**
     * 根据bean的class来查找所有的对象(包括子类)
     *
     * @param c
     * @return
     */
    public static Map getBeansByClass(Class c) {
        return applicationContext.getBeansOfType(c);
    }

    /**
     * 这里假定配置文件中的数据源id是租户id
     * 所以可以利用spring ioc容器,从中取出数据源
     * 如果租户id不是数据源id,也可以做一个映射关系,来获取数据源
     * 因为使用了spring ioc容器,所以相当于省去了自己创建map
     *
     * @param s
     * @return
     */
    public static DataSource getTenantDataSource(String s) {
        Object o = getBeanById(s);
//        这里不使用instanceof是因为它只能判断是否是一个类的实例对象,如果一个项目中使用了不同类型的数据源,那么这样会产生问题
//        if (o instanceof DruidDataSource) {
//        isAssignableFrom用来判断一个类,是否是另一个类或其子类的父类,isAssignableFrom后面放要判断的类,前面放指定的超类
            if (DataSource.class.isAssignableFrom(o.getClass())) {
            log.info("根据租户ID：{},找到了对应的数据源", s);
            return (DruidDataSource) o;
        }
        log.info("根据租户ID：{},没有找到对应的数据源,将返回Null", s);
        return null;
    }
}
