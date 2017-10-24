package cn.kanyun.cpa.service.user;

import cn.kanyun.cpa.model.entity.user.CpaUser;
import cn.kanyun.cpa.model.entity.user.UserCollect;
import cn.kanyun.cpa.model.entity.user.UserComment;
import cn.kanyun.cpa.service.ICommonService;

public interface IUserCommentService extends ICommonService<Integer, UserComment> {
    public static final String SERVICE_NAME = "cn.kanyun.cpa.service.user.impl.UserCommentServiceImpl";

}