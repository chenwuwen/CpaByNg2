package cn.kanyun.cpa.model.enums;

import cn.kanyun.cpa.model.enums.convert.Convert;

/**
 * 试题分类
 *
 * @author Kanyun
 */
public enum ExamClassificationEnum implements Convert {

    /**
     * 利用构造函数传参
     * 通过括号赋值,而且必须有带参构造器和属性和方法，否则编译出错
     * 赋值必须是都赋值或都不赋值，不能一部分赋值一部分不赋值
     * 如果不赋值则不能写构造器，赋值编译也出错
     */
    BASIC_ACCOUNT("会计基础"), CPU_ACCOUNT("会计电算化"), STATUTE_ETHICS("财经法规与职业道德");


    /**
     * 定义私有变量
     */

    private String value;


    /**
     * 构造函数，枚举类型的构造方法只能为私有
     *
     * @param value
     */
    ExamClassificationEnum(String value) {
        this.value = value;
    }

    /**
     * 定义get方法返回数据
     *
     * @return
     */
    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return String.valueOf(this.value);
    }

    @Override
    public String getName() {
        return this.name();
    }

    /**
     * 通过字符串找到枚举对象
     *
     * @param name
     * @return
     */
    public static ExamClassificationEnum findByName(String name) {
        ExamClassificationEnum[] objs = ExamClassificationEnum.values();
        for (ExamClassificationEnum obj : objs) {
            if (name.equals(obj.getName())) {
                return obj;
            }
        }
        return null;
    }

    public static void main(String[] args) {
        System.out.println(ExamClassificationEnum.valueOf("BASIC_ACCOUNT"));
        System.out.println(ExamClassificationEnum.BASIC_ACCOUNT.name());
        System.out.println("=================");
        for (ExamClassificationEnum exam : ExamClassificationEnum.values()) {
            System.out.println(exam.value);
            System.out.println(exam.getValue());
            System.out.println(exam.toString());
        }
    }


}
