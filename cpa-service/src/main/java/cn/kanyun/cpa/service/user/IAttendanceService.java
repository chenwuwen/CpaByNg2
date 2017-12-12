package cn.kanyun.cpa.service.user;

import cn.kanyun.cpa.model.entity.CpaResult;
import cn.kanyun.cpa.model.entity.user.Attendance;
import cn.kanyun.cpa.model.entity.user.CpaUser;
import cn.kanyun.cpa.service.ICommonService;

public interface IAttendanceService extends ICommonService<Integer,Attendance>{
    public static final String SERVICE_NAME = "cn.kanyun.cpa.service.user.impl.AttendanceServiceImpl";

    /**
     * @describe:  签到打卡
     * @params:
     * @Author: Kanyun
     * @Date: 2017/12/12  13:32
     */
    CpaResult signIn(CpaUser user);

    /**
     * @describe:  获取最后一天打卡时间(如果最后一次打卡时间,与当前时间相差一天以上返回false,否则返回true)
     * @params:
     * @Author: Kanyun
     * @Date: 2017/12/12  13:33
     */
    Boolean getReapSigInDay(CpaUser user);
}
