package cn.kanyun.cpa.service.user.impl;

import cn.kanyun.cpa.dao.user.IUserCollectDao;
import cn.kanyun.cpa.model.entity.CpaResult;
import cn.kanyun.cpa.model.entity.user.CpaUser;
import cn.kanyun.cpa.model.entity.user.UserCollect;
import cn.kanyun.cpa.service.CommonServiceImpl;
import cn.kanyun.cpa.service.user.IUserCollectService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service(IUserCollectService.SERVICE_NAME)
@Transactional
public class UserCollectServiceImpl extends CommonServiceImpl<Integer, UserCollect> implements IUserCollectService {
    @Resource(name = IUserCollectDao.REPOSITORY_NAME)
    private IUserCollectDao userCollectDao;


    @Override
    public void toggleUserCollect(Integer reId, CpaUser user) {
        UserCollect userCollect = new UserCollect();
        userCollect.setReId(reId);
        userCollect.setUserId(user.getId());
        Object[] params = {reId, user.getId()};
        String where = "o.reId = ? and o.userId =?";
        CpaResult cpaResult = userCollectDao.getScrollData(-1, -1, where, params);
        if (cpaResult.getTotalCount() > 0) {
            UserCollect userCollect1 = (UserCollect) cpaResult.getData();
            if (userCollect1.getStatus() == 1) {
                userCollect.setStatus(0);
            } else {
                userCollect.setStatus(1);
            }
        }

        userCollectDao.saveOrUpdate(userCollect);
    }
}
