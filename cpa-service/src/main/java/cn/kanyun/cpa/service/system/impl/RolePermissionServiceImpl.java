package cn.kanyun.cpa.service.system.impl;

import cn.kanyun.cpa.dao.system.IRolePermissionDao;
import cn.kanyun.cpa.model.entity.system.CpaPermission;
import cn.kanyun.cpa.model.entity.system.RolePermission;
import cn.kanyun.cpa.service.CommonServiceImpl;
import cn.kanyun.cpa.service.system.IRolePermissionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Set;

/**
 * Created by Administrator on 2017/6/16.
 */
@Service(IRolePermissionService.SERVICE_NAME)
public class RolePermissionServiceImpl extends CommonServiceImpl<Integer,RolePermission> implements IRolePermissionService {

    @Resource(name=IRolePermissionDao.REPOSITORY_NAME)
    private IRolePermissionDao rolePermissionDao;


    @Override
    public Set<CpaPermission> findPermissionByRoleId(Set roleIds) {
        return null;
    }
}
