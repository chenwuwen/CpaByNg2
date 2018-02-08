package cn.kanyun.cpa.dao.user.impl;

import cn.kanyun.cpa.dao.CommonDaoImpl;
import cn.kanyun.cpa.dao.user.CpaUserExtendDao;
import cn.kanyun.cpa.model.entity.CpaResult;
import cn.kanyun.cpa.model.entity.Page;
import cn.kanyun.cpa.model.entity.user.CpaUserExtend;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Queue;

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

    @Override
    public CpaResult findCpaUserExtendByCondition(CpaUserExtend cpaUserExtend, LinkedHashMap orderby) {
        CpaResult result;
        Page page = new Page();
        int pageNo = cpaUserExtend.getPageNo() == null || cpaUserExtend.getPageNo() == 0 ? 1 : cpaUserExtend.getPageNo();
        int pageSize = cpaUserExtend.getPageSize() == null || cpaUserExtend.getPageSize() == 0 ? 20 : cpaUserExtend.getPageSize();
        page.setPageNo(pageNo);
        page.setPageSize(pageSize);
        int firstResult = page.countOffset(pageNo, pageSize);
        Object[] params;
        Queue queue = new LinkedList();
        StringBuilder where = new StringBuilder();
        if (null != cpaUserExtend.getInviteUser()) {
            where.append(" and o.inviteUser = ？ ");
            queue.add(cpaUserExtend.getInviteUser());
        }
        if (null != cpaUserExtend.getCpaUser()) {
            where.append(" and o.cpaUser = ？ ");
            queue.add(cpaUserExtend.getCpaUser());
        }
        if (queue.size() > 0) {
            params = queue.toArray();
            result = getScrollData(firstResult, pageSize, where.toString(), params, orderby);
        } else {
            result = getScrollData(firstResult, pageSize, null, null, orderby);
        }
        page.setTotalRecords(result.getTotalCount().intValue());
        result.setTotalPage(page.getTotalPages());
        return result;
    }
}
