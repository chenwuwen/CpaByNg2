package cn.kanyun.cpa.service.user;

import cn.kanyun.cpa.model.entity.CpaResult;
import cn.kanyun.cpa.model.entity.user.CpaUser;
import cn.kanyun.cpa.model.entity.user.UserComment;
import cn.kanyun.cpa.service.ICommonService;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface IUserCommentService extends ICommonService<Integer, UserComment> {
    public static final String SERVICE_NAME = "cn.kanyun.cpa.service.user.impl.UserCommentServiceImpl";

    CpaResult getUserComment(Integer firstResult,Integer pageSize,String where ,Object[] params);

    List<Map<Object,Object>> getCommentCountByCondition(Object[] fields, String where, Map<String,Collection> params);
}
