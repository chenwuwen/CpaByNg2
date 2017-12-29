package cn.kanyun.cpa.service.system;

import cn.kanyun.cpa.model.entity.system.CpaPermission;
import cn.kanyun.cpa.service.CommonService;

/**
 * Created by Administrator on 2017/6/16.
 */
public interface CpaPermissionService extends CommonService<Integer,CpaPermission> {
    public static final String SERVICE_NAME="cn.kanyun.cpa.service.system.impl.CpaPermissionServiceImpl";
}
