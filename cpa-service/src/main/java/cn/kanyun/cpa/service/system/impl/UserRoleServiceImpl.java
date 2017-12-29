package cn.kanyun.cpa.service.system.impl;

import cn.kanyun.cpa.dao.system.RolePermissionDao;
import cn.kanyun.cpa.dao.system.UserRoleDao;
import cn.kanyun.cpa.model.entity.system.RolePermission;
import cn.kanyun.cpa.model.entity.system.UserRole;
import cn.kanyun.cpa.model.entity.user.CpaUser;
import cn.kanyun.cpa.service.CommonServiceImpl;
import cn.kanyun.cpa.service.system.UserRoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Administrator on 2017/6/16.
 */
@Service(UserRoleService.SERVICE_NAME)
public class UserRoleServiceImpl extends CommonServiceImpl<Integer, UserRole> implements UserRoleService {
    @Resource(name = UserRoleDao.REPOSITORY_NAME)
    private UserRoleDao userRoleDao;
    @Resource(name = RolePermissionDao.REPOSITORY_NAME)
    private RolePermissionDao rolePermissionDao;

    @Override
    public Set<String> findRoleByUserId(Integer userId) {
        Set<UserRole> setUserRoles = userRoleDao.findRoleByUserId(userId);
        Set<String> set = new HashSet<>();
        for (UserRole userRole : setUserRoles) {
//            set.add(String.valueOf(role.getId())); //shiro中要的是roleName不是roleId
            set.add(userRole.getCpaRole().getRoleName());
        }
        return set;
    }

    @Override
    public Set<String> findRoleByUser(CpaUser cpaUser) {
        Set<UserRole> userRoles = cpaUser.getUserRoles();
        Set<String> roles = new HashSet<>();
        for (UserRole userRole : userRoles) {
            roles.add(userRole.getCpaRole().getRoleName());
        }
        return roles;
    }

    @Override
    public Set<String> findPermissionByUer(CpaUser cpaUser) {
        Set<UserRole> userRoles = cpaUser.getUserRoles();
        Set<String> permissions = new HashSet<>();
        for (UserRole userRole : userRoles) {
            Set<RolePermission> rolePermissions = userRole.getCpaRole().getRolePermissions();
            for (RolePermission rolePermission : rolePermissions) {
                permissions.add(rolePermission.getCpaPermission().getPermissionCode());
            }
        }
        return permissions;
    }

    @Override
    public Set<String> findPermissionByUerId(Integer userId) {
        Set<UserRole> setUserRoles = userRoleDao.findRoleByUserId(userId);
        Set<Integer> roleIds = new HashSet<>();
        for (UserRole userRole : setUserRoles) {
            roleIds.add(userRole.getCpaRole().getId());
        }
        Set<RolePermission> setRolePermissions = rolePermissionDao.findPermissionByRoleId(roleIds);
        Set<String> set = new HashSet<>();
        for (RolePermission rolePermission : setRolePermissions) {

            set.add(rolePermission.getCpaPermission().getPermissionCode());
        }
        return set;
    }
}
