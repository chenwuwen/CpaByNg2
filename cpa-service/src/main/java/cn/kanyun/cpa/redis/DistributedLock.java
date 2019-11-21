package cn.kanyun.cpa.redis;

/**
 * 分布式锁
 * @author Kanyun
 */
public interface DistributedLock {

    /**
     * 成功获得锁,执行相应逻辑,由子类实现
     */
    void getLockSuccess();
}
