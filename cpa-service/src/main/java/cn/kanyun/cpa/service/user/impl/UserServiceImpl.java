package cn.kanyun.cpa.service.user.impl;

import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import cn.kanyun.cpa.dao.system.IUserRoleDao;
import cn.kanyun.cpa.dao.user.IUserDao;
import cn.kanyun.cpa.model.dto.user.CpaUserDto;
import cn.kanyun.cpa.model.entity.CpaResult;
import cn.kanyun.cpa.model.entity.system.CpaRole;
import cn.kanyun.cpa.model.entity.system.UserRole;
import cn.kanyun.cpa.model.entity.user.CpaUser;
import cn.kanyun.cpa.service.CommonServiceImpl;
import cn.kanyun.cpa.service.user.IUserService;
import cn.kanyun.cpa.util.EndecryptUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service(IUserService.SERVICE_NAME)
@Transactional
public class UserServiceImpl extends CommonServiceImpl<Integer, CpaUser> implements IUserService {
    @Resource(name = IUserDao.REPOSITORY_NAME)
    private IUserDao userDao;
    @Resource(name = IUserRoleDao.REPOSITORY_NAME)
    private IUserRoleDao userRoleDao;

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
//        CpaResult result = userDao.getScrollData(-1, -1, where, params);
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
        CpaUser user = userDao.findByUserName(userName);
        return user;
    }

    @Override
    @Transactional(rollbackFor={RuntimeException.class, Exception.class})
    public CpaUser saveUser(CpaUserDto userDto) {
        /*构建CpaUser*/
        CpaUser user = new CpaUser();
        user.setEmail(userDto.getEmail());
        user.setUserName(userDto.getUserName());
        userDto = EndecryptUtils.md5Password(userDto.getUserName(), userDto.getPassword());
        user.setRegDate(new Timestamp(System.currentTimeMillis()));
        user.setSalt(userDto.getSalt());
        user.setPassword(userDto.getPassword());
        user.setStatus(1);

        /*构建CpaRole,但并不会保存CpaRole,因为没有配置级联关系,级联关系为默认*/
        CpaRole role= new CpaRole();
        role.setId(3);

        /*构建UserRole*/
        Set userRoles = new HashSet<>();
        UserRole userRole = new UserRole();
        userRole.setCpaUser(user);
        userRole.setCpaRole(role);
        userRoles.add(userRole);

        user.setUserRoles(userRoles);
        /*直接保存CpaUser主表,这样可以把关联表UserRole保存了*/
        userDao.save(user);
        return user;
    }


}
