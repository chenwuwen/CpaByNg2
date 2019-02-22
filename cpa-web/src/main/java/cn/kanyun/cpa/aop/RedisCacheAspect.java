package cn.kanyun.cpa.aop;

import cn.kanyun.cpa.dao.common.annotation.RedisCacheProfiler;
import com.alibaba.fastjson.JSONObject;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

import java.lang.reflect.Method;

/**
 * Created by kanyun on 2018/5/24 0024.
 * Redis缓存注解AOP实现
 */
@Component
@Aspect
public class RedisCacheAspect {

    private static volatile JedisCluster jedisCluster = null;

    @Value("${redis.host}")
    private static String host;
    @Value("${redis.port}")
    private static int port;
    @Value("${redis.password}")
    private static String password;

    private static class InnerClass {
        private static HostAndPort hostAndPort = new HostAndPort(host, port);
        private static final JedisCluster jedisCluster = new JedisCluster(hostAndPort);
    }

    private RedisCacheAspect() {
    }

    public static final JedisCluster getJedisCluster() {
        return InnerClass.jedisCluster;
    }
//    public static JedisCluster getJedisCluster() {
//        if (jedisCluster == null) {
//            synchronized (JedisCluster.class) {
//                if (jedisCluster == null) {
//                    jedisCluster = new JedisCluster();
//                }
//            }
//        }
//        return jedisCluster;
//    }


    @Pointcut("@annotation(cn.kanyun.cpa.dao.common.annotation.RedisCacheProfiler)")

    public void aspectjMethod() {

    }


    @Around("aspectjMethod()")
    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {

        Object obj = null;
        try {
            //获取方法
            Method method = this.getMethod(pjp);
            RedisCacheProfiler redisCacheProfiler = method.getAnnotation(RedisCacheProfiler.class);

            //方法上没有注解，直接执行方法然后返回
            if (null == redisCacheProfiler) {
                return pjp.proceed();
            }

            //缓存key
            String cacheKey = this.getCacheKey(pjp, redisCacheProfiler);

            //true：从缓存读
            if (redisCacheProfiler.readFromCache()) {
                obj = jedisCluster.get(cacheKey);
            } else {
                return pjp.proceed();
            }
            if (null == obj) {
                obj = pjp.proceed();
            } else {
                return obj;
            }
            if (null != obj) {
                jedisCluster.setex(cacheKey, redisCacheProfiler.expire(), JSONObject.toJSONString(obj));
            }
            return obj;

        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return obj;
    }

    /**
     * @describe: 获取方法
     * @params:
     * @Author: Kanyun
     * @Date: 2018/5/24 0024 17:15
     */
    private Method getMethod(JoinPoint joinPoint) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        return method;
    }

    /**
     * @describe: 获取缓存key的方法
     * @params:
     * @Author: Kanyun
     * @Date: 2018/5/24 0024 17:22
     */
    private String getCacheKey(ProceedingJoinPoint pjp, RedisCacheProfiler redisCacheProfiler) {
        StringBuffer sb = new StringBuffer();
        if (pjp.getArgs() != null && pjp.getArgs().length != 0) {
            Object[] arr = pjp.getArgs();
            for (Object o : arr) {
                if (null != o) {
                    sb.append("_").append(String.valueOf(o));
                }
            }
            return sb.toString();
        } else {
            return sb.toString();
        }
    }

}
