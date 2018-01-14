package cn.kanyun.cpa.dao.user;


import cn.kanyun.cpa.dao.CommonDao;
import cn.kanyun.cpa.model.entity.user.UserCollect;

public interface UserCollectDao extends CommonDao<Long,UserCollect> {
	public static final String REPOSITORY_NAME="cn.kanyun.cpa.dao.user.impl.UserCollectDaoImpl";


}
