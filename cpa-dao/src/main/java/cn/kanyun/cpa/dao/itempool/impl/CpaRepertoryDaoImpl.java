package cn.kanyun.cpa.dao.itempool.impl;

import cn.kanyun.cpa.dao.CommonDaoImpl;
import cn.kanyun.cpa.dao.itempool.CpaRepertoryDao;
import cn.kanyun.cpa.model.entity.itempool.CpaRepertory;
import org.springframework.stereotype.Repository;

/**
 * Created by Administrator on 2017/6/16.
 */
@Repository(CpaRepertoryDao.REPOSITORY_NAME)
public class CpaRepertoryDaoImpl extends CommonDaoImpl<Integer, CpaRepertory> implements CpaRepertoryDao {

    public CpaRepertoryDaoImpl() {
        super(CpaRepertory.class);
    }

    /**
     * 保留指定clatt值的接口【通过子类显示调用父类的构造函数来指定】
     * 通过调用父类的构造函数指定clazz值，即实体类的类类型
     *
     * @param clatt
     */
    public CpaRepertoryDaoImpl(Class<CpaRepertory> clatt) {
        super(clatt);
    }


}
