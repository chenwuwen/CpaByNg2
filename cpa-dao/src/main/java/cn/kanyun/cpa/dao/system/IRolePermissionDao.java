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

    /**
     *@Author: zhaoyingxu
     *@Description: 根据角色id获取权限
     *@Date: 2017/8/16 15:38
     *@params:
     */
    Set<RolePermission> findPermissionByRoleId(Set roleIds);
}
