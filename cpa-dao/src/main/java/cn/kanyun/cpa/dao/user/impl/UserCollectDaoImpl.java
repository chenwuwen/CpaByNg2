package cn.kanyun.cpa.dao.user.impl;

import cn.kanyun.cpa.dao.CommonDaoImpl;
import cn.kanyun.cpa.dao.user.UserCollectDao;
import cn.kanyun.cpa.model.entity.user.UserCollect;
import org.springframework.stereotype.Repository;

/**
 * @author Kanyun
 */
@Repository(UserCollectDao.REPOSITORY_NAME)
public class UserCollectDaoImpl extends CommonDaoImpl<Long, UserCollect> implements UserCollectDao {
    /**
     * 通过调用父类的构造函数指定clazz值，即实体类的类型
     */
    public UserCollectDaoImpl() {
        super(UserCollect.class);
    }


}

