package cn.kanyun.cpa.dao.itempool;

import cn.kanyun.cpa.dao.CommonDao;
import cn.kanyun.cpa.model.entity.itempool.CpaOption;

/**
 * Created by Administrator on 2017/6/16.
 */
public interface CpaOptionDao extends CommonDao<Long, CpaOption> {
    public static final String REPOSITORY_NAME = "cn.kanyun.cpa.dao.itempool.impl.CpaOptionDaoImpl";

}
