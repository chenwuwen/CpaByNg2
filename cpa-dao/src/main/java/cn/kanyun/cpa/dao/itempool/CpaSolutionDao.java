package cn.kanyun.cpa.dao.itempool;

import cn.kanyun.cpa.dao.CommonDao;
import cn.kanyun.cpa.model.entity.itempool.CpaSolution;

import java.util.List;
import java.util.Map;

/**
 * @author Administrator
 * @date 2017/6/16
 */
public interface CpaSolutionDao extends CommonDao<Long, CpaSolution> {
    String REPOSITORY_NAME = "cn.kanyun.cpa.dao.itempool.impl.CpaSolutionDaoImpl";

    /**
     * @Author: kanyun
     * @Description: 获取答案
     * @Date: 2017/8/16 15:41
     * @params:
     */
    Map<Integer, CpaSolution> getAnswer(List<Long> questionIds);


}
