package cn.kanyun.cpa.dao.system.impl;

import cn.kanyun.cpa.dao.CommonDaoImpl;
import cn.kanyun.cpa.dao.system.ICpaPermissionDao;
import cn.kanyun.cpa.model.entity.system.CpaPermission;
import org.springframework.stereotype.Repository;

/**
 * Created by Administrator on 2017/6/16.
 */
@Repository(ICpaPermissionDao.REPOSITORY_NAME)
public class CpaPermissionDaoImpl extends CommonDaoImpl<Integer,CpaPermission> implements ICpaPermissionDao {

    public CpaPermissionDaoImpl() {
        super(CpaPermission.class);
    }

    /**
     * 保留指定clatt值的接口【通过子类显示调用父类的构造函数来指定】
     *
     * @param clatt
     */
    public CpaPermissionDaoImpl(Class<CpaPermission> clatt) {
        super(clatt);
    }


}
