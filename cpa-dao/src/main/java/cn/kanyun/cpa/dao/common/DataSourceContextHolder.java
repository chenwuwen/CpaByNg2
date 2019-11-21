package cn.kanyun.cpa.dao.common;

/**
 * 定义一个可以设置当前线程的变量的工具类，用于设置对应的数据源名称
 * 用于存放数据源，然后给其他类得到，保证同一线程下数据不会出错
 * @author  Kanyun
 * @date  2017/12/7 0007.
 */
public class DataSourceContextHolder {

    /**
     * 主数据源
     */
    public static final String DATA_SOURCE_MASTER = "master";
    /**
     * 从数据源
     */
    public static final String DATA_SOURCE_SLAVE = "slave";

    private static final ThreadLocal<String> dataSourceContextHolder = new ThreadLocal<String>();
    private static final ThreadLocal<String> sessionFactoryContextHolder = new ThreadLocal<String>();

    /**
     * @param dataSourceType 数据库类型
     * @return void
     * @throws
     * @Description: 设置数据源类型
     */
    public static void setDataSourceType(String dataSourceType) {
        dataSourceContextHolder.set(dataSourceType);
    }

    /**
     * @param
     * @return String
     * @throws
     * @Description: 获取数据源类型
     */
    public static String getDataSourceType() {
        return dataSourceContextHolder.get();
    }

    /**
     * @param
     * @return void
     * @throws
     * @Description: 清除数据源类型
     */
    public static void clearDataSourceType() {
        dataSourceContextHolder.remove();
    }

    /**
     * @describe: 设置SessionFactory类型
     * @params:
     * @Author: Kanyun
     * @Date: 2018/1/3 0003 10:53
     */
    public static void setSessionFactoryType(String sessionFactoryType) {
        sessionFactoryContextHolder.set(sessionFactoryType);
    }

    /**
     * @describe: 获取SessionFactory类型
     * @params:
     * @Author: Kanyun
     * @Date: 2018/1/3 0003 10:54
     */
    public static String getSessionFactoryType() {
        return sessionFactoryContextHolder.get();
    }

    /**
     * @describe: 清除SessionFactory类型
     * @params:
     * @Author: Kanyun
     * @Date: 2018/1/3 0003 10:55
     */
    public static void clearSessionFactoryType() {
        sessionFactoryContextHolder.remove();
    }
}
