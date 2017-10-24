package cn.kanyun.cpa.service.user.impl;

import cn.kanyun.cpa.dao.user.IUserCommentDao;
import cn.kanyun.cpa.model.entity.user.UserComment;
import cn.kanyun.cpa.service.CommonServiceImpl;
import cn.kanyun.cpa.service.user.IUserCommentService;
import cn.kanyun.cpa.service.user.IUserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service(IUserCommentService.SERVICE_NAME)
@Transactional
public class UserCommentServiceImpl extends CommonServiceImpl<Integer, UserComment> implements IUserCommentService {
    @Resource(name = IUserCommentDao.REPOSITORY_NAME)
    private IUserCommentDao userCommentDao;


}
