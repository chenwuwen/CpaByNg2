package cn.kanyun.cpa.dao.itempool;

import cn.kanyun.cpa.dao.ICommonDao;
import cn.kanyun.cpa.model.entity.itempool.CpaOption;
import cn.kanyun.cpa.model.entity.itempool.CpaRepertory;
import cn.kanyun.cpa.model.entity.itempool.CpaSolution;

import java.util.List;

/**
 * Created by Administrator on 2017/6/16.
 */
public interface ICpaOptionDao extends ICommonDao<Integer, CpaOption> {
    public static final String REPOSITORY_NAME = "cn.kanyun.cpa.dao.itempool.impl.CpaOptionDaoImpl";

    /**
     * @Author: zhaoyingxu
     * @Description: 新增单元测试 （新增试题选项）
     * @Date: 2017/9/18 17:07
     * @params:
     */
    public void saveOption(List<CpaOption> cpaOptions);
}
