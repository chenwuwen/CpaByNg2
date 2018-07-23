package cn.kanyun.cpa.service.system;

import cn.kanyun.cpa.dao.common.annotation.DataSource;
import cn.kanyun.cpa.model.entity.system.CpaPermission;
import cn.kanyun.cpa.model.entity.system.RolePermission;
import cn.kanyun.cpa.service.CommonService;

import java.util.Set;

/**
 *
 * @author Administrator
 * @date 2017/6/16
 */
public interface RolePermissionService extends CommonService<Integer, RolePermission> {
    String SERVICE_NAME = "cn.kanyun.cpa.service.system.impl.RolePermissionServiceImpl";

    @DataSource(targetDataSource = "slave")
    Set<CpaPermission> findPermissionByRoleId(Set roleIds);

}
