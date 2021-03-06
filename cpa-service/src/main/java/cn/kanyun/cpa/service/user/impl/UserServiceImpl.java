package cn.kanyun.cpa.service.user.impl;

import cn.kanyun.cpa.dao.system.UserRoleDao;
import cn.kanyun.cpa.dao.user.UserDao;
import cn.kanyun.cpa.model.dto.user.CpaUserDto;
import cn.kanyun.cpa.model.entity.CpaResult;
import cn.kanyun.cpa.model.entity.system.CpaRole;
import cn.kanyun.cpa.model.entity.system.UserRole;
import cn.kanyun.cpa.model.entity.user.CpaUser;
import cn.kanyun.cpa.model.entity.user.CpaUserExtend;
import cn.kanyun.cpa.service.CommonServiceImpl;
import cn.kanyun.cpa.service.user.UserService;
import cn.kanyun.cpa.util.EndecryptUtils;
import cn.kanyun.cpa.util.MD5Util;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.*;

/**
 * @author Administrator
 */
@Service(UserService.SERVICE_NAME)
@Transactional
public class UserServiceImpl extends CommonServiceImpl<Long, CpaUser> implements UserService {
    @Resource(name = UserDao.REPOSITORY_NAME)
    private UserDao userDao;
    @Resource(name = UserRoleDao.REPOSITORY_NAME)
    private UserRoleDao userRoleDao;


    @Override
    public CpaResult checkLogin(String username, String password) {
        Object[] params = {username};
        String md5_pwd = null;
        try {
            md5_pwd = MD5Util.md5Encode(password);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        String where = "o.username = ?";
        CpaUser user = null;
        CpaResult result = userDao.getScrollData(-1, -1, where, params);
        if (result.getTotalCount() == 1) {
            List list = (List) result.getData();
            user = (CpaUser) list.get(0);
            if (user.getPassword().equals(md5_pwd)) {
                result.setStatus(1);
                result.setData(user);
            } else {
                result.setStatus(2);
                result.setMsg("密码错误！");
            }
        } else {
            result.setStatus(0);
            result.setMsg("此用户不存在！");
        }

        return result;
    }

    @Override
    public CpaUser findByUserName(String userName) {
        CpaUser user = userDao.findByUserName(userName);
        return user;
    }

    @Override
    @Transactional(rollbackFor = {RuntimeException.class, Exception.class})
    public CpaUser saveUser(CpaUserDto userDto, CpaUserExtend userExtend) {
        /*构建CpaUser*/
        CpaUser user;
        user = EndecryptUtils.md5Password(userDto.getUserName(), userDto.getPassword());
        user.setEmail(userDto.getEmail());
        user.setUserName(userDto.getUserName());
        user.setRegDate(LocalDateTime.now());
        user.setStatus(1);

        /*构建CpaRole,但并不会保存CpaRole,因为没有配置级联关系,级联关系为默认*/
        CpaRole role = new CpaRole();
        role.setId(3);

        /*构建UserRole*/
        Set userRoles = new HashSet<>();
        UserRole userRole = new UserRole();
        userRole.setCpaUser(user);
        userRole.setCpaRole(role);
        userRoles.add(userRole);

        /*构建CpaUserExtend*/
        userExtend.setCpaUser(user);

        /*设置CpaUser外键关联对*/
        user.setUserRoles(Collections.singleton(userRole));
        user.setCpaUserExtend(userExtend);

        user.setUserRoles(userRoles);
        /*直接保存CpaUser主表,这样可以把关联表UserRole保存了*/
        userDao.save(user);
        return user;
    }

    @Override
    public CpaResult findCpaUserByCondition(CpaUserDto cpaUserDto, LinkedHashMap orderby) {
        CpaResult result = userDao.findCpaUserByCondition(cpaUserDto, orderby);
        List<CpaUser> cpaUsers = (List<CpaUser>) result.getData();
        List<CpaUserDto> cpaUserDtos = new ArrayList<>();
        cpaUsers.forEach(cpaUser -> {
            CpaUserDto userDto = new CpaUserDto();
            BeanUtils.copyProperties(cpaUser, userDto);
            cpaUserDtos.add(userDto);
        });
        result.setData(cpaUserDtos);
        return result;
    }


}
