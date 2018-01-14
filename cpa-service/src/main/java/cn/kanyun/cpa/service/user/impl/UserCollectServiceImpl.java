package cn.kanyun.cpa.service.user.impl;

import cn.kanyun.cpa.dao.user.UserCollectDao;
import cn.kanyun.cpa.model.entity.CpaResult;
import cn.kanyun.cpa.model.entity.user.CpaUser;
import cn.kanyun.cpa.model.entity.user.UserCollect;
import cn.kanyun.cpa.service.CommonServiceImpl;
import cn.kanyun.cpa.service.user.UserCollectService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;

@Service(UserCollectService.SERVICE_NAME)
@Transactional
public class UserCollectServiceImpl extends CommonServiceImpl<Long, UserCollect> implements UserCollectService {
    @Resource(name = UserCollectDao.REPOSITORY_NAME)
    private UserCollectDao userCollectDao;


    @Override
    public Integer toggleUserCollect(Integer reId, CpaUser user) {
        UserCollect userCollect = new UserCollect();
        userCollect.setReId(reId.longValue());
        userCollect.setUserId(user.getId());
        userCollect.setCollectDate( LocalDateTime.now() );
        userCollect.setStatus(1);
        userCollect.setUsername(user.getUserName());
        userCollect.setPetname(user.getPetName());
        Object[] params = {reId, user.getId()};
        String where = "o.reId = ? and o.userId =?";
/*
        此处可能会报异常：(org.hibernate.NonUniqueObjectException: a different object with the same identifier value was already associated with the session),
        原因是查出来的实体对象(这个时候该对象是存在与session中的持久化对象[一级缓存],查出来之后session并未关闭)的id赋值给了新建的临时对象，
        在调用save方法时session中有2个oid一样的对象，hibernate不知道该让哪个持久化到库里
        解决思路：直接 clear();清空一下 session缓存不就Ok了。但是 clear打击面太广了（慎用！）。其他一些“无辜”对象也被杀掉，导致其他业务无法进行了。
        session一个evict方法，“定点清除”对象缓存。先用传回来的id用get方法（其实这里用load方法也行反正都是从缓存中加载）获得session里的持久化实体，然后杀掉，再保存临时实体
*/
        CpaResult<UserCollect> cpaResult = userCollectDao.getScrollData(-1, -1, where, params);
        if (cpaResult.getTotalCount() > 0) {
            java.util.List<UserCollect> userCollects = (java.util.List<UserCollect>) cpaResult.getData();
            userCollect.setId(userCollects.get(0).getId());
            if (userCollects.get(0).getStatus() == 1) {
                userCollect.setStatus(0);
            }
            userCollectDao.evict(userCollects.get(0));
        }
        UserCollect userCollect1 = userCollectDao.saveOrUpdate(userCollect);
        return userCollect1.getId().intValue();
    }
}
