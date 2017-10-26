package cn.kanyun.cpa.service.system.impl;

import cn.kanyun.cpa.dao.system.ICpaRoleDao;
import cn.kanyun.cpa.dao.system.impl.CpaRoleDaoImpl;
import cn.kanyun.cpa.model.entity.system.CpaRole;
import cn.kanyun.cpa.service.CommonServiceImpl;
import cn.kanyun.cpa.service.system.ICpaRoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by Administrator on 2017/6/16.
 */
@Service(ICpaRoleService.SERVICE_NAME)
public class CpaRoleServiceImpl extends CommonServiceImpl<Integer, CpaRole> implements ICpaRoleService {
    @Resource(name = ICpaRoleDao.REPOSITORY_NAME)
    private ICpaRoleDao cpaRoleDao;
}
