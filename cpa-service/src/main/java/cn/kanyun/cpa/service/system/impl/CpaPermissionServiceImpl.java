package cn.kanyun.cpa.service.system.impl;

import cn.kanyun.cpa.model.entity.system.CpaPermission;
import cn.kanyun.cpa.service.CommonServiceImpl;
import cn.kanyun.cpa.service.system.ICpaPermissionService;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2017/6/16.
 */
@Service(ICpaPermissionService.SERVICE_NAME)
public class CpaPermissionServiceImpl extends CommonServiceImpl<Integer,CpaPermission> implements ICpaPermissionService {
}
