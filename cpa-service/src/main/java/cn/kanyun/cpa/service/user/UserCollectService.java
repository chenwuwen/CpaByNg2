package cn.kanyun.cpa.service.user;


import cn.kanyun.cpa.model.entity.user.CpaUser;
import cn.kanyun.cpa.model.entity.user.UserCollect;
import cn.kanyun.cpa.service.CommonService;

public interface UserCollectService extends CommonService<Long, UserCollect> {
    public static final String SERVICE_NAME = "cn.kanyun.cpa.service.user.impl.UserCollectServiceImpl";

    Integer toggleUserCollect(Integer reId, CpaUser user);
}
