package cn.kanyun.cpa.dao.system.impl;

import cn.kanyun.cpa.dao.CommonDaoImpl;
import cn.kanyun.cpa.dao.system.IUserRoleDao;
import cn.kanyun.cpa.model.entity.system.CpaRole;
import cn.kanyun.cpa.model.entity.system.UserRole;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Administrator on 2017/6/16.
 */
@Repository(IUserRoleDao.REPOSITORY_NAME)
public class UserRoleDaoImpl extends CommonDaoImpl<Integer,UserRole> implements IUserRoleDao {

    public UserRoleDaoImpl(){
        super(UserRole.class);
    }
    /**
     * 保留指定clatt值的接口【通过子类显示调用父类的构造函数来指定】
     *
     * @param clatt
     */
    public UserRoleDaoImpl(Class<UserRole> clatt) {
        super(clatt);
    }

    @Override
    public Set<UserRole> findRoleByUserId(Integer userId) {
        Session session = getSession();
        String hql = " from UserRole o where userId = :userId";
        Query query = session.createQuery(hql);
        query.setParameter("userId",userId);
        List<UserRole> listWithoutDup = new ArrayList<UserRole>(new HashSet<UserRole>(query.list()));
        Set<UserRole> set = new HashSet<UserRole>(listWithoutDup);
        return set;
    }
}
