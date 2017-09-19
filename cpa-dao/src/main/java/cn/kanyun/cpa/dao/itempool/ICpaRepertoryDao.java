package cn.kanyun.cpa.dao.itempool;

import cn.kanyun.cpa.dao.ICommonDao;
import cn.kanyun.cpa.model.entity.itempool.CpaOption;
import cn.kanyun.cpa.model.entity.itempool.CpaRepertory;
import cn.kanyun.cpa.model.entity.itempool.CpaSolution;

/**
 * Created by Administrator on 2017/6/16.
 */
public interface ICpaRepertoryDao extends ICommonDao<Integer, CpaRepertory> {
    public static final String REPOSITORY_NAME = "cn.kanyun.cpa.dao.itempool.impl.CpaRepertoryDaoImpl";

    /**
     * @Author: kanyun
     * @Description: 新增单元测试 （新增试题题干）
     * @Date: 2017/9/18 16:58
     * @params:
     */
    public Integer saveRepertory(CpaRepertory cpaRepertory);
}
