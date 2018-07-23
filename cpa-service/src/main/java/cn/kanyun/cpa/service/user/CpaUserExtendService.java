package cn.kanyun.cpa.service.user;

import cn.kanyun.cpa.model.entity.user.CpaUserExtend;
import cn.kanyun.cpa.service.CommonService;

/**
 * @author Administrator
 */
public interface CpaUserExtendService extends CommonService<Long, CpaUserExtend> {
    String SERVICE_NAME = "cn.kanyun.cpa.service.user.impl.CpaUserExtendServiceImpl";
}
