package cn.kanyun.cpa.dao.user;


import cn.kanyun.cpa.dao.CommonDao;
import cn.kanyun.cpa.model.entity.user.AnswerRecord;

public interface AnswerRecordDao extends CommonDao<Long,AnswerRecord> {
	public static final String REPOSITORY_NAME="cn.kanyun.cpa.dao.user.impl.AnswerRecordDaoImpl";


}
