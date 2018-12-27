package cn.kanyun.cpa.controller.user;


import cn.kanyun.cpa.model.constants.CpaConstants;
import cn.kanyun.cpa.model.dto.user.CpaUserDto;
import cn.kanyun.cpa.model.entity.CpaResult;
import cn.kanyun.cpa.model.entity.user.CpaUser;
import cn.kanyun.cpa.model.entity.user.CpaUserExtend;
import cn.kanyun.cpa.service.file.UploadFileService;
import cn.kanyun.cpa.service.system.UserRoleService;
import cn.kanyun.cpa.service.user.CpaUserExtendService;
import cn.kanyun.cpa.service.user.UserService;
import cn.kanyun.cpa.util.WebUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

/**
 * 通过redirect返回String类型跳转，注意这种方法不允许Spring控制器用@RestController注解，
 * 因为@RestController相当于类中的所有方法都标注了@ResponseBody，这些方法不会返回一个视图，而是返回一个json对象，
 * 这样的话只是在页面上打印出字符串，而不跳转。控制器用@Controller注解即可
 * @author Kanyun
 */
@Api(value = "/api/user",tags = "用户管理模块")
@Controller
@RequestMapping("/api/user")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Resource(name = UserService.SERVICE_NAME)
    private UserService userService;
    @Resource(name = UserRoleService.SERVICE_NAME)
    private UserRoleService userRoleService;
    @Resource(name = UploadFileService.SERVICE_NAME)
    private UploadFileService uploadFileService;
    @Resource(name = CpaUserExtendService.SERVICE_NAME)
    private CpaUserExtendService cpaUserExtendService;


    /**
     * @Author: kanyun
     * @Description:
     * 注册Ajax检查用户名是否可用
     * @Date: 2017/8/16 17:02
     * @params:
     */
    @Deprecated
    @RequestMapping(value = {"/checkname"}, method = RequestMethod.GET)
    @ResponseBody
    public String checkName_old(String username) {
        boolean b = true;
        Object[] params = {username};
        String where = "o.userName=? ";
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
     * @param
     * @return
     * @author Kanyun
     * @Description: (新方法 ， 统一返回值 ， 保留旧方法)注册Ajax检查用户名是否可用
     * @date 2017/11/13 10:21
     */
    @ApiOperation(value = "/checkName",notes = "注册Ajax检查用户名是否可用",httpMethod = "GET",response = CpaResult.class)
    @RequestMapping("/checkName")
    @ResponseBody
    public CpaResult checkName(@RequestParam("username") String username) {
        Object[] params = {username};
        String where = "o.userName=? ";
        CpaResult result = userService.getScrollData(-1, -1, where, params);
        if (result.getTotalCount() > 0) {
            result.setState(CpaConstants.OPERATION_ERROR);
            result.setMsg("用户名已存在");
            result.setData(null);
        } else {
            result.setState(CpaConstants.OPERATION_SUCCESS);
            result.setData(null);
        }
        return result;
    }

    /**
     * @Author: kanyun
     * @Description:
     * 用户注册通过分享链接 @RequestMapping，value可以匹配多个多个路径
     * @Date: 2017/8/16 17:02
     * @params:
     */
    @ApiOperation(value = "/register/{inviteUser}",notes = "注册通过分享链接",httpMethod = "POST",response = CpaResult.class)
    @RequestMapping(value = {"/register/{inviteUser}", "/register"})
    @ResponseBody
    public CpaResult saveUser(CpaUserDto userDto, HttpServletRequest request, @PathVariable(value = "inviteUser", required = false) Long inviteUser) throws NoSuchAlgorithmException {
        CpaResult result = new CpaResult();
        // 获取session中保存的验证码
        String s_code = (String) request.getSession().getAttribute("validateCode");
        // 先比较验证码(equalsIgnoreCase忽略大小写，equals不忽略)
        if (!s_code.equalsIgnoreCase(userDto.getValidateCode())) {
            result.setState(CpaConstants.OPERATION_ERROR);
            result.setMsg("验证码错误！");
        } else {
            try {
                /* 如果存在推荐人,将推荐人ID set进对象*/
                CpaUserExtend cpaUserExtend = new CpaUserExtend();
                if (!(inviteUser == null) && !(inviteUser == 0)) {
                    if (null != userService.findById(inviteUser)) {
                        cpaUserExtend.setInviteUser(inviteUser);
                    }
                }
//                去除字符串中所有空白符
                userDto.setUserName(StringUtils.deleteWhitespace(userDto.getUserName()));
                userDto.setPassword(StringUtils.deleteWhitespace(userDto.getPassword()));
//                级联保存CpaUser
                CpaUser user = userService.saveUser(userDto, cpaUserExtend);
                result.setState(CpaConstants.OPERATION_SUCCESS);
                result.setMsg("注册成功,即将跳转至登陆页！");
                WebUtil.setSessionUser(request, user);
            } catch (Exception e) {
                logger.info("ERROR：/api/user/register  用户注册异常：  " + e);
                result.setState(CpaConstants.OPERATION_ERROR);
                result.setMsg("注册失败,请重试！");
            }
        }

        return result;
    }

    /**
     * @param
     * @return
     * @author Kanyun
     * @Description:
     * 上传用户头像
     * @date 2017/11/21 20:31
     */
    @ApiOperation(value = "/upLoadUserHeaderImg",notes = "上传用户头像",httpMethod = "POST",response = CpaResult.class)
    @RequestMapping("/upLoadUserHeaderImg")
    @ResponseBody
    public CpaResult upLoadUserHeaderImg(@RequestParam("uploadFile") MultipartFile uploadFile, HttpServletRequest request) {
        CpaResult result = new CpaResult();
        try {
            MultipartFile[] files = {uploadFile};
            CpaResult imgResult = uploadFileService.upLoadImg(files);
            if (1 == imgResult.getState()) {
                result.setData(imgResult.getData());
                CpaUser user = WebUtil.getSessionUser(request);
                user.setImgPath(String.valueOf(imgResult.getData()));
                userService.update(user);
                result.setState(CpaConstants.OPERATION_SUCCESS);
            }
        } catch (Exception e) {
            logger.error("api/user/upLoadUserHeaderImg error: " + e);
            result.setState(CpaConstants.OPERATION_ERROR);
        }

        return result;
    }

    /**
     * @describe:
     * 获取用户列表
     * @params:
     * @Author: Kanyun
     * @Date: 2018/1/23 0023 10:36
     */
    @ApiOperation(value = "/getUserList",notes = "获取用户列表",httpMethod = "POST",response = CpaResult.class)
    @RequestMapping("/getUserList")
    @ResponseBody
    public CpaResult getUserList(CpaUserDto cpaUserDto) {
        CpaResult result = null;
        try {
            result = userService.findCpaUserByCondition(cpaUserDto, null);
            result.setState(CpaConstants.OPERATION_SUCCESS);
        } catch (Exception e) {
            logger.error("ERROR: api/user/getUserList {}", e);
            result.setState(CpaConstants.OPERATION_ERROR);
        }
        return result;
    }

    /**
     * @describe:
     * 获取用户详细信息
     * @params:
     * @Author: Kanyun
     * @Date: 2018/1/26 0026 16:39
     */
    @ApiOperation(value = "/getUserDetail/{userId}",notes = "获取用户详细信息",response = CpaResult.class,httpMethod = "GET")
    @RequestMapping("/getUserDetail/{userId}")
    @ResponseBody
    public CpaResult getUserDetail(@PathVariable("userId") Long userId) {
        CpaResult result = new CpaResult();
        try {
            CpaUser user = userService.findById(userId);
            CpaUserDto userDto = new CpaUserDto();
            userDto.setGender(user.getGender());
            userDto.setUserName(user.getUserName());
            userDto.setEmail(user.getEmail());
            userDto.setStatus(user.getStatus());
//            userDto.setRoles(user.getUserRoles());
            result.setData(userDto);
            result.setState(CpaConstants.OPERATION_SUCCESS);
        } catch (Exception e) {
            logger.error("ERROR: api/user/getUserDetail {}", e);
            result.setState(CpaConstants.OPERATION_ERROR);
        }
        return result;
    }

    /**
     * @describe:
     * 删除用户
     * @params:
     * @Author: Kanyun
     * @Date: 2018/1/26 0026 16:39
     */
    @ApiOperation(value = "/delUser/{userId}",notes = "删除用户",httpMethod = "GET",response = CpaResult.class,produces="application/json")
    @RequestMapping("/delUser/{userId}")
    @ResponseBody
    public CpaResult delUser(@PathVariable("userId") Long userId) {
        CpaResult result = new CpaResult();
        try {
            userService.deleteById(userId);
            result.setState(CpaConstants.OPERATION_SUCCESS);
        } catch (Exception e) {
            logger.error("ERROR: api/user/delUser {}", e);
            result.setState(CpaConstants.OPERATION_ERROR);
        }
        return result;
    }

    /**
     * @describe:
     * 修改用户
     * @params:
     * @Author: Kanyun
     * @Date: 2018/1/26 0026 16:39
     */
    @ApiOperation(value = "/updUser",notes = "修改用户",httpMethod = "POST",response = CpaResult.class,produces="application/json")
    @RequestMapping("/updUser")
    @ResponseBody
    public CpaResult updUser(CpaUserDto userDto) {
        CpaResult result = new CpaResult();
        try {
            CpaUser user = new CpaUser();
//            阿里把把规约插件提示使用Apache Beanutils拷贝属性性能较差,建议使用Spring BeanUtils
            BeanUtils.copyProperties(user, userDto);
            userService.update(user);
            result.setState(CpaConstants.OPERATION_SUCCESS);
        } catch (Exception e) {
            logger.error("ERROR: api/user/updUser {}", e);
            result.setState(CpaConstants.OPERATION_ERROR);
        }
        return result;
    }

    /**
     * @describe:
     * 跳转网页 (目前只是使用在用户扫描二维码,跳转到注册页面)
     * @params: inviteUser:推荐人ID
     * @Author: Kanyun
     * @Date: 2018/2/23 0023 17:48
     */
    @RequestMapping(value = {"/skipWebPage/{inviteUser}", "/skipWebPage/"})
    @ApiOperation(value = "跳转到注册页面",notes = "可能会携带推荐人ID,也可能不包含",httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "inviteUser",value = "推荐人ID",dataType = "Integer",paramType = "path",required = false)
    })
    public String skipWebPage(@PathVariable(value = "inviteUser", required = false) Long inviteUser) {
        return "index";
    }

    /**
     * @param
     * @return
     * @author Kanyun
     * @Description:
     * 用户注销(已被shiro接管, shiro拦截此url, 进入SysLogoutFilter过滤器)
     * @date 2017/11/25 15:57
     */
    @Deprecated
    @RequestMapping("/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        CpaUser user = WebUtil.getSessionUser(request);
        logger.info(user.getUserName() + "于时间" + new DateTime(System.currentTimeMillis()) + "退出系统");
        try {
            /*清除session*/
            WebUtil.removeSessionUser(request);
            /*重定向到默认页*/
            response.sendRedirect("/");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
