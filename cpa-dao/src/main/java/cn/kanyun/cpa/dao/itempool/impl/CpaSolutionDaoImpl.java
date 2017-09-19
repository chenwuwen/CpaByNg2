package cn.kanyun.cpa.dao.itempool.impl;

import cn.kanyun.cpa.dao.CommonDaoImpl;
import cn.kanyun.cpa.dao.itempool.ICpaSolutionDao;
import cn.kanyun.cpa.model.entity.itempool.CpaSolution;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Override
    public Map<Integer, CpaSolution> getAnswer(List<Integer> respertoryIds) {
        Session session = getSession();
        String hql = "from CpaSolution o where o.id in (:respertoryIds)";
        Query query = session.createQuery(hql);
        query.setParameterList("respertoryIds", respertoryIds);
        Map<Integer, CpaSolution> mapSolution = new HashMap();
        List<CpaSolution> solutionList = query.list();
        for (int i = 0; i < solutionList.size(); i++) {
            mapSolution.put(solutionList.get(i).getId(), solutionList.get(i));
        }
        return mapSolution;
    }

    @Override
    public Integer saveSolution(CpaSolution cpaSolution) {
        Integer k = save(cpaSolution);
        return k;
    }
}
