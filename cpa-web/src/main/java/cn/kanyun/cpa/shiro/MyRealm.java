package cn.kanyun.cpa.shiro;

import cn.kanyun.cpa.model.entity.system.UserRole;
import cn.kanyun.cpa.model.entity.user.CpaUser;
import cn.kanyun.cpa.service.system.IRolePermissionService;
import cn.kanyun.cpa.service.system.IUserRoleService;
import cn.kanyun.cpa.service.user.IUserService;
import net.sf.ehcache.CacheManager;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.util.ByteSource;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.Set;

/**
 * 权限认证
 * <p>
 * Created by Administrator on 2017/6/14.
 */
public class MyRealm extends AuthorizingRealm {
    //由于shiro是在其他service之前加载，所以必须在运行时加载
    @Resource
    private IUserService userService;
    @Resource
    private IUserRoleService userRoleService;
    @Resource
    private IRolePermissionService rolePermissionService;

    private org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(MyRealm.class);

    public MyRealm() {
    }

    public MyRealm(CacheManager cacheManager, CredentialsMatcher matcher) {
        super((org.apache.shiro.cache.CacheManager) cacheManager, matcher);
    }

    /**
     * Shiro登录认证(原理：用户提交 用户名和密码  --- shiro 封装令牌 ---- realm 通过用户名将密码查询返回 ---- shiro 自动去比较查询出密码和用户输入密码是否一致---- 进行登陆控制 )
     * 认证回调函数,登录时调用.经测试登陆时会调用两次
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(
            AuthenticationToken token) throws AuthenticationException {
        logger.info("=================Shiro开始登录认证===================");
        UsernamePasswordToken upToken = (UsernamePasswordToken) token;
        CpaUser user = userService.findByUserName(upToken.getUsername());
        // 账号不存在
        if (user == null || 0 == user.getStatus()) {
            throw new UnknownAccountException(); //没找到用户,或用户被删除
        } else if (user.getStatus() == 2) {
            // 账号状态异常
            throw new LockedAccountException(); //账号被锁定
        } else {
//            盐值：取用户信息中盐值字段的值(随机值)，避免由于两个用户原始密码相同，加密后的密码也相同
            ByteSource credentialsSalt = ByteSource.Util.bytes(user.getUserName() + user.getSalt());
            //若存在，将此用户存放到登录认证info中
            return new SimpleAuthenticationInfo(user.getUserName(), user.getPassword(), credentialsSalt, this.getName());
        }

    }

    /**
     * Shiro权限认证 授权查询回调函数, 进行鉴权但缓存中无用户的授权信息时调用.
     * 当用户进行访问链接时的授权方法 将权限查出来
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(
            PrincipalCollection principals) {
        logger.info("===========检测权限=========");

        String userName = (String) principals.getPrimaryPrincipal();
        CpaUser user = userService.findByUserName(userName);
        if (user != null) {
            SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
            //角色名集合
//            Set<String> roles = userRoleService.findRoleByUserId(user.getId());
            Set<String> roles = userRoleService.findRoleByUser(user);
            //权限名的集合
//            Set<String> permissions = userRoleService.findPermissionByUerId(user.getId());
            Set<String> permissions = userRoleService.findPermissionByUer(user);
            info.setRoles(roles);
            info.addStringPermissions(permissions);
            return info;
        }
        return null;
    }

    @Override
    public void onLogout(PrincipalCollection principals) {
        super.clearCachedAuthorizationInfo(principals);
        CpaUser user = (CpaUser) principals.getPrimaryPrincipal();
        removeUserCache(user);
    }

    /**
     * 清除用户缓存
     *
     * @param CpaUser
     */
    public void removeUserCache(CpaUser user) {
        removeUserCache(user.getUserName());
    }

    /**
     * 清除用户缓存
     *
     * @param userName
     */
    public void removeUserCache(String userName) {
        SimplePrincipalCollection principals = new SimplePrincipalCollection();
        principals.add(userName, super.getName());
        super.clearCachedAuthenticationInfo(principals);
    }
}


