package cn.kanyun.cpa.dao.user;

import cn.kanyun.cpa.dao.CommonDao;
import cn.kanyun.cpa.model.entity.user.UserComment;

/**
 *
 * @author KANYUN
 * @date 2017/10/24
 */
public interface UserCommentDao extends CommonDao<Long, UserComment> {
    String REPOSITORY_NAME = "cn.kanyun.cpa.dao.user.impl.UserCommentDaoImpl";

}
