package cn.kanyun.cpa.dao.common.tenant;

import org.hibernate.context.spi.CurrentTenantIdentifierResolver;

/**
 * Created by kanyun on 2019/5/31.
 * 自定义如何解析出tenant_id 用以支持 多租户
 * 这个类是由Hibernate提供的用于识别tenantId的类，当每次执行sql语句被拦截就会调用这个类中的方法来获取tenantId
 */
public class CpaTenantIdentifierResolver implements CurrentTenantIdentifierResolver {

    /**
     * 获取tenantId的逻辑在这个方法里面写,也可以通过获取访问的域名来判断租户
     * @return
     */
    @Override
    public String resolveCurrentTenantIdentifier() {
        return null;
    }

    @Override
    public boolean validateExistingCurrentSessions() {
        return true;
    }
}
