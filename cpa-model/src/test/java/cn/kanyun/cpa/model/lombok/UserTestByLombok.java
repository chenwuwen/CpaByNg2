package cn.kanyun.cpa.model.lombok;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;


/**
 * Created by Administrator on 2018/7/31.
 * <p>
 * lombok方式实现链式调用
 */

/**
 * @Getter / @Setter注释任何字段（当然也可以注释到类上的），让lombok自动生成默认的getter / setter方法。
 * 默认生成的方法是public的，如果要修改方法修饰符可以设置AccessLevel的值，例如：@Getter(access = AccessLevel.PROTECTED)
 * @Accessors 主要用于控制生成的getter和setter
 * 主要参数介绍
 * fluent boolean值，默认为false。此字段主要为控制生成的getter和setter方法前面是否带get/set
 * chain boolean值，默认false。如果设置为true，setter返回的是此对象，方便链式调用方法
 * prefix 设置前缀 例如：@Accessors(prefix = "abc") private String abcAge 当生成get/set方法时，会把此前缀去掉
 */

@Accessors(chain = true)
@Setter
@Getter
public class UserTestByLombok {
    private String name;
    private int age;

}
