package cn.kanyun.cpa.redis;

import org.springframework.data.redis.core.BoundSetOperations;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2017/6/5.
 */
public interface RedisService<T> {

    String SERVICE_NAME = "cn.kanyun.cpa.redis.RedisServiceImpl";

    /**
     * 缓存基本的对象，Integer、String、实体类等
     *
     * @param key   缓存的键值
     * @param value 缓存的值
     * @return 缓存的对象
     */
    void setCacheObject(String key, Object value);

    /**
     * 缓存基本的对象，Integer、String、实体类等
     *
     * @param key      缓存的键值
     * @param value    缓存的值
     * @param time     缓存时间
     * @param timeUnit 缓存时间单位
     * @return 缓存的对象
     */
    void setCacheObjectForTime(String key, Object value, long time, TimeUnit timeUnit);

    /**
     * 获得缓存的基本对象。
     *
     * @param key       缓存键值
     * @param operation
     * @return 缓存键值对应的数据
     */
    <T> T getCacheObject(String key, Class<T> clazz);


    String getCache(String key);

    /**
     * 缓存List数据
     *
     * @param key      缓存的键值
     * @param dataList 待缓存的List数据
     * @return 缓存的对象
     */
    void setCacheList(String key, List<Object> dataList);

    /**
     * 获得缓存的list对象
     *
     * @param key 缓存的键值
     * @return 缓存键值对应的数据
     */
    List<T> getCacheList(String key);

    /**
     * @param
     * @return
     * @Description: 如果 key 存在则覆盖,并返回旧值.
     * 如果不存在,返回null 并添加
     * @auther: kanyun
     * @date: 2018/12/28 8:54
     */
    Object getAndSet(String key, String value);

    /**
     * @param step 步进数 ，即 每次加几
     * @return
     * @Description: 对一个 key-value 的值进行加减操作,
     * 如果该 key 不存在 将创建一个key 并赋值该 number
     * 如果 key 存在,但 value 不是长整型 ,将报错
     * @auther: zhaoyingxu
     * @date: 2018/12/28 8:56
     */
    Long increment(String key, long step);

    /**
     * @param step 步进数 ，即 每次加几
     * @return
     * @Description: 对一个 key-value 的值进行加减操作,
     * 如果该 key 不存在 将创建一个key 并赋值该 number
     * 如果 key 存在,但 value 不是 纯数字 ,将报错
     * @auther: zhaoyingxu
     * @date: 2018/12/28 8:56
     */
    Double increment(String key, double step);

    /**
     * 获取指定key 的过期时间
     *
     * @param key
     * @return
     */
    Long getExpire(String key);

    /**
     * 移除指定key 的过期时间
     *
     * @param key
     * @return
     */
    boolean persist(String key);

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
    List<Object> range(String key, long start, long end);

    /**
     * list集合长度
     *
     * @param key
     * @return
     */
    Long listSize(String key);

    /**
     * 覆盖操作,将覆盖List中指定位置的值
     *
     * @param key
     * @param int    index 位置
     * @param String value 值
     * @return 状态码
     */
    void listSet(String key, int index, Object obj);

    /**
     * 向List尾部追加记录
     *
     * @param String key
     * @param String value
     * @return 记录总数
     */
    long leftPush(String key, Object obj);

    /**
     * 从左边依次入栈
     * 导入顺序按照 Collection 顺序
     * 如: a b c => c b a
     *
     * @param String     key
     * @param Collection value
     * @return 记录总数
     */
    long leftPushAll(String key, Collection<Object> values);

    /**
     * 指定 list 从左出栈
     * 如果列表没有元素,会堵塞到列表一直有元素或者超时为止
     *
     * @param key
     * @return 出栈的值
     */
    Object leftPop(String key);

    /**
     * 向List头部追加记录
     * 指定 list 从右入栈
     *
     * @param String key
     * @param String value
     * @return 记录总数
     */
    long rightPush(String key, Object obj);

    /**
     * 从右边依次入栈
     * 导入顺序按照 Collection 顺序
     * 如: a b c => a b c
     *
     * @param key
     * @param values
     * @return
     */
    long rightPushAll(String key, Collection<Object> values);

    /**
     * 指定 list 从右出栈
     * 如果列表没有元素,会堵塞到列表一直有元素或者超时为止
     *
     * @param key
     * @return 出栈的值
     */
    Object rightPop(String key);

    /**
     * 根据下标获取值
     *
     * @param key
     * @param index
     * @return
     */
    Object popIndex(String key, long index);

