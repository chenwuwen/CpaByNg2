package cn.kanyun.cpa.dao.itempool.impl;

import cn.kanyun.cpa.dao.CommonDaoImpl;
import cn.kanyun.cpa.dao.itempool.CpaSolutionDao;
import cn.kanyun.cpa.model.entity.itempool.CpaSolution;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Kanyun
 * @date 2017/6/16
 */
@Repository(CpaSolutionDao.REPOSITORY_NAME)
public class CpaSolutionDaoImpl extends CommonDaoImpl<Long, CpaSolution> implements CpaSolutionDao {

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

    @Override
    public Map<Integer, CpaSolution> getAnswer(List<Long> questionIds) {
        Session session = getSession();
        String hql = "from CpaSolution o where o.id in (:questionIds)";
        Query query = session.createQuery(hql);
        query.setParameterList("questionIds", questionIds);
        Map<Integer, CpaSolution> mapSolution = new HashMap();
        List<CpaSolution> solutionList = query.list();
        for (CpaSolution cpaSolution : solutionList) {
            mapSolution.put(cpaSolution.getId().intValue(), cpaSolution);
        }
        return mapSolution;
    }

}
