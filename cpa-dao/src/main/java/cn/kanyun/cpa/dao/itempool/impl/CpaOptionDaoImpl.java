package cn.kanyun.cpa.dao.itempool.impl;

import cn.kanyun.cpa.dao.CommonDaoImpl;
import cn.kanyun.cpa.dao.itempool.CpaOptionDao;
import cn.kanyun.cpa.model.entity.itempool.CpaOption;
import org.springframework.stereotype.Repository;

/**
 * Created by Administrator on 2017/6/16.
 */
@Repository(CpaOptionDao.REPOSITORY_NAME)
public class CpaOptionDaoImpl extends CommonDaoImpl<Long, CpaOption> implements CpaOptionDao {

    public CpaOptionDaoImpl() {
        super(CpaOption.class);
    }

    /**
     * 保留指定clatt值的接口【通过子类显示调用父类的构造函数来指定】
     *
     * @param clatt
     */
    public CpaOptionDaoImpl(Class<CpaOption> clatt) {
        super(clatt);
    }

}
