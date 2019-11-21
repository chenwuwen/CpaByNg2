package cn.kanyun.cpa.model.enums;

import cn.kanyun.cpa.model.enums.convert.Convert;

/**
 * 问题类型
 *
 * @author Kanyun
 */
public enum QuestionTypeEnum implements Convert {
    /**
     * 利用构造函数传参
     */
    SINGLE("单选题"), MULTIPLE("多选题"), JUDGE("判断题");
    /**
     * 定义私有变量
     */
    private String value;

    /**
     * 构造函数，枚举类型只能为私有
     */
    QuestionTypeEnum(String value) {
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
    public static QuestionTypeEnum findByName(String name) {
        QuestionTypeEnum[] objs = QuestionTypeEnum.values();
        for (QuestionTypeEnum obj : objs) {
            if (name.equals(obj.getName())) {
                return obj;
            }
        }
        return null;
    }
}
