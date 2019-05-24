package cn.kanyun.cpa.dao.common.annotation;

import java.lang.annotation.*;

/**
 * 在方法上使用，用于指定使用哪个数据源
 * 在Dao接口方法上使用，指定使用哪个数据源,默认使用主数据源
 */
@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataSource {
    String targetDataSource() default "master";
}
