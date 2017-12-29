package cn.kanyun.cpa.dao.system;

import cn.kanyun.cpa.dao.CommonDao;
import cn.kanyun.cpa.model.entity.system.CpaRole;

/**
 * Created by Administrator on 2017/6/16.
 */
public interface CpaRoleDao extends CommonDao<Integer,CpaRole> {
    public static final String REPOSITORY_NAME="cn.kanyun.cpa.dao.system.impl.CpaRoleDaoImpl";
}
