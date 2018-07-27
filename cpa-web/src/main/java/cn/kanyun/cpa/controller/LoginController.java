package cn.kanyun.cpa.controller;

import cn.kanyun.cpa.controller.user.UserController;
import cn.kanyun.cpa.model.constants.CpaConstants;
import cn.kanyun.cpa.model.constants.RoleConstants;
import cn.kanyun.cpa.model.dto.user.CpaUserDto;
import cn.kanyun.cpa.model.entity.CpaResult;
import cn.kanyun.cpa.model.entity.user.CpaUser;
import cn.kanyun.cpa.service.system.UserRoleService;
import cn.kanyun.cpa.service.user.UserService;
import cn.kanyun.cpa.util.JwtUtil;
import cn.kanyun.cpa.util.WebUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

/**
 * @author Administrator
 *         登录类
 */
@Controller
@RequestMapping("/api")
public class LoginController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Resource(name = UserService.SERVICE_NAME)
    private UserService userService;
    @Resource
    private UserRoleService userRoleService;

    /**
     * 首页
     *
     * @return
     */
    @GetMapping("/index")
    public String index() {
        return "/dist/index";
    }

    /**
     * GET 登录
     *
     * @return {String}
     */
    @GetMapping("/login")
    public String login() {
        logger.info("GET请求登录");
        if (SecurityUtils.getSubject().isAuthenticated()) {
            return "redirect:page/index";
        }
        return "index";
    }

    /**
     * @Author: kanyun
     * @Description: 用户登录，shiro
     * @Date: 2017/8/16 17:01
     * @params:
     */
    @PostMapping("/login")
    @ResponseBody
    public CpaResult login(CpaUserDto user, HttpServletRequest request) {
        CpaResult result = new CpaResult();
        String s_code = (String) request.getSession().getAttribute(CpaConstants.IDENTIFYING_CODE);
        // 先比较验证码(equalsIgnoreCase忽略大小写，equals不忽略)
        if (!s_code.equalsIgnoreCase(user.getValidateCode())) {
            result.setState(CpaConstants.OPERATION_ERROR);
            result.setMsg("验证码错误！");
        } else {
            //就是代表当前的用户
            Subject currentUser = SecurityUtils.getSubject();
            //获取基于用户名和密码的令牌
            //这里的token大家叫他令牌，也就相当于一张表格，你要去验证，你就得填个表，里面写好用户名密码，交给公安局的同志给你验证。
            UsernamePasswordToken token = new UsernamePasswordToken(
                    user.getUserName(), user.getPassword());
//      但是，“已记住”和“已认证”是有区别的：
//      已记住的用户仅仅是非匿名用户，你可以通过subject.getPrincipals()获取用户信息。但是它并非是完全认证通过的用户，当你访问需要认证用户的功能时，你仍然需要重新提交认证信息。
//      这一区别可以参考亚马逊网站，网站会默认记住登录的用户，再次访问网站时，对于非敏感的页面功能，页面上会显示记住的用户信息，但是当你访问网站账户信息时仍然需要再次进行登录认证。
            if ("on".equals(user.getIsRememberMe())) {
                token.setRememberMe(true);
            }
            try {
                // 回调doGetAuthenticationInfo，进行认证, 回调realm里的一个方法,验证用户
                currentUser.login(token);

                if (currentUser.hasRole(RoleConstants.ADMIN)) {
                    logger.info("角色为admin的用户: {} 于时间:{}  登录系统,,登录IP为:{}", user.getUserName(), LocalDateTime.now(), WebUtil.getClientAddr(request));
                } else if (currentUser.hasRole(RoleConstants.MANAGER)) {
                    logger.info("角色为manager的用户: {} 于时间:{}  登录系统,,登录IP为:{}", user.getUserName(), LocalDateTime.now(), WebUtil.getClientAddr(request));
                } else if (currentUser.hasRole(RoleConstants.NORMAL)) {
                    logger.info("角色为normal的用户: {} 于时间:{}  登录系统,,登录IP为:{}", user.getUserName(), LocalDateTime.now(), WebUtil.getClientAddr(request));
                } else {
                    logger.info("用户: {} 于时间: {} 该用户未分配角色,登录IP为:{}", user.getUserName(), LocalDateTime.now(), WebUtil.getClientAddr(request));
                }
                CpaUser u = userService.findByUserName(user.getUserName());
                WebUtil.setSessionUser(request, u);
                user.setRoles(userRoleService.findRoleByUser(u));
                user.setPermissions(userRoleService.findPermissionByUser(u));
                user.setId(u.getId());
                user.setPassword(null);
                user.setToken(JwtUtil.createJWT(JwtUtil.generalSubject(u), u.getUserName(), CpaConstants.JWT_ISSUSER, CpaConstants.JWT_SECRET));
                user.setImgPath(u.getImgPath());
                result.setState(CpaConstants.OPERATION_SUCCESS);
                result.setStatus(CpaConstants.USER_HAS_LOGIN);
                result.setMsg("登陆成功");
                result.setData(user);
            } catch (AuthenticationException e) {
                if (UnknownAccountException.class.getName().equals(e.getClass().getName()) || IncorrectCredentialsException.class.getName().equals(e.getClass().getName())) {
                    result.setMsg("登录失败,用户名或密码错误!");
                } else if (LockedAccountException.class.getName().equals(e.getClass().getName())) {
                    result.setMsg("登录失败,用户被锁定,请联系管理员!");
                } else {
                    result.setMsg("登陆失败");
                }
                result.setState(CpaConstants.OPERATION_ERROR);
            }
        }
        return result;
    }
}
