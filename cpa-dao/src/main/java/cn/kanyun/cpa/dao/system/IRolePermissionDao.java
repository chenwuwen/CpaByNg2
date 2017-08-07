package cn.kanyun.cpa.dao.system;

import cn.kanyun.cpa.dao.ICommonDao;
import cn.kanyun.cpa.model.entity.system.CpaPermission;
import cn.kanyun.cpa.model.entity.system.RolePermission;

import java.util.Set;

/**
 * Created by Administrator on 2017/6/16.
 */
public interface IRolePermissionDao extends ICommonDao<Integer,RolePermission> {
    public static final String REPOSITORY_NAME="cn.kanyun.cpa.dao.system.impl.RolePermissionDaoImpl";

    Set<RolePermission> findPermissionByRoleId(Set roleIds);
}
