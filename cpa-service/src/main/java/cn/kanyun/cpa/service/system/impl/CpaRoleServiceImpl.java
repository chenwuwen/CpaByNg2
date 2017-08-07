package cn.kanyun.cpa.service.system.impl;

import cn.kanyun.cpa.model.entity.system.CpaRole;
import cn.kanyun.cpa.service.CommonServiceImpl;
import cn.kanyun.cpa.service.system.ICpaRoleService;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2017/6/16.
 */
@Service(ICpaRoleService.SERVICE_NAME)
public class CpaRoleServiceImpl extends CommonServiceImpl<Integer,CpaRole> implements ICpaRoleService {
}
