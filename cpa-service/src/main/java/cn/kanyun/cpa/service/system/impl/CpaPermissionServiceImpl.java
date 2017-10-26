package cn.kanyun.cpa.service.system.impl;

import cn.kanyun.cpa.dao.system.ICpaPermissionDao;
import cn.kanyun.cpa.model.entity.system.CpaPermission;
import cn.kanyun.cpa.service.CommonServiceImpl;
import cn.kanyun.cpa.service.system.ICpaPermissionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by Administrator on 2017/6/16.
 */
@Service(ICpaPermissionService.SERVICE_NAME)
public class CpaPermissionServiceImpl extends CommonServiceImpl<Integer, CpaPermission> implements ICpaPermissionService {
    @Resource(name = ICpaPermissionDao.REPOSITORY_NAME)
    private ICpaPermissionDao cpaPermissionDao;
}
