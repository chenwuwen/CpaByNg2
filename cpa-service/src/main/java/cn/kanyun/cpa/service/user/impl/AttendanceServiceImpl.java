package cn.kanyun.cpa.service.user.impl;

import cn.kanyun.cpa.model.entity.CpaConstants;
import cn.kanyun.cpa.model.entity.CpaResult;
import cn.kanyun.cpa.model.entity.user.Attendance;
import cn.kanyun.cpa.model.entity.user.CpaUser;
import cn.kanyun.cpa.service.CommonServiceImpl;
import cn.kanyun.cpa.service.user.IAttendanceService;
import org.joda.time.DateTimeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service(IAttendanceService.SERVICE_NAME)
public class AttendanceServiceImpl extends CommonServiceImpl<Integer, Attendance> implements IAttendanceService {
    private static final Logger logger = LoggerFactory.getLogger(AttendanceServiceImpl.class);

    @Override
    public CpaResult signIn(CpaUser user) {
        CpaResult result = new CpaResult();
        Attendance attendance = new Attendance();
        attendance.setAttendanceDate(new Timestamp(System.currentTimeMillis()));
        attendance.setUserId(user.getId());
        attendance.setUserName(user.getUserName());
        save(attendance);
        result.setState(CpaConstants.OPERATION_SUCCESS);
        return result;
    }
}
