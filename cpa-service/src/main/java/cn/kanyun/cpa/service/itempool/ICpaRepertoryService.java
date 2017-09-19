package cn.kanyun.cpa.service.itempool;

import cn.kanyun.cpa.model.dto.itempool.CpaRepertoryDto;
import cn.kanyun.cpa.model.entity.CpaResult;
import cn.kanyun.cpa.model.entity.itempool.CpaOption;
import cn.kanyun.cpa.model.entity.itempool.CpaRepertory;
import cn.kanyun.cpa.model.entity.itempool.CpaSolution;
import cn.kanyun.cpa.service.ICommonService;

import java.util.List;

/**
 * Created by Administrator on 2017/6/16.
 */
public interface ICpaRepertoryService extends ICommonService<Integer,CpaRepertory> {
    public static final String SERVICE_NAME="cn.kanyun.cpa.Service.itempool.impl.CpaRepertoryServiceImpl";

    /**
     *@Author: kanyun
     *@Description: 获取试题列表（单元测试）
     *@Date: 2017/8/16 15:05
     *@params:
     */
    CpaResult getUnitExam(String where, Object[] params);

    Integer saveUnitExam(CpaRepertory cpaRepertory, List<CpaOption> cpaOptions, CpaSolution cpaSolution);
}
