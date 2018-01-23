package cn.kanyun.cpa.dao.user;


import cn.kanyun.cpa.dao.CommonDao;
import cn.kanyun.cpa.model.dto.user.CpaUserDto;
import cn.kanyun.cpa.model.entity.CpaResult;
import cn.kanyun.cpa.model.entity.user.CpaUser;

import java.util.LinkedHashMap;

public interface UserDao extends CommonDao<Long,CpaUser> {
	public static final String REPOSITORY_NAME="cn.kanyun.cpa.dao.user.impl.UserDaoImpl";

	/**
	 *@Author: kanyun
	 *@Description: 根据用户名获取用户
	 *@Date: 2017/8/16 15:37
	 *@params:
	 */
	CpaUser findByUserName(String userName);

	/**
	 * @describe: 根据条件获取用户列表
	 * @params: cpaUserDto 查询参数(参数都封装在dto中) orderby 可选值,默认按id倒叙排
	 * @Author: Kanyun
	 * @Date: 2018/1/23 0023 11:04
	 */
	CpaResult findCpaUserByCondition(CpaUserDto cpaUserDto, LinkedHashMap orderby);
}
