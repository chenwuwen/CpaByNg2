package cn.kanyun.cpa.dao.user;


import cn.kanyun.cpa.dao.CommonDao;
import cn.kanyun.cpa.model.entity.user.UserCollect;

/**
 * @author KANYUN
 */
public interface UserCollectDao extends CommonDao<Long,UserCollect> {
	 String REPOSITORY_NAME="cn.kanyun.cpa.dao.user.impl.UserCollectDaoImpl";


}
