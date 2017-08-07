package cn.kanyun.cpa.service.itempool;

import cn.kanyun.cpa.model.entity.itempool.CpaSolution;
import cn.kanyun.cpa.service.ICommonService;

/**
 * Created by Administrator on 2017/6/16.
 */
public interface ICpaSolutionService extends ICommonService<Integer,CpaSolution> {
    public static final String SERVICE_NAME="cn.kanyun.cpa.Service.itempool.impl.CpaSolutionServiceImpl";
}
