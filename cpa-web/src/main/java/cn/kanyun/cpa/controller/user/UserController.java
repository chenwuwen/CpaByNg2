package cn.kanyun.cpa.controller.user;


import cn.kanyun.cpa.model.dto.user.CpaUserDto;
import cn.kanyun.cpa.model.entity.system.UserRole;
import cn.kanyun.cpa.model.entity.user.CpaUser;
import cn.kanyun.cpa.model.entity.CpaResult;
import cn.kanyun.cpa.service.system.IUserRoleService;
import cn.kanyun.cpa.service.user.IUserService;
import cn.kanyun.cpa.model.entity.CpaConstants;
import cn.kanyun.cpa.util.EndecryptUtils;
import cn.kanyun.cpa.util.WebUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;


@Controller
@RequestMapping("/api/user")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Resource(name = IUserService.SERVICE_NAME)
    private IUserService userService;
    @Resource
    private IUserRoleService userRoleService;


    /**
     *@Author: kanyun
     *@Description: 注册Ajax检查用户名是否可用
     *@Date: 2017/8/16 17:02
     *@params:
     */
    @RequestMapping("/checkname")
    @ResponseBody
    public String checkName(String username) {
        boolean b = true;
        Object[] params = {username};
        String where = "o.username=? ";
        CpaResult result = userService.getScrollData(-1, -1, where, params);
        if (result.getTotalCount() > 0) {
           b = false;
        }
        Map<String, Boolean> map = new HashMap<>();
        map.put("valid", b);
        ObjectMapper mapper = new ObjectMapper();
        String resultString = "";
        try {
            resultString = mapper.writeValueAsString(map); //使用jackson的writeValueAsString把java对象输出成字符串实例,此处Map集合即为对象;转换为字符串后在通过@ResponseBody转换为json
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return resultString;
    }

    /**
     *@Author: kanyun
     *@Description: 用户注册
     *@Date: 2017/8/16 17:02
     *@params:
     */
    @RequestMapping("/register")
    @ResponseBody
    public CpaResult saveUser(CpaUserDto userDto, HttpSession session) throws NoSuchAlgorithmException {
        CpaResult result = new CpaResult();
        // 获取session中保存的验证码
        String s_code = (String) session.getAttribute("validateCode");
        // 先比较验证码(equalsIgnoreCase忽略大小写，equals不忽略)
        if (!s_code.equalsIgnoreCase(userDto.getValidateCode())) {
            result.setState(2);
            result.setMsg("验证码错误！");
        } else {
            try {
                CpaUser user = new CpaUser();
                user.setEmail(userDto.getEmail());
                user.setUserName(userDto.getUserName());
                userDto = EndecryptUtils.md5Password(userDto.getUserName(), userDto.getPassword());
                user.setRegDate(new Timestamp(System.currentTimeMillis()));
                user.setSalt(userDto.getSalt());
                user.setPassword(userDto.getPassword());
                user.setStatus(1);
                Integer k = userService.save(user);
                if (k != null) {
                    UserRole userRole = new UserRole();
                    userRole.setUserId(k);
                    userRole.setRoleId(3);
                    Integer r = userRoleService.save(userRole);
                    if(r!=null) {
                        result.setState(CpaConstants.OPERATION_SUCCESS);
                        result.setMsg("注册成功,即将跳转至登陆页！");
                    }else{
                        result.setState(CpaConstants.OPERATION_ERROR);
                        result.setMsg("注册失败,请重试！");
                    }
                } else {
                    result.setState(2);
                    result.setMsg("注册失败,请重试！");
                }
            }catch (Exception e){
                logger.info("/api/user/register  用户注册异常：  "+e);
                result.setState(2);
                result.setMsg("注册失败,请重试！");
            }
        }
        return result;
    }
}
