package cn.kanyun.cpa.service.user.impl;

import cn.kanyun.cpa.dao.user.AttendanceDao;
import cn.kanyun.cpa.dao.user.UserDao;
import cn.kanyun.cpa.model.constants.CpaConstants;
import cn.kanyun.cpa.model.entity.CpaResult;
import cn.kanyun.cpa.model.entity.user.Attendance;
import cn.kanyun.cpa.model.entity.user.CpaUser;
import cn.kanyun.cpa.model.entity.user.CpaUserExtend;
import cn.kanyun.cpa.service.CommonServiceImpl;
import cn.kanyun.cpa.service.user.AttendanceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;

/**
 * @author Administrator
 */
@Service(AttendanceService.SERVICE_NAME)
@Transactional
public class AttendanceServiceImpl extends CommonServiceImpl<Long, Attendance> implements AttendanceService {
    private static final Logger logger = LoggerFactory.getLogger(AttendanceServiceImpl.class);

    @Resource(name = AttendanceDao.REPOSITORY_NAME)
    private AttendanceDao attendanceDao;
    @Resource(name = UserDao.REPOSITORY_NAME)
    private UserDao userDao;

    @Override
    @Transactional(rollbackFor = {RuntimeException.class, Exception.class})
    public CpaResult signIn(CpaUser user,CpaUserExtend userExtend) {
        CpaResult result = new CpaResult();
        Attendance attendance = new Attendance();
        attendance.setAttendanceDate(LocalDateTime.now());
        attendance.setUserId(user.getId());
        attendance.setUserName(user.getUserName());
        //判断当前未打卡前上一次打卡,与当前时间差值
        if (this.getReapSigInDay(user)) {
            //设置连续打卡时间,为之前连续打卡时间加1
            userExtend.setReapSignInDay(userExtend.getReapSignInDay() + 1);
        } else {
            //设置连续打卡时间为1
            userExtend.setReapSignInDay(1);
        }
        save(attendance);
        userDao.update(user);
        result.setState(CpaConstants.OPERATION_SUCCESS);
        return result;
    }

    @Override
    public Boolean getReapSigInDay(CpaUser user) {
        String where = "o.userId=?";
        Object[] params = {user.getId()};
        LinkedHashMap orderBy = new LinkedHashMap() {{
            put("attendanceDate", "desc");
        }};
        CpaResult result = getScrollData(1, 1, where, params, orderBy);
        Attendance attendance = (Attendance) result.getData();
        //获取上次签到时间
        Timestamp lastSiginTime = Timestamp.valueOf(attendance.getAttendanceDate());
        if (System.currentTimeMillis() - lastSiginTime.getTime() > 24 * 60 * 60 * 1000) {
            //上次签到时间与当前相差一天
            return false;
        }
        return true;
    }
}
