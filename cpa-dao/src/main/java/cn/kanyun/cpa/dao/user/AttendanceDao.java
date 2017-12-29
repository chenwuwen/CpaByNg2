package cn.kanyun.cpa.dao.user;

import cn.kanyun.cpa.dao.CommonDao;
import cn.kanyun.cpa.model.entity.user.Attendance;

public interface AttendanceDao extends CommonDao<Integer,Attendance> {
    public static final String REPOSITORY_NAME="cn.kanyun.cpa.dao.user.impl.AttendanceDaoImpl";


}
