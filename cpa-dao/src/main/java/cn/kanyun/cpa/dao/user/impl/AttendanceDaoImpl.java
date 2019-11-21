package cn.kanyun.cpa.dao.user.impl;

import cn.kanyun.cpa.dao.CommonDaoImpl;
import cn.kanyun.cpa.dao.user.AttendanceDao;
import cn.kanyun.cpa.model.entity.user.Attendance;
import org.springframework.stereotype.Repository;

/**
 * @author Kanyun
 */
@Repository(AttendanceDao.REPOSITORY_NAME)
public class AttendanceDaoImpl extends CommonDaoImpl<Long,Attendance> implements AttendanceDao {
    /**
     * 保留指定clatt值的接口【通过子类显示调用父类的构造函数来指定】
     *
     */
    public AttendanceDaoImpl() {
        super(Attendance.class);
    }


}
