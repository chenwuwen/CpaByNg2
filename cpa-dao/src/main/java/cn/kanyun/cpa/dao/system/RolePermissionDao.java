package cn.kanyun.cpa.dao.system;

import cn.kanyun.cpa.dao.CommonDao;
import cn.kanyun.cpa.model.entity.system.RolePermission;

import java.util.Set;

/**
 * @author Kanyun
 * @date 2017/6/16
 */
public interface RolePermissionDao extends CommonDao<Integer, RolePermission> {
    String REPOSITORY_NAME = "cn.kanyun.cpa.dao.system.impl.RolePermissionDaoImpl";

    /**
     * @Author: Kanyun
     * @Description: 根据角色id获取权限
     * @Date: 2017/8/16 15:38
     * @params:
     */
    Set<RolePermission> findPermissionByRoleId(Set roleIds);
}
