package cn.kanyun.cpa.dao;

import org.hibernate.context.spi.CurrentTenantIdentifierResolver;

/**
 * Created by kanyun on 2019/5/31.
 * 自定义如何解析出tenant_id 用以支持 多租户
 */
public class CpaTenantIdentifierResolver implements CurrentTenantIdentifierResolver {
    @Override
    public String resolveCurrentTenantIdentifier() {
        return null;
    }

    @Override
    public boolean validateExistingCurrentSessions() {
        return false;
    }
}
