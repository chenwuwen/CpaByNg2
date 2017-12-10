package cn.kanyun.cpa.controller.user;

import cn.kanyun.cpa.model.entity.CpaConstants;
import cn.kanyun.cpa.model.entity.CpaResult;
import cn.kanyun.cpa.model.entity.user.CpaUser;
import cn.kanyun.cpa.service.user.IAttendanceService;
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

    @Resource(name = IAttendanceService.SERVICE_NAME)
    private IAttendanceService attendanceService;

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
}
