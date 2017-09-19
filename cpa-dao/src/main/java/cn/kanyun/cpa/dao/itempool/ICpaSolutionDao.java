package cn.kanyun.cpa.dao.itempool;

import cn.kanyun.cpa.dao.ICommonDao;
import cn.kanyun.cpa.model.entity.itempool.CpaOption;
import cn.kanyun.cpa.model.entity.itempool.CpaRepertory;
import cn.kanyun.cpa.model.entity.itempool.CpaSolution;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/6/16.
 */
public interface ICpaSolutionDao extends ICommonDao<Integer, CpaSolution> {
    public static final String REPOSITORY_NAME = "cn.kanyun.cpa.dao.itempool.impl.CpaSolutionDaoImpl";

    /**
     * @Author: kanyun
     * @Description: 获取答案
     * @Date: 2017/8/16 15:41
     * @params:
     */
    public Map<Integer, CpaSolution> getAnswer(List<Integer> respertoryIds);

    /**
     * @Author: kanyun
     * @Description: 新增单元测试 （新增试题答案）
     * @Date: 2017/9/18 17:05
     * @params:
     */
    public Integer saveSolution(CpaSolution cpaSolution);

}
