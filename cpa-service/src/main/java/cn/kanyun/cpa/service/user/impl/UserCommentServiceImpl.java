package cn.kanyun.cpa.service.user.impl;

import cn.kanyun.cpa.dao.user.UserCommentDao;
import cn.kanyun.cpa.model.entity.CpaResult;
import cn.kanyun.cpa.model.entity.user.UserComment;
import cn.kanyun.cpa.service.CommonServiceImpl;
import cn.kanyun.cpa.service.user.UserCommentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service(UserCommentService.SERVICE_NAME)
@Transactional
public class UserCommentServiceImpl extends CommonServiceImpl<Long, UserComment> implements UserCommentService {
    @Resource(name = UserCommentDao.REPOSITORY_NAME)
    private UserCommentDao userCommentDao;


    @Override
    public CpaResult getUserComment(Integer firstResult, Integer pageSize, String where, Object[] params) {
        LinkedHashMap orderby = new LinkedHashMap();
        orderby.put("commentDate", "desc");
        CpaResult cpaResult = userCommentDao.getScrollData(firstResult, pageSize, where, params, orderby);
        return cpaResult;
    }

    @Override
    public List<Map<Object, Object>> getCommentCountByCondition(Object[] fields, String where, Map<String, Collection> params) {
        List<Map<Object, Object>> list = userCommentDao.getGroupByList(fields, where, params);
        return list;
    }
}
