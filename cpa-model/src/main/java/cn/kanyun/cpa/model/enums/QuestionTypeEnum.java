package cn.kanyun.cpa.model.enums;

public enum QuestionTypeEnum {
    // 利用构造函数传参
    UNIQUE("单选题"), MULTIPLE("多选题"), JUDGE("判断题");
    // 定义私有变量
    private String value;

    // 构造函数，枚举类型只能为私有
    private QuestionTypeEnum(String value) {

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
