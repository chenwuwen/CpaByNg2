package cn.kanyun.cpa.service.user.impl;

import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.annotation.Resource;

import cn.kanyun.cpa.dao.user.IUserDao;
import cn.kanyun.cpa.model.entity.CpaResult;
import cn.kanyun.cpa.model.entity.user.CpaUser;
import cn.kanyun.cpa.service.CommonServiceImpl;
import cn.kanyun.cpa.service.user.IUserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service(IUserService.SERVICE_NAME)
@Transactional
public class UserServiceImpl extends CommonServiceImpl<Integer, CpaUser> implements IUserService {
    @Resource(name = IUserDao.REPOSITORY_NAME )
    private IUserDao userdao;

    /*检测用户登陆*/
    /*2017.7用户登录由shiro接管*/
//    public CpaResult checkLogin(String username, String password) {
//        Object[] params = {username};
//        String md5_pwd = null;
//        try {
//            md5_pwd = MD5util.md5(password);
//        } catch (NoSuchAlgorithmException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//        String where = "o.username = ?";
//        CpaUser user = null;
//        CpaResult result = userdao.getScrollData(-1, -1, where, params);
//        if (result.getTotalCount() == 1) {
//            List list = (List) result.getData();
//            user = (CpaUser) list.get(0);
//            if (user.getPassword().equals(md5_pwd)) {
//                result.setStatus(1);
//                result.setData(user);
//            } else {
//                result.setStatus(2);
//                result.setMsg("密码错误！");
//            }
//        } else {
//            result.setStatus(0);
//            result.setMsg("此用户不存在！");
//        }
//
//        return result;
//    }

    @Override
    public CpaUser findByUserName(String userName) {
        CpaUser user = userdao.findByUserName(userName);
        return user;
    }

}
