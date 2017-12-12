package cn.kanyun.cpa.dao.user.impl;

import cn.kanyun.cpa.dao.CommonDaoImpl;
import cn.kanyun.cpa.dao.user.IAttendanceDao;
import cn.kanyun.cpa.dao.user.IUserCollectDao;
import cn.kanyun.cpa.model.entity.user.Attendance;
import org.springframework.stereotype.Repository;

@Repository(IAttendanceDao.REPOSITORY_NAME)
public class AttendanceDaoImpl extends CommonDaoImpl<Integer,Attendance> implements IAttendanceDao {
    /**
     * 保留指定clatt值的接口【通过子类显示调用父类的构造函数来指定】
     *
     * @param clatt
     */
    public AttendanceDaoImpl(Class clatt) {
        super(clatt);
    }


}
