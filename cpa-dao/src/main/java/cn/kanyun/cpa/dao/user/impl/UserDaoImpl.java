package cn.kanyun.cpa.dao.user.impl;

import cn.kanyun.cpa.dao.CommonDaoImpl;
import cn.kanyun.cpa.dao.user.UserDao;
import cn.kanyun.cpa.model.dto.user.CpaUserDto;
import cn.kanyun.cpa.model.entity.CpaResult;
import cn.kanyun.cpa.model.entity.Page;
import cn.kanyun.cpa.model.entity.user.CpaUser;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Queue;

@Repository(UserDao.REPOSITORY_NAME)
public class UserDaoImpl extends CommonDaoImpl<Long, CpaUser> implements UserDao {
    //通过调用父类的构造函数指定clazz值，即实体类的类类型
    public UserDaoImpl() {
        super(CpaUser.class);
    }

    @Override
    public CpaUser findByUserName(String userName) {
        Session session = getSession();
        String hql = " from CpaUser o where o.userName = :userName";
        Query query = session.createQuery(hql);
        query.setParameter("userName", userName);
        return (CpaUser) query.uniqueResult();
    }

    @Override
    public CpaResult findCpaUserByCondition(CpaUserDto cpaUserDto, LinkedHashMap orderby) {
        Page page = new Page();
        int pageNo = cpaUserDto.getPageNo() == null || cpaUserDto.getPageNo() == 0 ? 1 : cpaUserDto.getPageNo();
        int pageSize = cpaUserDto.getPageSize() == null || cpaUserDto.getPageSize() == 0 ? 20 : cpaUserDto.getPageSize();
        page.setPageNo(pageNo);
        page.setPageSize(pageSize);
        int firstResult = page.countOffset(pageNo, pageSize);
        Object[] params;
        Queue queue = new LinkedList();
        StringBuilder where = new StringBuilder();
        if (StringUtils.isNotBlank(cpaUserDto.getUserName())) {
            where.append("o.userName like ?");
            queue.add("%" + cpaUserDto.getUserName() + "%");
        }
        if (cpaUserDto.getStartRegisterDate() != null) {
            where.append("o.regDate > ?");
            queue.add(cpaUserDto.getStartRegisterDate());
        }
        if (cpaUserDto.getEndRegisterDate() != null) {
            where.append("o.regDate < ?");
            queue.add(cpaUserDto.getEndRegisterDate());
        }
        if (cpaUserDto.getStartlastLoginDate() != null) {
            where.append("o.lastLoginDate > ?");
            queue.add(cpaUserDto.getStartlastLoginDate());
        }
        if (cpaUserDto.getEndlastLoginDate() != null) {
            where.append("o.lastLoginDate < ?");
            queue.add(cpaUserDto.getEndlastLoginDate());
        }
        params = queue.toArray();
        CpaResult result = getScrollData(firstResult, pageSize, where.toString(), params, orderby);
        page.setTotalRecords(result.getTotalCount().intValue());
        result.setTotalPage(page.getTotalPages());
        return result;
    }
}

