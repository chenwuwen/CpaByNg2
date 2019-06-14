package cn.kanyun.cpa.controller.user;

import cn.kanyun.cpa.exception.ProgramsInternalException;
import cn.kanyun.cpa.model.dto.user.AttendanceDto;
import cn.kanyun.cpa.model.constants.CpaConstants;
import cn.kanyun.cpa.model.entity.CpaResult;
import cn.kanyun.cpa.model.entity.user.CpaUser;
import cn.kanyun.cpa.model.entity.user.CpaUserExtend;
import cn.kanyun.cpa.service.user.AttendanceService;
import cn.kanyun.cpa.util.WebUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 用户签到
 * @author Kanyun
 */
@Api(value = "/api/user",tags = "签到模块")
@Controller
@RequestMapping("/api/user")
public class AttendanceController {
    private static final Logger logger = LoggerFactory.getLogger(AttendanceController.class);

    @Resource(name = AttendanceService.SERVICE_NAME)
    private AttendanceService attendanceService;


    /**
     * @describe: 签到
     * @params:[request]
     * @Author: Kanyun
     * @Date: 2017/12/12 0012 13:33
     */
    @ApiOperation(value = "/attendance",notes = "用户签到",response = CpaResult.class,produces="application/json")
    @RequestMapping("/attendance")
    @ResponseBody
    public CpaResult signIn(HttpServletRequest request) {
        CpaResult result ;
        try {
            CpaUser user = WebUtil.getSessionUser(request);
            CpaUserExtend userExtend = user.getCpaUserExtend();
            result = attendanceService.signIn(user, userExtend);
        } catch (Exception e) {
            logger.error("用户签到异常：" + e);
            throw new ProgramsInternalException("用户签到异常");
        }
        return result;
    }


}
