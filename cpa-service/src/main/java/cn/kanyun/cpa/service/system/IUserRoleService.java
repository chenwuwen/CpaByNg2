package cn.kanyun.cpa.service.system;


import cn.kanyun.cpa.model.entity.system.UserRole;
import cn.kanyun.cpa.model.entity.user.CpaUser;
import cn.kanyun.cpa.service.ICommonService;

import java.util.Set;

/**
 * Created by Administrator on 2017/6/16.
 */
public interface IUserRoleService extends ICommonService<Integer, UserRole> {
    public static final String SERVICE_NAME = "cn.kanyun.cpa.service.system.impl.UserRoleServiceImpl";

    Set<String> findRoleByUserId(Integer userId);

    Set<String> findPermissionByUerId(Integer userId);

    /**
     * @param
     * @return
     * @author Kanyun
     * @Description: 通过用户对象获取用户角色名称集合(之前方法已弃用(主要是之前一直采用的mybatis的那种写法), 由于表是主外键关联, 同时hibernate也配置了关联关系，所以采用新方式)
     * @date 2017/11/14 14:25
     */
    Set<String> findRoleByUser(CpaUser cpaUser);

    /**
     * @param
     * @return
     * @author Kanyun
     * @Description: 通过用户对象获取用户权限名称集合(之前方法已弃用(主要是之前一直采用的mybatis的那种写法), 由于表是主外键关联, 同时hibernate也配置了关联关系，所以采用新方式)
     * @date 2017/11/14 14:28
     */
    Set<String> findPermissionByUer(CpaUser cpaUser);

}
