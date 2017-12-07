package cn.kanyun.cpa.dao.common;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * 定义一个类继承AbstractRoutingDataSource实现determineCurrentLookupKey方法，该方法可以实现数据库的动态切换
 * Created by Administrator on 2017/12/7 0007.
 */
public class DynamicDataSource extends AbstractRoutingDataSource {
    @Override
    protected Object determineCurrentLookupKey() {
        return DataSourceContextHolder.getDataSourceType();
    }
}
