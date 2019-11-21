package cn.kanyun.cpa.dao.user.impl;

import cn.kanyun.cpa.dao.CommonDaoImpl;
import cn.kanyun.cpa.dao.user.AnswerRecordDao;
import cn.kanyun.cpa.model.entity.user.AnswerRecord;
import org.springframework.stereotype.Repository;

/**
 * @author Kanyun
 */
@Repository(AnswerRecordDao.REPOSITORY_NAME)
public class AnswerRecordDaoImpl extends CommonDaoImpl<Long, AnswerRecord> implements AnswerRecordDao {
    /**
     * 通过调用父类的构造函数指定clazz值，即实体类的类类型
     */
    public AnswerRecordDaoImpl() {
        super(AnswerRecord.class);
    }


}

