package cn.kanyun.cpa.service.user;

import cn.kanyun.cpa.model.entity.CpaResult;
import cn.kanyun.cpa.model.entity.user.Attendance;
import cn.kanyun.cpa.model.entity.user.CpaUser;
import cn.kanyun.cpa.service.ICommonService;

public interface IAttendanceService extends ICommonService<Integer,Attendance>{
    public static final String SERVICE_NAME = "cn.kanyun.cpa.service.user.impl.AttendanceServiceImpl";

    CpaResult signIn(CpaUser user);
}
