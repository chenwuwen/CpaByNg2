package cn.kanyun.cpa.service.user.impl;

import cn.kanyun.cpa.dao.user.AttendanceDao;
import cn.kanyun.cpa.dao.user.UserDao;
import cn.kanyun.cpa.model.constants.CpaConstants;
import cn.kanyun.cpa.model.entity.CpaResult;
import cn.kanyun.cpa.model.entity.user.Attendance;
import cn.kanyun.cpa.model.entity.user.CpaUser;
import cn.kanyun.cpa.model.entity.user.CpaUserExtend;
import cn.kanyun.cpa.service.CommonServiceImpl;
import cn.kanyun.cpa.service.user.AttendanceService;
import cn.kanyun.cpa.util.DateUtils;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.LinkedHashMap;
import java.util.concurrent.TimeUnit;

/**
 * @author Administrator
 */
@Service(AttendanceService.SERVICE_NAME)
@Transactional
public class AttendanceServiceImpl extends CommonServiceImpl<Long, Attendance> implements AttendanceService {
    private static final Logger logger = LoggerFactory.getLogger(AttendanceServiceImpl.class);

    private static final long time = 24 * 60 * 60 * 1000;

    /**
     * 设置本地缓存,使用了Guava包的缓存工具(不使用ConcurrentMap的原因是因为ConcurrentMap需要显视的移除,不能自动回收)
     * guava缓存包的创建使用了CacheBuilder
     * 我们可以构建出两种类型的cache，他们分别是Cache与LoadingCache。
     * LoadingCache，顾名思义，它能够通过CacheLoader自发的加载缓存(如项目启动时查询数据库放入缓存)
     */
    private static Cache<CpaUser, Long> cache;

    static {
//        CacheBuilder的构造函数是私有的，只能通过其静态方法newBuilder()来获得CacheBuilder的实例
        cache = CacheBuilder.newBuilder()
//               使用弱引用存储键。当键没有其它（强或软）引用时，该缓存可能会被回收。
                .weakKeys()
//               使用弱引用存储值。当值没有其它（强或软）引用时，该缓存可能会被回收
//                .weakValues()
//               使用软引用存储值。当内存不足并且该值其它强引用引用时，该缓存就会被回收
//                .softValues()
//                设置写缓存后多久过期(java8后可以使用Duration设置)
//                .expireAfterWrite(8, TimeUnit.SECONDS)
//                在10分钟内未访问则过期(java8后可以使用Duration设置)
                .expireAfterAccess(Duration.ofMinutes(10))
//                设置并发级别为8，并发级别是指可以同时写缓存的线程数
                .concurrencyLevel(8)
//               设置缓存容器的初始容量为10
                .initialCapacity(10)
//               设置缓存最大容量为100，超过100之后就会按照LRU最近虽少使用算法来移除缓存项
                .maximumSize(100)
//               设置缓存的移除通知
                .removalListener(new RemovalListener<Object, Object>() {
                    @Override
                    public void onRemoval(RemovalNotification<Object, Object> notification) {
                        logger.info("AttendanceServiceImpl Cache:{} was removed, cause is {}", notification.getKey(), notification.getCause());
                    }
                })

                .build();
    }


    @Resource(name = AttendanceDao.REPOSITORY_NAME)
    private AttendanceDao attendanceDao;
    @Resource(name = UserDao.REPOSITORY_NAME)
    private UserDao userDao;

    @Override
    @Transactional(rollbackFor = {RuntimeException.class, Exception.class})
    public CpaResult signIn(CpaUser user, CpaUserExtend userExtend) {
        CpaResult result = new CpaResult();
        Attendance attendance = new Attendance();
        attendance.setAttendanceDate(LocalDateTime.now());
        attendance.setUserId(user.getId());
        attendance.setNickName(user.getNickName());
        //判断当前未打卡前上一次打卡,与当前时间差值
        if (this.isSeriesSignIn(user)) {
            //设置连续打卡时间,为之前连续打卡时间加1
            userExtend.setSeriesSignInDay(userExtend.getSeriesSignInDay() + 1);
        } else {
            //设置连续打卡时间为1
            userExtend.setSeriesSignInDay(1);
        }
        save(attendance);
        userDao.update(user);
        result.setState(CpaConstants.OPERATION_SUCCESS);
        return result;
    }

    /**
     * 判断当前打卡是否连续,判断当前打卡与上次打卡间隔,超过两天返回false
     *
     * @param user
     * @return
     */
    public Boolean isSeriesSignIn(CpaUser user) {
        String where = "o.userId=?";
        Object[] params = {user.getId()};
        LinkedHashMap orderBy = new LinkedHashMap() {{
            put("attendanceDate", "desc");
        }};
//        时间倒序,仅取一条记录,提高性能
        CpaResult result = getScrollData(1, 1, where, params, orderBy);
        Attendance attendance = (Attendance) result.getData();
//        获取上次签到时间
        long lastSigInTime = attendance.getAttendanceDate().toEpochSecond(ZoneOffset.of("+8"));
//        存到缓存中
        cache.put(user, lastSigInTime);
        if (System.currentTimeMillis() - lastSigInTime > 2 * time) {
            //上次签到时间与当前相差一天
            return false;
        }
        return true;
    }


    /**
     * 判断今天是否打过卡
     *
     * @param user
     * @return
     */
    public Boolean isTodaySignIn(CpaUser user) {
        long lastSigInTime = 0;
//        从本地缓存中取出数据
        lastSigInTime = cache.getIfPresent(user);
        if (lastSigInTime == 0) {
            String where = "o.userId=?";
            Object[] params = {user.getId()};
            LinkedHashMap orderBy = new LinkedHashMap() {{
                put("attendanceDate", "desc");
            }};
//        时间倒序,仅取一条记录,提高性能
            CpaResult result = getScrollData(1, 1, where, params, orderBy);
            Attendance attendance = (Attendance) result.getData();
//        获取上次签到时间(转换成秒数)
            lastSigInTime = attendance.getAttendanceDate().toEpochSecond(ZoneOffset.of("+8"));
        }
//        获取今天的结束时间,判断两次时间间隔是否在24小时内
        if (DateUtils.getDayEnd().toEpochSecond(ZoneOffset.of("+8")) - lastSigInTime > time) {
            //上次签到时间与当前相差一天
            return true;
        }
        return false;
    }
}
