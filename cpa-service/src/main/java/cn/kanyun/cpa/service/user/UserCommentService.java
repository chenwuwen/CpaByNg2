package cn.kanyun.cpa.service.user;

import cn.kanyun.cpa.dao.common.DataSourceContextHolder;
import cn.kanyun.cpa.dao.common.annotation.DataSource;
import cn.kanyun.cpa.model.entity.CpaResult;
import cn.kanyun.cpa.model.entity.user.UserComment;
import cn.kanyun.cpa.service.CommonService;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface UserCommentService extends CommonService<Long, UserComment> {

    public static final String SERVICE_NAME = "cn.kanyun.cpa.service.user.impl.UserCommentServiceImpl";

    @DataSource(targetDataSource = DataSourceContextHolder.DATA_SOURCE_SLAVE)
    CpaResult getUserComment(Integer firstResult, Integer pageSize, String where, Object[] params);

    @DataSource(targetDataSource = DataSourceContextHolder.DATA_SOURCE_SLAVE)
    List<Map<Object, Object>> getCommentCountByCondition(Object[] fields, String where, Map<String, Collection> params);
}
