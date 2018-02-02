package cn.kanyun.cpa.dao.user.impl;

import cn.kanyun.cpa.dao.CommonDaoImpl;
import cn.kanyun.cpa.dao.user.CpaUserExtendDao;
import cn.kanyun.cpa.model.entity.user.CpaUserExtend;
import org.springframework.stereotype.Repository;

@Repository(CpaUserExtendDao.REPOSITORY_NAME)
public class CpaUserExtendDaoImpl extends CommonDaoImpl<Long, CpaUserExtend> implements CpaUserExtendDao {
    /**
     * 保留指定clatt值的接口【通过子类显示调用父类的构造函数来指定】
     *
     * @param clatt
     */
    //通过调用父类的构造函数指定clazz值，即实体类的类类型
    public CpaUserExtendDaoImpl() {
        super(CpaUserExtend.class);
    }
}
