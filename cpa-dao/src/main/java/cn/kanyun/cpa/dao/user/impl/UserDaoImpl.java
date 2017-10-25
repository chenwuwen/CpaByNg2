package cn.kanyun.cpa.dao.user.impl;

import cn.kanyun.cpa.dao.CommonDaoImpl;
import cn.kanyun.cpa.dao.user.IUserDao;
import cn.kanyun.cpa.model.entity.user.CpaUser;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

@Repository(IUserDao.REPOSITORY_NAME)
public class UserDaoImpl extends CommonDaoImpl<Integer, CpaUser> implements IUserDao {
    //通过调用父类的构造函数指定clazz值，即实体类的类类型
    public UserDaoImpl() {
        super(CpaUser.class);
    }

    @Override
    public CpaUser findByUserName(String userName) {
        Session session = getSession();
        String hql = " from CpaUser o where o.userName = :userName";
        Query query = session.createQuery(hql);
        query.setParameter("userName", userName);
        return (CpaUser) query.uniqueResult();
    }
}

