package cn.kanyun.cpa.dao.system.impl;

import cn.kanyun.cpa.dao.CommonDaoImpl;
import cn.kanyun.cpa.dao.system.CpaRoleDao;
import cn.kanyun.cpa.model.entity.system.CpaRole;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Administrator
 * @date 2017/6/16
 */
@Repository(CpaRoleDao.REPOSITORY_NAME)
public class CpaRoleDaoImpl extends CommonDaoImpl<Integer,CpaRole> implements CpaRoleDao {

    public CpaRoleDaoImpl() {
        super(CpaRole.class);
    }

    /**
     * 保留指定clatt值的接口【通过子类显示调用父类的构造函数来指定】
     *
     * @param clatt
     */
    public CpaRoleDaoImpl(Class<CpaRole> clatt) {
        super(clatt);
    }


}
