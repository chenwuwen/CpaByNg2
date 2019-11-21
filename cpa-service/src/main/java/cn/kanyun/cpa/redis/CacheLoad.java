package cn.kanyun.cpa.redis;

/**
 * 数据获取接口
 *
 * @param <T>
 * @author Kanyun
 * @see CacheServiceTemplate
 */
public interface CacheLoad<T> {

    /**
     * 加载数据,当从缓存从取不到数据时,根据业务从其他地方获取数据
     * @return
     */
    T load();
}
