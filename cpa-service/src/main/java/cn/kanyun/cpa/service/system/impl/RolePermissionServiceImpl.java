package cn.kanyun.cpa.service.system.impl;

import cn.kanyun.cpa.dao.system.RolePermissionDao;
import cn.kanyun.cpa.model.entity.system.CpaPermission;
import cn.kanyun.cpa.model.entity.system.RolePermission;
import cn.kanyun.cpa.service.CommonServiceImpl;
import cn.kanyun.cpa.service.system.RolePermissionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Set;

/**
 *
 * @author Administrator
 * @date 2017/6/16
 */
@Service(RolePermissionService.SERVICE_NAME)
public class RolePermissionServiceImpl extends CommonServiceImpl<Integer,RolePermission> implements RolePermissionService {

    @Resource(name=RolePermissionDao.REPOSITORY_NAME)
    private RolePermissionDao rolePermissionDao;


    @Override
    public Set<CpaPermission> findPermissionByRoleId(Set roleIds) {
        return null;
    }
}
