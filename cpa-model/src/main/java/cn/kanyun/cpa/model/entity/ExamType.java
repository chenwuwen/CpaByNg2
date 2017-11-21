package cn.kanyun.cpa.model.entity;

public enum ExamType {

    /**
     * 利用构造函数传参利用构造函数传参
     * 通过括号赋值,而且必须有带参构造器和属性和方法，否则编译出错
     * 赋值必须是都赋值或都不赋值，不能一部分赋值一部分不赋值
     * 如果不赋值则不能写构造器，赋值编译也出错
     */

    // 利用构造函数传参
    BASICACCOUNT("会计基础"), CPUACCOUNT("会计电算化"), STATUTEETHICS("财经法规与职业道德");
    // 定义私有变量
    private String value;


    // 构造函数，枚举类型只能为私有
    private ExamType(String value) {

        this.value = value;
    }

    // 定义get方法返回数据
    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return String.valueOf(this.value);
    }
}
