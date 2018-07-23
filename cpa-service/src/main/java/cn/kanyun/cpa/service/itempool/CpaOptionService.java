package cn.kanyun.cpa.service.itempool;

import cn.kanyun.cpa.model.entity.itempool.CpaOption;
import cn.kanyun.cpa.service.CommonService;

/**
 * @author Administrator
 * @date 2017/6/16
 */
public interface CpaOptionService extends CommonService<Long, CpaOption> {

    String SERVICE_NAME = "cn.kanyun.cpa.dao.itempool.impl.CpaOptionServiceImpl";
}
