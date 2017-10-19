package cn.kanyun.cpa.dao.user;


import cn.kanyun.cpa.dao.ICommonDao;
import cn.kanyun.cpa.model.entity.user.AnswerRecord;
import cn.kanyun.cpa.model.entity.user.CpaUser;

public interface IAnswerRecordDao extends ICommonDao<Integer,AnswerRecord> {
	public static final String REPOSITORY_NAME="cn.kanyun.cpa.dao.user.impl.AnswerRecordDaoImpl";


}
