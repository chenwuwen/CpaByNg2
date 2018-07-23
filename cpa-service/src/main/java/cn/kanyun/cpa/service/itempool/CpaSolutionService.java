package cn.kanyun.cpa.service.itempool;

import cn.kanyun.cpa.model.entity.CpaResult;
import cn.kanyun.cpa.model.entity.itempool.CpaSolution;
import cn.kanyun.cpa.service.CommonService;

import java.util.List;
import java.util.Map;

/**
 *
 * @author Kanyun
 * @date 2017/6/16
 */
public interface CpaSolutionService extends CommonService<Long, CpaSolution> {

    String SERVICE_NAME = "cn.kanyun.cpa.Service.itempool.impl.CpaSolutionServiceImpl";

    /**
     * @Author: zhaoyingxu
     * @Description: 根据试题ID获取答案
     * @Date: 2017/8/16 15:27
     * @params:
     */
    Map<Integer, String[]> getSolution(List<Long> questionIds, String typeCode);

    /**
     * @Author: kanyun
     * @Description: 检测试题答案
     * @Date: 2017/8/16 15:06
     * @params:
     */
    CpaResult compareAnswer(Map<Long, String[]> peopleAnswer, String typeCode);
}
