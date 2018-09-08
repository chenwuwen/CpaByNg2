package cn.kanyun.cpa.redis.service.impl;

import cn.kanyun.cpa.redis.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2017/6/1.
 */

/**
 * @Resource和@Autowired都是做bean的注入时使用，其实@Resource并不是Spring的注解，它的包是javax.annotation.Resource，需要导入，但是Spring支持该注解的注入
 * 1、共同点
 * 两者都可以写在字段和setter方法上。两者如果都写在字段上，那么就不需要再写setter方法。
 * 2、不同点
 * @Autowired为Spring提供的注解，需要导入包org.springframework.beans.factory.annotation.Autowired;只按照byType注入。
 * @Autowired注解是按照类型（byType）装配依赖对象，默认情况下它要求依赖对象必须存在，如果允许null值，可以设置它的required属性为false（设置required为false可以再编译时,不报错,但是实际运用还是会报错的,不常用）。如果我们想使用按照名称（byName）来装配，可以结合@Qualifier注解一起使用
 * @Qualifier注解只能跟@Autowired一起使用,@Autowired注入非集合和数组属性时，如果发现多个匹配类型则报异常。因为按它按类型匹配，发现多个，无法确定注入哪个(此时可以使用@Qualifier注解,属性里面可以填写具体需要注入的bean的名称)
 * @Resource默认按照ByName自动注入，由J2EE提供，需要导入包javax.annotation.Resource。@Resource有两个重要的属性：name和type，而Spring将@Resource注解的name属性解析为bean的名字，而type属性则解析为bean的类型。所以，如果使用name属性，则使用byName的自动注入策略，而使用type属性时则使用byType自动注入策略。如果既不制定name也不制定type属性，这时将通过反射机制使用byName自动注入策略。
 */
@SuppressWarnings("ALL")
@Service
public class RedisServiceImpl<T> implements RedisService<T> {
    @Autowired
    @Qualifier("redisTemplate")
    public RedisTemplate<String, Object> redisTemplate;

    @Autowired
    @Qualifier("redisTemplate")
    protected RedisTemplate<Serializable, Serializable> redisTemplateSerializable;

