package cn.kanyun.cpa.service.user;


import cn.kanyun.cpa.model.entity.CpaResult;
import cn.kanyun.cpa.model.entity.user.CpaUser;
import cn.kanyun.cpa.service.ICommonService;

public interface IUserService extends ICommonService<Integer,CpaUser> {
	public static final String SERVICE_NAME="cn.kanyun.cpa.service.user.impl.UserServiceImpl";
//	CpaResult checkLogin(String username, String password);
	CpaUser findByUserName(String userName);
}
