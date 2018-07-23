package cn.kanyun.cpa.dao.user;

import cn.kanyun.cpa.dao.CommonDao;
import cn.kanyun.cpa.model.entity.CpaResult;
import cn.kanyun.cpa.model.entity.user.CpaUserExtend;

import java.util.LinkedHashMap;

/**
 * @author Administrator
 */
public interface CpaUserExtendDao extends CommonDao<Long, CpaUserExtend> {
    String REPOSITORY_NAME = "cn.kanyun.cpa.dao.user.impl.CpaUserExtendDaoImpl";

    /**
     * @describe: 根据条件获取用户扩展信息
     * @params:
     * @Author: Kanyun
     * @Date: 2018/2/8 0008 17:50
     */
    CpaResult findCpaUserExtendByCondition(CpaUserExtend cpaUserExtend, LinkedHashMap orderby);
}
