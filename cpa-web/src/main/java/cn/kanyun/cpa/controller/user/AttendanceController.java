package cn.kanyun.cpa.controller.user;

import cn.kanyun.cpa.model.dto.user.AttendanceDto;
import cn.kanyun.cpa.model.constants.CpaConstants;
import cn.kanyun.cpa.model.entity.CpaResult;
import cn.kanyun.cpa.model.entity.user.CpaUser;
import cn.kanyun.cpa.service.user.AttendanceService;
import cn.kanyun.cpa.util.WebUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/api/user")
public class AttendanceController {
    private static final Logger logger = LoggerFactory.getLogger(AttendanceController.class);

    @Resource(name = AttendanceService.SERVICE_NAME)
    private AttendanceService attendanceService;


    /**
     * @describe:
     * @params:[request]
     * @Author: Kanyun
     * @Date: 2017/12/12 0012 13:33
     */
    @RequestMapping("/attendance")
    @ResponseBody
    public CpaResult signIn(HttpServletRequest request) {
        CpaResult result = new CpaResult();
        try {
            CpaUser user = WebUtil.getSessionUser(request);
            result = attendanceService.signIn(user);
        } catch (Exception e) {
            logger.error("用户签到异常：" + e);
            result.setState(CpaConstants.OPERATION_ERROR);
        }
        return result;
    }

    @RequestMapping("/getSignIn")
    @ResponseBody
    public CpaResult getReapSigInDay(HttpServletRequest request){
        CpaResult result = new CpaResult();
        CpaUser user = WebUtil.getSessionUser(request);
        AttendanceDto attendanceDto = new AttendanceDto();
        if (attendanceService.getReapSigInDay(user)){
                attendanceDto.setReapSigInDay(user.getReapSigInDay());
        }else {
            attendanceDto.setReapSigInDay(0);
        }
        result.setData(attendanceDto);
        return result;
    }
}
