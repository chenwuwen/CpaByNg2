package cn.kanyun.cpa.dao.system;

import cn.kanyun.cpa.dao.CommonDao;
import cn.kanyun.cpa.model.entity.system.CpaPermission;

/**
 *
 * @author Kanyun
 * @date 2017/6/16
 */
public interface CpaPermissionDao extends CommonDao<Integer, CpaPermission> {
    String REPOSITORY_NAME = "cn.kanyun.cpa.dao.system.impl.CpaPermissionDaoImpl";
}
