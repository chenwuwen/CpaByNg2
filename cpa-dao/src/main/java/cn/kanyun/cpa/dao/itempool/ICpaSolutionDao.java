package cn.kanyun.cpa.dao.itempool;

import cn.kanyun.cpa.dao.ICommonDao;
import cn.kanyun.cpa.model.entity.itempool.CpaSolution;

/**
 * Created by Administrator on 2017/6/16.
 */
public interface ICpaSolutionDao extends ICommonDao<Integer,CpaSolution> {
    public static final String REPOSITORY_NAME="cn.kanyun.cpa.dao.itempool.impl.CpaSolutionDaoImpl";
}
