package cn.kanyun.cpa.controller.user;


import cn.kanyun.cpa.model.constants.CpaConstants;
import cn.kanyun.cpa.model.dto.user.CpaUserDto;
import cn.kanyun.cpa.model.entity.CpaResult;
import cn.kanyun.cpa.model.entity.user.CpaUser;
import cn.kanyun.cpa.service.file.UploadFileService;
import cn.kanyun.cpa.service.system.UserRoleService;
import cn.kanyun.cpa.service.user.UserService;
import cn.kanyun.cpa.util.WebUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

/**
 * 通过redirect返回String类型跳转，注意这种方法不允许Spring控制器用@RestController注解，
 * 因为@RestController相当于类中的所有方法都标注了@ResponseBody，这些方法不会返回一个视图，而是返回一个json对象，
 * 这样的话只是在页面上打印出字符串，而不跳转。控制器用@Controller注解即可
 */
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


    /**
     * @Author: kanyun
     * @Description: 注册Ajax检查用户名是否可用
     * @Date: 2017/8/16 17:02
     * @params:
     */
    @RequestMapping("/checkname1")
    @ResponseBody
    public String checkName1(String username) {
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
    @RequestMapping("/checkname")
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
     * @Description: 用户注册
     * @Date: 2017/8/16 17:02
     * @params:
     */
    @RequestMapping("/register")
    @ResponseBody
    public CpaResult saveUser(CpaUserDto userDto, HttpServletRequest request, HttpSession session) throws NoSuchAlgorithmException {
        CpaResult result = new CpaResult();
        // 获取session中保存的验证码
        String s_code = (String) session.getAttribute("validateCode");
        // 先比较验证码(equalsIgnoreCase忽略大小写，equals不忽略)
        if (!s_code.equalsIgnoreCase(userDto.getValidateCode())) {
            result.setState(CpaConstants.OPERATION_ERROR);
            result.setMsg("验证码错误！");
        } else {
            try {
                CpaUser user = userService.saveUser(userDto);
                result.setState(CpaConstants.OPERATION_SUCCESS);
                result.setMsg("注册成功,即将跳转至登陆页！");
                session.setAttribute(CpaConstants.USER, user);
            } catch (Exception e) {
                logger.info("/api/user/register  用户注册异常：  " + e);
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
     * @Description: 上传用户头像
     * @date 2017/11/21 20:31
     */
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
     * @describe: 获取用户列表
     * @params:
     * @Author: Kanyun
     * @Date: 2018/1/23 0023 10:36
     */
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
     * @describe: 获取用户详细信息
     * @params:
     * @Author: Kanyun
     * @Date: 2018/1/26 0026 16:39
     */
    @RequestMapping("/getUserDetail/{userId}")
    @ResponseBody
    public CpaResult getUserDetail(@PathVariable("userId") Long userId) {
        CpaResult result = new CpaResult();
        try {
            CpaUser user = userService.findById(userId);
            CpaUserDto userDto = new CpaUserDto();
            org.springframework.beans.BeanUtils.copyProperties(user, userDto);
            result.setData(userDto);
            result.setState(CpaConstants.OPERATION_SUCCESS);
        } catch (Exception e) {
            logger.error("ERROR: api/user/getUserDetail {}", e);
            result.setState(CpaConstants.OPERATION_ERROR);
        }
        return result;
    }

    /**
     * @describe: 删除用户
     * @params:
     * @Author: Kanyun
     * @Date: 2018/1/26 0026 16:39
     */
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
     * @describe: 修改用户
     * @params:
     * @Author: Kanyun
     * @Date: 2018/1/26 0026 16:39
     */
    @RequestMapping("/updUser")
    @ResponseBody
    public CpaResult updUser(CpaUserDto userDto) {
        CpaResult result = new CpaResult();
        try {
            CpaUser user = new CpaUser();
            userService.saveOrUpdate(user);
            result.setState(CpaConstants.OPERATION_SUCCESS);
        } catch (Exception e) {
            logger.error("ERROR: api/user/updUser {}", e);
            result.setState(CpaConstants.OPERATION_ERROR);
        }
        return result;
    }

    /**
     * @param
     * @return
     * @author Kanyun
     * @Description: 用户注销(已被shiro接管, shiro拦截此url, 进入SysLogoutFilter过滤器)
     * @date 2017/11/25 15:57
     */
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
