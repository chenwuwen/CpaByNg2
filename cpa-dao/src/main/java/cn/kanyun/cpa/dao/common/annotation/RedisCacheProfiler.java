package cn.kanyun.cpa.dao.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * redis结合自定义注解实现基于方法的注解缓存
 * 如果方法标注了@RedisCacheProfiler注解则走aop
 * 如果获取到RedisCacheProfiler类，并且readFromCache()设置的是true，就去getCacheKey()获取缓存的key
 * 根据缓存的key值去redis中查询，如果有就查询缓存，如果没有就执行方法，将方法的返回值作为value存入缓存,并根据RedisCacheProfiler的expire()设置的过期时间给key加上过期时间
 * @author kanyun
 * @date 2018/5/24 0024.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface RedisCacheProfiler {

    /**
     * cacheKey() 缓存key的前缀，缓存key由key前缀+入参组成
     * @return
     */
    String cacheKey();

    /**
     * expire() 缓存的过去时间，默认为60秒
     * @return
     */
    int expire() default 60;

    /**
     * readFromCache() 是否读取缓存，默认为true
     * @return
     */
    boolean readFromCache() default true;

}
