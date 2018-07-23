package cn.kanyun.cpa.service.system;

import cn.kanyun.cpa.model.entity.system.CpaRole;
import cn.kanyun.cpa.service.CommonService;

/**
 *
 * @author Administrator
 * @date 2017/6/16
 */
public interface CpaRoleService extends CommonService<Integer,CpaRole> {
     String SERVICE_NAME="cn.kanyun.cpa.service.system.impl.CpaRoleServiceImpl";
}
