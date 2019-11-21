package cn.kanyun.cpa.dao.user.impl;

import cn.kanyun.cpa.dao.CommonDaoImpl;
import cn.kanyun.cpa.dao.user.UserCommentDao;
import cn.kanyun.cpa.model.entity.user.UserComment;
import org.springframework.stereotype.Repository;

/**
 * @author Kanyun
 */
@Repository(UserCommentDao.REPOSITORY_NAME)
public class UserCommentDaoImpl extends CommonDaoImpl<Long, UserComment> implements UserCommentDao {

    /**
     * 通过调用父类的构造函数指定clazz值，即实体类的类型
     */
    public UserCommentDaoImpl() {
        super(UserComment.class);
    }
    /**
     * 保留指定clatt值的接口【通过子类显示调用父类的构造函数来指定】
     *
     * @param clatt
     */
    public UserCommentDaoImpl(Class<UserComment> clatt) {
        super(clatt);
    }
}

