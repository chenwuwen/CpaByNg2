package cn.kanyun.cpa.dao.common.tenant;

import cn.kanyun.cpa.model.constants.CpaConstants;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * 自定义如何解析出tenant_id 用以支持 多租户
 * 这个类是由Hibernate提供的用于识别tenantId的类，当每次执行sql语句被拦截就会调用这个类中的方法来获取tenantId
 *
 * @author Kanyun
 */
@Slf4j
public class TenantIdentifierResolverImpl implements CurrentTenantIdentifierResolver {

    private static HttpServletRequest request ;

    static {
        request = ((ServletRequestAttributes) (RequestContextHolder.currentRequestAttributes())).getRequest();
    }

    /**
     * 获取tenantId的逻辑在这个方法里面写,也可以通过获取访问的域名来判断租户
     *
     * @return
     */
    @Override
    public String resolveCurrentTenantIdentifier() {
        String domain_name = request.getScheme();
        log.info("当前访问域名是：{}",domain_name);
//        这里通过域名判断租户,每个租户有一个域名,其域名为二级域名,这里可以截取二级域名部分来判断是哪个租户,然后返回
        if (domain_name != null) {
//            return null;
        }
        return CpaConstants.DEFAULT_TENANT;
    }

    @Override
    public boolean validateExistingCurrentSessions() {
        return true;
    }
}
