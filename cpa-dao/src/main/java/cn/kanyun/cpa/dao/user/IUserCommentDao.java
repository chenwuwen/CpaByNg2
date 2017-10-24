package cn.kanyun.cpa.dao.user;

import cn.kanyun.cpa.dao.ICommonDao;
import cn.kanyun.cpa.model.entity.user.UserComment;

/**
 * Created by KANYUN on 2017/10/24.
 */
public interface IUserCommentDao extends ICommonDao<Integer,UserComment> {
    public static final String REPOSITORY_NAME="cn.kanyun.cpa.dao.user.impl.UserCommentDaoImpl";

}
