package cn.kanyun.cpa.dao.common.tenant;

import org.hibernate.engine.jdbc.connections.spi.AbstractDataSourceBasedMultiTenantConnectionProviderImpl;

import javax.sql.DataSource;

/**
 * Created by HLWK-06 on 2019/5/31.
 * 指定了 ConnectionProvider，即 Hibernate 需要知道如何以租户特有的方式获取数据连接
 * 这个类是Hibernate框架拦截sql语句并在执行sql语句之前更换数据源提供的类
 */
public class MultiTenantConnectionProvider extends AbstractDataSourceBasedMultiTenantConnectionProviderImpl {

    /**
     * 返回默认的数据源
     * 在没有提供tenantId的情况下返回默认数据源
     * @return
     */
    @Override
    protected DataSource selectAnyDataSource() {
        return null;
    }

    /**
     * 根据tenantIdentifier返回指定数据源
     * 提供了tenantId的话就根据ID来返回数据源
     * tenantId除了可以是在url中拼接参数,也可以配置在请求体或者请求头中,
     * 还可以是域名,例如：xiaohua.XXX.com xiaoming.XXX.com 这样每个域名就是一个租户,通过判断域名来选择使用哪个数据库
     * @param s  tenantId
     * @return
     */
    @Override
    protected DataSource selectDataSource(String s) {
        return null;
    }
}
