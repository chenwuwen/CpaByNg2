package cn.kanyun.cpa.dao.user;

import cn.kanyun.cpa.dao.CommonDao;
import cn.kanyun.cpa.model.entity.user.CpaUserExtend;

public interface CpaUserExtendDao extends CommonDao<Long,CpaUserExtend> {
    public static final String REPOSITORY_NAME="cn.kanyun.cpa.dao.user.impl.CpaUserExtendDaoImpl";

}
