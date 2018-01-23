package cn.kanyun.cpa.dao.itempool;

import cn.kanyun.cpa.dao.CommonDao;
import cn.kanyun.cpa.model.dto.itempool.CpaRepertoryDto;
import cn.kanyun.cpa.model.entity.CpaResult;
import cn.kanyun.cpa.model.entity.itempool.CpaRepertory;

import java.util.LinkedHashMap;

/**
 * Created by Administrator on 2017/6/16.
 */
public interface CpaRepertoryDao extends CommonDao<Long, CpaRepertory> {
    public static final String REPOSITORY_NAME = "cn.kanyun.cpa.dao.itempool.impl.CpaRepertoryDaoImpl";

    /**
     * @describe: 通过条件获取试题列表
     * @params: cpaRepertoryDto 查询参数  orderby可选,默认按照id倒叙排列
     * @Author: Kanyun
     * @Date: 2018/1/23 0023 13:21
     */
    CpaResult findCpaRepertoryByCondition(CpaRepertoryDto cpaRepertoryDto, LinkedHashMap orderby);
}
