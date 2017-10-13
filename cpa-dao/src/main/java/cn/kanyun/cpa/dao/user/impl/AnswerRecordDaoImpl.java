package cn.kanyun.cpa.dao.user.impl;

import cn.kanyun.cpa.dao.CommonDaoImpl;
import cn.kanyun.cpa.dao.user.IAnswerRecordDao;
import cn.kanyun.cpa.dao.user.IUserDao;
import cn.kanyun.cpa.model.entity.user.CpaUser;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

@Repository(IAnswerRecordDao.REPOSITORY_NAME)
public class AnswerRecordDaoImpl extends CommonDaoImpl<Integer, CpaUser> implements IAnswerRecordDao {
    //通过调用父类的构造函数指定clazz值，即实体类的类类型
    public AnswerRecordDaoImpl() {
        super(CpaUser.class);
    }


}

