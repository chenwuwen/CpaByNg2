package cn.kanyun.cpa.service.system;

import cn.kanyun.cpa.model.entity.system.CpaRole;
import cn.kanyun.cpa.service.ICommonService;

/**
 * Created by Administrator on 2017/6/16.
 */
public interface ICpaRoleService extends ICommonService<Integer,CpaRole> {
    public static final String SERVICE_NAME="cn.kanyun.cpa.service.system.impl.CpaRoleServiceImpl";
}
