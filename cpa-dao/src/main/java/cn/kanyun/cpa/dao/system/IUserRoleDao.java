package cn.kanyun.cpa.dao.system;

import cn.kanyun.cpa.dao.ICommonDao;
import cn.kanyun.cpa.model.entity.system.CpaRole;
import cn.kanyun.cpa.model.entity.system.UserRole;

import java.util.Set;

/**
 * Created by Administrator on 2017/6/16.
 */
public interface IUserRoleDao extends ICommonDao<Integer,UserRole> {
    public static final String REPOSITORY_NAME="cn.kanyun.cpa.dao.system.impl.UserRoleDaoImpl";

    Set<UserRole> findRoleByUserId(Integer userId);
}
