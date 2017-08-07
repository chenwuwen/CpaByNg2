package cn.kanyun.cpa.dao.itempool.impl;

import cn.kanyun.cpa.dao.CommonDaoImpl;
import cn.kanyun.cpa.dao.itempool.ICpaSolutionDao;
import cn.kanyun.cpa.model.entity.itempool.CpaSolution;
import org.springframework.stereotype.Repository;

/**
 * Created by Administrator on 2017/6/16.
 */
@Repository(ICpaSolutionDao.REPOSITORY_NAME)
public class CpaSolutionDaoImpl extends CommonDaoImpl<Integer, CpaSolution> implements ICpaSolutionDao {

    public CpaSolutionDaoImpl() {
        super(CpaSolution.class);
    }

    /**
     * 保留指定clatt值的接口【通过子类显示调用父类的构造函数来指定】
     *
     * @param clatt
     */
    public CpaSolutionDaoImpl(Class<CpaSolution> clatt) {
        super(clatt);
    }
}
