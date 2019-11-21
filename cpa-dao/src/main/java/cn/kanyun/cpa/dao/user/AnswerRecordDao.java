package cn.kanyun.cpa.dao.user;


import cn.kanyun.cpa.dao.CommonDao;
import cn.kanyun.cpa.model.entity.user.AnswerRecord;

/**
 * @author KANYUN
 */
public interface AnswerRecordDao extends CommonDao<Long, AnswerRecord> {
    String REPOSITORY_NAME = "cn.kanyun.cpa.dao.user.impl.AnswerRecordDaoImpl";


}
