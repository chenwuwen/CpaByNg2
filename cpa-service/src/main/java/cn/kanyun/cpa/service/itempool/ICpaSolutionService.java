package cn.kanyun.cpa.service.itempool;

import cn.kanyun.cpa.model.dto.itempool.CpaRepertoryDto;
import cn.kanyun.cpa.model.entity.CpaResult;
import cn.kanyun.cpa.model.entity.itempool.CpaSolution;
import cn.kanyun.cpa.service.ICommonService;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/6/16.
 */
public interface ICpaSolutionService extends ICommonService<Integer,CpaSolution> {
    public static final String SERVICE_NAME="cn.kanyun.cpa.Service.itempool.impl.CpaSolutionServiceImpl";

    /**
     *@Author: zhaoyingxu
     *@Description: 根据试题ID获取答案
     *@Date: 2017/8/16 15:27
     *@params:
     */
    public Map<Integer,String> getSolution(List<Integer> respertoryIds);

    /**
     *@Author: kanyun
     *@Description: 检测试题答案
     *@Date: 2017/8/16 15:06
     *@params:
     */
    CpaResult compareAnswer(Map<Integer,String> peopleAnswer,String typeCode);
}
