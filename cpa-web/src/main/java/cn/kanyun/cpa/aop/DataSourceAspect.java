package cn.kanyun.cpa.aop;

import cn.kanyun.cpa.dao.common.DataSourceContextHolder;
import cn.kanyun.cpa.dao.common.annotation.DataSource;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * 动态切换数据源AOP类
 * 通过Service接口上的DataSource注解中的值，来确定要切换的数据源
 */
@Order(-1) // 保证该AOP在@Transactional之前执行,此配置很重要尤其在进行事务配置之后，如果进行了事务配置，不在AOP中加这个配置,那么动态切换数据源将生效
@Component
@Aspect
public class DataSourceAspect {

    private static final Logger logger = LoggerFactory.getLogger(DataSourceAspect.class);

    /**
     * 拦截标注了DataSource注解的方法
     */
    @Pointcut("@annotation(cn.kanyun.cpa.dao.common.annotation.DataSource)")
    private void aspectjMethod() {
    }

    /**
     * 拦截目标方法，获取由@DataSource指定的数据源标识，设置到线程存储中以便切换数据源
     *
     * @param point
     * @throws Exception
     */
    @Before("aspectjMethod()")
    public void beforeAdvice(JoinPoint point) throws Exception {
        logger.info("*****************************进入选择数据源AOP,讲根据接口的@DataSource注解,选择数据源！*****************************");
        Class<?> target = point.getTarget().getClass();
        MethodSignature signature = (MethodSignature) point.getSignature();
        // 默认使用目标类型的注解，如果没有则使用其实现接口的注解
        for (Class<?> clazz : target.getInterfaces()) {
            resolveDataSource(clazz, signature.getMethod());
        }
        resolveDataSource(target, signature.getMethod());
    }

    /**
     * 提取目标对象方法注解和类型注解中的数据源标识
     *
     * @param clazz
     * @param method
     */
    private void resolveDataSource(Class<?> clazz, Method method) {
        try {
            Class<?>[] types = method.getParameterTypes();
            // 默认使用类型注解
            if (clazz.isAnnotationPresent((Class<? extends Annotation>) DataSource.class)) {
                DataSource source = clazz.getAnnotation(DataSource.class);
                DataSourceContextHolder.setDataSourceType(source.targetDataSource());
            }
            // 方法注解可以覆盖类型注解
            Method m = clazz.getMethod(method.getName(), types);
            if (m != null && m.isAnnotationPresent(DataSource.class)) {
                DataSource source = m.getAnnotation(DataSource.class);
                logger.info("当前请求选择的数据源为：" + source.targetDataSource());
                DataSourceContextHolder.setDataSourceType(source.targetDataSource());
            }
        } catch (Exception e) {
            logger.error("动态选择数据源报错：" + clazz + ":" + e.getMessage());
        }
    }

    /**
     * @describe:　方法结束后清除数据源
     * @params:
     * @Author: Kanyun
     * @Date: 2018/1/4 0004 15:22
     */
    @After("aspectjMethod()")
    public void afterAdvice(JoinPoint point) {
        logger.info("==========AOP after方法后，动态切换数据源，清除数据源==========");
        DataSourceContextHolder.clearDataSourceType();
    }
}
