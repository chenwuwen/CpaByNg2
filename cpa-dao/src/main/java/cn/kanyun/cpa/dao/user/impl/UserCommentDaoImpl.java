package cn.kanyun.cpa.dao.user.impl;

import cn.kanyun.cpa.dao.CommonDaoImpl;
import cn.kanyun.cpa.dao.user.IUserCollectDao;
import cn.kanyun.cpa.dao.user.IUserCommentDao;
import cn.kanyun.cpa.model.entity.user.UserCollect;
import cn.kanyun.cpa.model.entity.user.UserComment;
import org.springframework.stereotype.Repository;

@Repository(IUserCommentDao.REPOSITORY_NAME)
public class UserCommentDaoImpl extends CommonDaoImpl<Integer, UserComment> implements IUserCommentDao {
    //通过调用父类的构造函数指定clazz值，即实体类的类类型
    public UserCommentDaoImpl() {
        super(UserComment.class);
    }


}

