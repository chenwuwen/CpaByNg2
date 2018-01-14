package cn.kanyun.cpa.dao.user;


import cn.kanyun.cpa.dao.CommonDao;
import cn.kanyun.cpa.model.entity.user.CpaUser;

public interface UserDao extends CommonDao<Long,CpaUser> {
	public static final String REPOSITORY_NAME="cn.kanyun.cpa.dao.user.impl.UserDaoImpl";

	/**
	 *@Author: kanyun
	 *@Description: 根据用户名获取用户
	 *@Date: 2017/8/16 15:37
	 *@params:
	 */
	CpaUser findByUserName(String userName);
}
