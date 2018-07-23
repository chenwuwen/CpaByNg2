package cn.kanyun.cpa.service.system.impl;

import cn.kanyun.cpa.dao.system.CpaRoleDao;
import cn.kanyun.cpa.model.entity.system.CpaRole;
import cn.kanyun.cpa.service.CommonServiceImpl;
import cn.kanyun.cpa.service.system.CpaRoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 *
 * @author Administrator
 * @date 2017/6/16
 */
@Service(CpaRoleService.SERVICE_NAME)
public class CpaRoleServiceImpl extends CommonServiceImpl<Integer, CpaRole> implements CpaRoleService {
    @Resource(name = CpaRoleDao.REPOSITORY_NAME)
    private CpaRoleDao cpaRoleDao;
}
