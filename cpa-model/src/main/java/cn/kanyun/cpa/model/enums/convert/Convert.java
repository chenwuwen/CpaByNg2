package cn.kanyun.cpa.model.enums.convert;

/**
 * Hibernate Enum类型转换接口
 * https://blog.csdn.net/xtiawxf/article/details/53710197
 * @author Kanyun
 */
public interface Convert {

    /**
     * 返回枚举的名称(非Value)
     * @return string
     */
    String getName();
}
