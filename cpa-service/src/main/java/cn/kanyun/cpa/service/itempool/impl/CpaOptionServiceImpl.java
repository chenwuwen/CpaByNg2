package cn.kanyun.cpa.service.itempool.impl;


import cn.kanyun.cpa.model.entity.itempool.CpaOption;
import cn.kanyun.cpa.service.CommonServiceImpl;
import cn.kanyun.cpa.service.itempool.ICpaOptionService;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2017/6/16.
 */
@Service(ICpaOptionService.SERVICE_NAME)
public class CpaOptionServiceImpl extends CommonServiceImpl<Integer, CpaOption> implements ICpaOptionService {

}
