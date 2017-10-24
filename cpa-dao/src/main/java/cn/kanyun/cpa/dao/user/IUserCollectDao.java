package cn.kanyun.cpa.dao.user;


import cn.kanyun.cpa.dao.ICommonDao;
import cn.kanyun.cpa.model.entity.user.AnswerRecord;
import cn.kanyun.cpa.model.entity.user.UserCollect;

public interface IUserCollectDao extends ICommonDao<Integer,UserCollect> {
	public static final String REPOSITORY_NAME="cn.kanyun.cpa.dao.user.impl.UserCollectDaoImpl";


}
