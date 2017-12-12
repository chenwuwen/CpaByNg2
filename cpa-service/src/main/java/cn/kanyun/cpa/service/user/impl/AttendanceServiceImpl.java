package cn.kanyun.cpa.service.user.impl;

import cn.kanyun.cpa.dao.user.IAttendanceDao;
import cn.kanyun.cpa.dao.user.IUserDao;
import cn.kanyun.cpa.model.entity.CpaConstants;
import cn.kanyun.cpa.model.entity.CpaResult;
import cn.kanyun.cpa.model.entity.user.Attendance;
import cn.kanyun.cpa.model.entity.user.CpaUser;
import cn.kanyun.cpa.service.CommonServiceImpl;
import cn.kanyun.cpa.service.user.IAttendanceService;
import org.joda.time.DateTimeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.annotation.Transient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.LinkedHashMap;

@Service(IAttendanceService.SERVICE_NAME)
@Transactional
public class AttendanceServiceImpl extends CommonServiceImpl<Integer, Attendance> implements IAttendanceService {
    private static final Logger logger = LoggerFactory.getLogger(AttendanceServiceImpl.class);

    @Resource(name = IAttendanceDao.REPOSITORY_NAME)
    private IAttendanceDao attendanceDao;
    @Resource(name = IUserDao.REPOSITORY_NAME)
    private IUserDao userDao;

    @Override
    @Transactional(rollbackFor = {RuntimeException.class, Exception.class})
    public CpaResult signIn(CpaUser user) {
        CpaResult result = new CpaResult();
        Attendance attendance = new Attendance();
        attendance.setAttendanceDate(new Timestamp(System.currentTimeMillis()));
        attendance.setUserId(user.getId());
        attendance.setUserName(user.getUserName());
        //判断当前未打卡前上一次打卡,与当前时间差值
        if (this.getReapSigInDay(user)) {
            //设置连续打卡时间,为之前连续打卡时间加1
            user.setReapSigInDay(user.getReapSigInDay() + 1);
        } else {
            //设置连续打卡时间为1
            user.setReapSigInDay(1);
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
        if (System.currentTimeMillis() - attendance.getAttendanceDate().getTime() > 24 * 60 * 60 * 1000) {
            //上次打卡时间与当前相差一天
            return false;
        }
        return true;
    }
}