    /**
     * 缓存基本的对象，Integer、String、实体类等
     *
     * @param key   缓存的键值
     * @param value 缓存的值
     * @return 缓存的对象
     */
    @Override
    public void setCacheObject(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     * 获得缓存的基本对象。
     *
     * @param key       缓存键值
     * @param operation
     * @return 缓存键值对应的数据
     */
    @Override
    public Object getCacheObject(String key/*,ValueOperations<String,T> operation*/) {
        return redisTemplate.opsForValue().get(key);
    }

    /**
     * 缓存List数据
     *
     * @param key      缓存的键值
     * @param dataList 待缓存的List数据
     * @return 缓存的对象
     */
    @Override
    public Object setCacheList(String key, List<Object> dataList) {
        ListOperations<String, Object> listOperation = redisTemplate.opsForList();
        if (null != dataList) {
            int size = dataList.size();
            for (int i = 0; i < size; i++) {
                listOperation.rightPush(key, dataList.get(i));
            }
        }
        return listOperation;
    }

    /**
     * 获得缓存的list对象
     *
     * @param key 缓存的键值
     * @return 缓存键值对应的数据
     */
    @Override
    public List<Object> getCacheList(String key) {
        List<Object> dataList = new ArrayList<Object>();
        ListOperations<String, Object> listOperation = redisTemplate.opsForList();
        Long size = listOperation.size(key);

        for (int i = 0; i < size; i++) {
            dataList.add(listOperation.leftPop(key));
        }
        return dataList;
    }

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
    @Override
    public List<Object> range(String key, long start, long end) {
        ListOperations<String, Object> listOperation = redisTemplate.opsForList();
        return listOperation.range(key, start, end);
    }

    /**
     * list集合长度
     *
     * @param key
     * @return
     */
    @Override
    public Long listSize(String key) {
        return redisTemplate.opsForList().size(key);
    }

    /**
     * 覆盖操作,将覆盖List中指定位置的值
     *
     * @param key
     * @param int    index 位置
     * @param String value 值
     * @return 状态码
     */
    @Override
    public void listSet(String key, int index, Object obj) {
        redisTemplate.opsForList().set(key, index, obj);
    }

    /**
     * 向List尾部追加记录
     *
     * @param String key
     * @param String value
     * @return 记录总数
     */
    @Override
    public long leftPush(String key, Object obj) {
        return redisTemplate.opsForList().leftPush(key, obj);
    }

    /**
     * 向List头部追加记录
     *
     * @param String key
     * @param String value
     * @return 记录总数
     */
    @Override
    public long rightPush(String key, Object obj) {
        return redisTemplate.opsForList().rightPush(key, obj);
    }

    /**
     * 算是删除吧，只保留start与end之间的记录
     *
     * @param String key
     * @param int    start 记录的开始位置(0表示第一条记录)
     * @param int    end 记录的结束位置（如果为-1则表示最后一个，-2，-3以此类推）
     * @return 执行状态码
     */
    @Override
    public void trim(String key, int start, int end) {
        redisTemplate.opsForList().trim(key, start, end);
    }

    /**
     * 删除List中c条记录，被删除的记录值为value
     *
     * @param String key
     * @param int    c 要删除的数量，如果为负数则从List的尾部检查并删除符合的记录
     * @param Object obj 要匹配的值
     * @return 删除后的List中的记录数
     */
    @Override
    public long remove(String key, long i, Object obj) {
        return redisTemplate.opsForList().remove(key, i, obj);
    }

    /**
     * 缓存Set
     *
     * @param key     缓存键值
     * @param dataSet 缓存的数据
     * @return 缓存数据的对象
     */
    @Override
    public BoundSetOperations<String, Object> setCacheSet(String key, Set<Object> dataSet) {
        BoundSetOperations<String, Object> setOperation = redisTemplate.boundSetOps(key);
        /*T[] t = (T[]) dataSet.toArray();
             setOperation.add(t);*/

        Iterator<Object> it = dataSet.iterator();
        while (it.hasNext()) {
            setOperation.add(it.next());
        }
        return setOperation;
    }

    /**
     * 获得缓存的set
     *
     * @param key
     * @param operation
     * @return
     */
    @Override
    public Set<Object> getCacheSet(String key/*,BoundSetOperations<String,T> operation*/) {
        Set<Object> dataSet = new HashSet<Object>();
        BoundSetOperations<String, Object> operation = redisTemplate.boundSetOps(key);

        Long size = operation.size();
        for (int i = 0; i < size; i++) {
            dataSet.add(operation.pop());
        }
        return dataSet;
    }

    /**
     * 缓存Map
     *
     * @param key
     * @param dataMap
     * @return
     */
    @Override
    public int setCacheMap(String key, Map<String, Object> dataMap) {
        if (null != dataMap) {
            HashOperations<String, Object, Object> hashOperations = redisTemplate.opsForHash();
            for (Map.Entry<String, Object> entry : dataMap.entrySet()) {
                /*System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());  */
                if (hashOperations != null) {
                    hashOperations.put(key, entry.getKey(), entry.getValue());
                } else {
                    return 0;
                }
            }
        } else {
            return 0;
        }
        return dataMap.size();
    }

    /**
     * 获得缓存的Map
     *
     * @param key
     * @param hashOperation
     * @return
     */
    @Override
    public Map<Object, Object> getCacheMap(String key/*,HashOperations<String,String,T> hashOperation*/) {
        Map<Object, Object> map = redisTemplate.opsForHash().entries(key);
        /*Map<String, T> map = hashOperation.entries(key);*/
        return map;
    }

    /**
     * 缓存Map
     *
     * @param key
     * @param dataMap
     * @return
     */
    @Override
    public void setCacheIntegerMap(String key, Map<Integer, Object> dataMap) {
        HashOperations<String, Object, Object> hashOperations = redisTemplate.opsForHash();
        if (null != dataMap) {
            for (Map.Entry<Integer, Object> entry : dataMap.entrySet()) {
                /*System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());  */
                hashOperations.put(key, entry.getKey(), entry.getValue());
            }

        }
    }

    /**
     * 获得缓存的Map
     *
     * @param key
     * @param hashOperation
     * @return
     */
    @Override
    public Map<Object, Object> getCacheIntegerMap(String key/*,HashOperations<String,String,T> hashOperation*/) {
        Map<Object, Object> map = redisTemplate.opsForHash().entries(key);
        /*Map<String, T> map = hashOperation.entries(key);*/
        return map;
    }

    /**
     * 从hash中删除指定的存储
     *
     * @param String
     * @return 状态码，1成功，0失败
     */
    @Override
    public long deleteMap(String key) {
        redisTemplate.setEnableTransactionSupport(true);
        return redisTemplate.opsForHash().delete(key);
    }

    /**
     * 设置过期时间
     *
     * @param key
     * @param time
     * @param unit
     * @return
     */
    @Override
    public boolean expire(String key, long time, TimeUnit unit) {
        return redisTemplate.expire(key, time, unit);
    }

    /**
     * increment
     *
     * @param key
     * @param step
     * @return
     */
    @Override
    public long increment(String key, long step) {
        return redisTemplate.opsForValue().increment(key, step);
    }


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
    @Override
    public long del(final byte[] key) {
        return (long) redisTemplateSerializable.execute(new RedisCallback<Object>() {
            @Override
            public Long doInRedis(RedisConnection connection) {
                return connection.del(key);
            }
        });
    }

    @Override
    @SuppressWarnings({"unchecked", "rawtypes"})
    public byte[] get(final byte[] key) {
        return (byte[]) redisTemplateSerializable.execute(new RedisCallback() {
            @Override
            public byte[] doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.get(key);
            }
        });
    }

    /**
     * @param key
     * @param value
     * @param liveTime
     */
    @Override
    public void set(final byte[] key, final byte[] value, final long liveTime) {
        redisTemplateSerializable.execute(new RedisCallback<Object>() {
            @Override
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                connection.set(key, value);
                if (liveTime > 0) {
                    connection.expire(key, liveTime);
                }
                return 1L;
            }
        });
    }

}

