package cn.kanyun.cpa.service.system.impl;

import cn.kanyun.cpa.dao.system.CpaPermissionDao;
import cn.kanyun.cpa.model.entity.system.CpaPermission;
import cn.kanyun.cpa.service.CommonServiceImpl;
import cn.kanyun.cpa.service.system.CpaPermissionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 *
 * @author Administrator
 * @date 2017/6/16
 */
@Service(CpaPermissionService.SERVICE_NAME)
public class CpaPermissionServiceImpl extends CommonServiceImpl<Integer, CpaPermission> implements CpaPermissionService {
    @Resource(name = CpaPermissionDao.REPOSITORY_NAME)
    private CpaPermissionDao cpaPermissionDao;
}