    /**
     * 获取列表指定长度
     *
     * @param key
     * @param index
     * @return
     */
    Long listSize(String key, long index);


    /**
     * 算是删除吧，只保留start与end之间的记录
     *
     * @param String key
     * @param int    start 记录的开始位置(0表示第一条记录)
     * @param int    end 记录的结束位置（如果为-1则表示最后一个，-2，-3以此类推）
     * @return 执行状态码
     */
    void trim(String key, int start, int end);

    /**
     * 删除List中c条记录，被删除的记录值为value
     *
     * @param String key
     * @param int    c 要删除的数量，如果为负数则从List的尾部检查并删除符合的记录
     * @param Object obj 要匹配的值
     * @return 删除后的List中的记录数
     */
    long remove(String key, long i, Object obj);

    /**
     * 缓存Set
     *
     * @param key     缓存键值
     * @param dataSet 缓存的数据
     * @return 缓存数据的对象
     */
    BoundSetOperations<String, Object> setCacheSet(String key, Set<Object> dataSet);

    /**
     * 获得缓存的set
     *
     * @param key
     * @param operation
     * @return
     */
    Set<Object> getCacheSet(String key/*,BoundSetOperations<String,T> operation*/);

    /**
     * 缓存Map
     *
     * @param key
     * @param dataMap
     * @return
     */
    int setCacheMap(String key, Map<String, Object> dataMap);

    /**
     * 获得缓存的Map
     *
     * @param key
     * @param hashOperation
     * @return
     */
    Map<Object, Object> getCacheMap(String key/*,HashOperations<String,String,T> hashOperation*/);


    /**
     * 从hash中删除指定的存储
     *
     * @param String
     * @return 状态码，1成功，0失败
     */
    long deleteMap(String key);

    /**
     * 设置过期时间
     *
     * @param key
     * @param time
     * @param unit
     * @return
     */
    boolean expire(String key, long time, TimeUnit unit);

    /**
     * 重命名Key
     *
     * @param oldKey
     * @param newKey
     * @return Boolean
     */
    Boolean renameKey(String oldKey, String newKey);

    /**
     * 将 key 与 otherKey 的并集,保存到 destKey 中
     *
     * @param key
     * @param otherKey
     * @param destKey
     * @return destKey 数量
     */
    Long unionAndStoreSet(String key, String otherKey, String destKey);


    /**
     * key 和 other 两个集合的并集,保存在 destKey 集合中, 列名相同的 score 相加
     *
     * @param key
     * @param otherKey
     * @param destKey
     * @return
     */
    Long unionAndStoreZset(String key, Collection<String> otherKeys, String destKey);

    /**
     * key 和 other 两个集合的并集,保存在 destKey 集合中, 列名相同的 score 相加
     *
     * @param key
     * @param otherKey
     * @param destKey
     * @return
     */
    Long unionAndStoreZset(String key, String otherKey, String destKey);

    /**
     * 删除指定索引位置的成员,其中成员分数按( 从小到大 )
     *
     * @param key
     * @param start
     * @param end
     * @return
     */
    Long removeRange(String key, long start, long end);


    /**
     * 删除指定 分数范围 内的成员 [main,max],其中成员分数按( 从小到大 )
     *
     * @param key
     * @param min
     * @param max
     * @return
     */
    Long removeRangeByScore(String key, double min, double max);


    /**
     * key 和 otherKey 两个集合的交集,保存在 destKey 集合中
     *
     * @param key
     * @param otherKey
     * @param destKey
     * @return
     */
    Long intersectAndStore(String key, String otherKey, String destKey);


    /**
     * key 和 otherKeys 多个集合的交集,保存在 destKey 集合中
     *
     * @param key
     * @param otherKeys
     * @param destKey
     * @return
     */
    Long intersectAndStore(String key, Collection<String> otherKeys, String destKey);


    //redisTemplateSerializable

    /**
     * 删除redis的所有数据
     */
    /*@SuppressWarnings({"unchecked", "rawtypes"})
     String flushDB() {
        return redisTemplateSerializable.execute(new RedisCallback() {
             String doInRedis(RedisConnection connection) throws DataAccessException {
                connection.flushDb();
                return "ok";
            }
        });
    }*/
    long del(final byte[] key);

    @SuppressWarnings({"unchecked", "rawtypes"})
    byte[] get(final byte[] key);

    /**
     * @param key
     * @param value
     * @param liveTime
     */
    void set(final byte[] key, final byte[] value, final long liveTime);
}
