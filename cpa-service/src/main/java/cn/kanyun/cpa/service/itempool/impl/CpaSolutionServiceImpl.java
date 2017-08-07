package cn.kanyun.cpa.service.itempool.impl;

import cn.kanyun.cpa.model.entity.itempool.CpaSolution;
import cn.kanyun.cpa.service.CommonServiceImpl;
import cn.kanyun.cpa.service.itempool.ICpaSolutionService;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2017/6/16.
 */
@Service(ICpaSolutionService.SERVICE_NAME)
public class CpaSolutionServiceImpl extends CommonServiceImpl<Integer, CpaSolution> implements ICpaSolutionService {

}
