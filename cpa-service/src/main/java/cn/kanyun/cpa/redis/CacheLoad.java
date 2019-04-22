package cn.kanyun.cpa.redis;

/**
 * 数据获取接口
 * @param <T>
 */
public interface CacheLoad<T> {

    T load();
}
