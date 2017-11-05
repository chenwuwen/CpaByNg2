package cn.kanyun.cpa.service.user.impl;

import cn.kanyun.cpa.dao.user.IUserCommentDao;
import cn.kanyun.cpa.model.entity.CpaResult;
import cn.kanyun.cpa.model.entity.user.CpaUser;
import cn.kanyun.cpa.model.entity.user.UserComment;
import cn.kanyun.cpa.service.CommonServiceImpl;
import cn.kanyun.cpa.service.user.IUserCommentService;
import cn.kanyun.cpa.service.user.IUserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Service(IUserCommentService.SERVICE_NAME)
@Transactional
public class UserCommentServiceImpl extends CommonServiceImpl<Integer, UserComment> implements IUserCommentService {
    @Resource(name = IUserCommentDao.REPOSITORY_NAME)
    private IUserCommentDao userCommentDao;


    @Override
    public CpaResult getUserComment(Integer firstResult,Integer pageSize,String where ,Object[] params) {
        CpaResult cpaResult = userCommentDao.getScrollData(firstResult, pageSize, where, params);
        return cpaResult;
    }

    @Override
    public List<Map<Object,Object>> getCommentCountByCondition(Object[] fields, String where, Map<String,Collection> params) {
        List<Map<Object,Object>> list=userCommentDao.getGroupByList(fields,where,params);
        return list;
    }
}
