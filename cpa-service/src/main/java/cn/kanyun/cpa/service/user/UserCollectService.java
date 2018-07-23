package cn.kanyun.cpa.service.user;


import cn.kanyun.cpa.model.entity.user.CpaUser;
import cn.kanyun.cpa.model.entity.user.UserCollect;
import cn.kanyun.cpa.service.CommonService;

/**
 * @author Administrator
 */
public interface UserCollectService extends CommonService<Long, UserCollect> {
     String SERVICE_NAME = "cn.kanyun.cpa.service.user.impl.UserCollectServiceImpl";

    /**
     * @Author: kanyun
     * @Description: 试题收藏或取消收藏
     * @Date: 2017/8/16 17:02
     * @params:
     */
    Integer toggleUserCollect(Long reId, CpaUser user);
}
