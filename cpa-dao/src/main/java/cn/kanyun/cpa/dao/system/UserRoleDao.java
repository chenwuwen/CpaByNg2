package cn.kanyun.cpa.dao.system;

import cn.kanyun.cpa.dao.CommonDao;
import cn.kanyun.cpa.model.entity.system.UserRole;

import java.util.Set;

/**
 * Created by Administrator on 2017/6/16.
 */
public interface UserRoleDao extends CommonDao<Integer,UserRole> {
    public static final String REPOSITORY_NAME="cn.kanyun.cpa.dao.system.impl.UserRoleDaoImpl";

    /**
     *@Author: kanyun
     *@Description: 根据用户ID获取用户角色
     *@Date: 2017/8/16 15:39
     *@params:
     */
    Set<UserRole> findRoleByUserId(Integer userId);
}
