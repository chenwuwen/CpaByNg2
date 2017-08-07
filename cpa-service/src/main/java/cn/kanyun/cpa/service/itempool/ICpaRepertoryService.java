package cn.kanyun.cpa.service.itempool;

import cn.kanyun.cpa.model.entity.CpaResult;
import cn.kanyun.cpa.model.entity.itempool.CpaRepertory;
import cn.kanyun.cpa.service.ICommonService;

/**
 * Created by Administrator on 2017/6/16.
 */
public interface ICpaRepertoryService extends ICommonService<Integer,CpaRepertory> {
    public static final String SERVICE_NAME="cn.kanyun.cpa.Service.itempool.impl.CpaRepertoryServiceImpl";
    CpaResult getUnitExam(String where, Object[] params);
}
