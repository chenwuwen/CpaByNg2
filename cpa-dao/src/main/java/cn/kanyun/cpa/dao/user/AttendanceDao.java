package cn.kanyun.cpa.dao.user;

import cn.kanyun.cpa.dao.CommonDao;
import cn.kanyun.cpa.model.entity.user.Attendance;

/**
 * @author KANYUN
 */
public interface AttendanceDao extends CommonDao<Long, Attendance> {
    String REPOSITORY_NAME = "cn.kanyun.cpa.dao.user.impl.AttendanceDaoImpl";


}
