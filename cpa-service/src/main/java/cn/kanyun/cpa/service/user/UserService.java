package cn.kanyun.cpa.service.user;


import cn.kanyun.cpa.model.dto.user.CpaUserDto;
import cn.kanyun.cpa.model.entity.user.CpaUser;
import cn.kanyun.cpa.service.CommonService;

public interface UserService extends CommonService<Long, CpaUser> {
    public static final String SERVICE_NAME = "cn.kanyun.cpa.service.user.impl.UserServiceImpl";
//	CpaResult checkLogin(String username, String password);

    /**
     * @describe: 根据用户名,查找用户.
     * @params:
     * @Author: Kanyun
     * @Date: 2017/8/16 14:37
     */
    CpaUser findByUserName(String userName);

    /**
     * @param
     * @return
     * @author Kanyun
     * @Description: 保存用户
     * @date 2017/11/13 14:36
     */
    CpaUser saveUser(CpaUserDto serDto);
}
