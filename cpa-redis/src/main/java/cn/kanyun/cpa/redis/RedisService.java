package cn.kanyun.cpa.redis;

import org.springframework.data.redis.core.BoundSetOperations;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2017/6/5.
 */
public interface RedisService<T> {
    public void setCacheObject(String key, Object value);

    /**
     * 获得缓存的基本对象。
     *
     * @param key       缓存键值
     * @param operation
     * @return 缓存键值对应的数据
     */
    public Object getCacheObject(String key/*,ValueOperations<String,T> operation*/);

    /**
     * 缓存List数据
     *
     * @param key      缓存的键值
     * @param dataList 待缓存的List数据
     * @return 缓存的对象
     */
    public Object setCacheList(String key, List<Object> dataList);

    /**
     * 获得缓存的list对象
     *
     * @param key 缓存的键值
     * @return 缓存键值对应的数据
     */
    public List<Object> getCacheList(String key);

    /**
     * 获得缓存的list对象
     *
     * @param @param  key
     * @param @param  start
     * @param @param  end
     * @param @return
     * @return List<T>    返回类型
     * @throws
     * @Title: range
     * @Description: TODO(这里用一句话描述这个方法的作用)
     */
    public List<Object> range(String key, long start, long end);

    /**
     * list集合长度
     *
     * @param key
     * @return
     */
    public Long listSize(String key);

    /**
     * 覆盖操作,将覆盖List中指定位置的值
     *
     * @param key
     * @param int    index 位置
     * @param String value 值
     * @return 状态码
     */
    public void listSet(String key, int index, Object obj);

    /**
     * 向List尾部追加记录
     *
     * @param String key
     * @param String value
     * @return 记录总数
     */
    public long leftPush(String key, Object obj);

    /**
     * 向List头部追加记录
     *
     * @param String key
     * @param String value
     * @return 记录总数
     */
    public long rightPush(String key, Object obj);

    /**
     * 算是删除吧，只保留start与end之间的记录
     *
     * @param String key
     * @param int    start 记录的开始位置(0表示第一条记录)
     * @param int    end 记录的结束位置（如果为-1则表示最后一个，-2，-3以此类推）
     * @return 执行状态码
     */
    public void trim(String key, int start, int end);

    /**
     * 删除List中c条记录，被删除的记录值为value
     *
     * @param String key
     * @param int    c 要删除的数量，如果为负数则从List的尾部检查并删除符合的记录
     * @param Object obj 要匹配的值
     * @return 删除后的List中的记录数
     */
    public long remove(String key, long i, Object obj);

    /**
     * 缓存Set
     *
     * @param key     缓存键值
     * @param dataSet 缓存的数据
     * @return 缓存数据的对象
     */
    public BoundSetOperations<String, Object> setCacheSet(String key, Set<Object> dataSet);

    /**
     * 获得缓存的set
     *
     * @param key
     * @param operation
     * @return
     */
    public Set<Object> getCacheSet(String key/*,BoundSetOperations<String,T> operation*/);

    /**
     * 缓存Map
     *
     * @param key
     * @param dataMap
     * @return
     */
    public int setCacheMap(String key, Map<String, Object> dataMap);

    /**
     * 获得缓存的Map
     *
     * @param key
     * @param hashOperation
     * @return
     */
    public Map<Object, Object> getCacheMap(String key/*,HashOperations<String,String,T> hashOperation*/);

    /**
     * 缓存Map
     *
     * @param key
     * @param dataMap
     * @return
     */
    public void setCacheIntegerMap(String key, Map<Integer, Object> dataMap);

    /**
     * 获得缓存的Map
     *
     * @param key
     * @param hashOperation
     * @return
     */
    public Map<Object, Object> getCacheIntegerMap(String key/*,HashOperations<String,String,T> hashOperation*/);

    /**
     * 从hash中删除指定的存储
     *
     * @param String
     * @return 状态码，1成功，0失败
     */
    public long deleteMap(String key);

    /**
     * 设置过期时间
     *
     * @param key
     * @param time
     * @param unit
     * @return
     */
    public boolean expire(String key, long time, TimeUnit unit);

    /**
     * increment
     *
     * @param key
     * @param step
     * @return
     */
    public long increment(String key, long step);


    //redisTemplateSerializable

    /**
     * 删除redis的所有数据
     */
    /*@SuppressWarnings({"unchecked", "rawtypes"})
    public String flushDB() {
        return redisTemplateSerializable.execute(new RedisCallback() {
            public String doInRedis(RedisConnection connection) throws DataAccessException {
                connection.flushDb();
                return "ok";
            }
        });
    }*/
    public long del(final byte[] key);

    @SuppressWarnings({"unchecked", "rawtypes"})
    public byte[] get(final byte[] key);

    /**
     * @param key
     * @param value
     * @param liveTime
     */
    public void set(final byte[] key, final byte[] value, final long liveTime);
}
