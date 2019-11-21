package cn.kanyun.cpa.redis;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * 缓存管理
 * 模板方法模式 也叫钩子函数
 * @author Kanyun
 */
@Component
@Slf4j
public class CacheServiceTemplate {

    @Resource
    private RedisService redisService;
    @Resource
    private RedissonClient redissonClient;

    /**
     * 从缓存中获得对象
     * @param key 缓存的key
     * @param expire 过期时间
     * @param unit 过期时间单位
     * @param clazz 类型转换,从缓存中加载到数据,进行类型转换
     * @param cacheLoad 缓存加载器(当从缓存中没有加载到数据,则需要自定义进行加载,需要实现CacheLoad接口并进行实现)
     * @param <T>
     * @return
     */
    public <T> T getObjectFromCache(String key, long expire, TimeUnit unit, TypeReference<T> clazz, CacheLoad<T> cacheLoad) {

        T t;

        t = JSONObject.parseObject(redisService.getCache(key),clazz) ;

        if (t != null) {
            return t;
        }
//        这个地方使用了双重锁,是因为,在设置缓存,获取缓存时,在高并发环境下,会造成缓存穿透,也就是说,一开始没有缓存,突然很多线程同时请求数据,不加锁会造成所有请求都直接到达数据库
        synchronized (this) {
            t = JSONObject.parseObject(redisService.getCache(key),clazz) ;
            if (t != null) {
                return t;
            }
//            如果没有获取到缓存,需要自己实现的业务代码(也就是查数据库操作)
            t = cacheLoad.load();

//            将从数据库里查询到的数据放到缓存
            redisService.setCacheObjectForTime(key, t, expire, unit);
        }
        return t;
    }

    /**
     * 加分布式锁
     *
     * @param lockName 这个名字一定要全局唯一 所谓的全局指的是在Redis集群中
     * @param distributedLock
     * @return
     */
    public boolean redisLock(String lockName, DistributedLock distributedLock) {
        boolean isLocked = false;
//        基于Redis的Redisson分布式可重入锁RLock实现了java.util.concurrent.locks.Lock接口
//        如果负责存储这个分布式锁的Redis节点宕机以后,而且这个锁正好处于锁住的状态时,这个锁会出现死锁的状态,为了避免这个状况的发生
//        Redisson内部提供了一个监控锁的看门狗,他的作用是在Redisson实例被关闭前,不断地延长锁的有效期,默认情况下,看门狗的锁检查的超时
//        时间是30秒钟,也可以通过修改Config.lockWatchdogTimeout来另外指定
        RLock lock = redissonClient.getLock(lockName);
        if (lock == null) {
            log.error("lock is null");
            return false;
        }
        boolean lockedState = lock.isLocked();

        if (lockedState) {
            log.error("lock_redisson state seems already locked: {}, name of lock is: {}", lockedState, lockName);
        }

        try {
//             1. 最常见的使用方法
//            lock.lock();
//             2. 支持过期解锁功能,10秒钟以后自动解锁, 无需调用unlock方法手动解锁
//            lock.lock(10, TimeUnit.SECONDS);
//            lock.lockAsync()
//             3. 尝试加锁，最多等待3秒，上锁以后10秒自动解锁
            isLocked = lock.tryLock(3, 10, TimeUnit.SECONDS);
            if (isLocked) {
//                加锁成功
                distributedLock.getLockSuccess();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
//            释放锁
            lock.unlock();
        }
        return isLocked;
    }
}