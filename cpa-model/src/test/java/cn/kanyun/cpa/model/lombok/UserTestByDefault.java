package cn.kanyun.cpa.model.lombok;

/**
 * Created by Administrator on 2018/7/31.
 * <p>
 * 测试链式调用
 */
public class UserTestByDefault {
    private String name;
    private int age;

    public String getName() {
        return name;
    }

    public UserTestByDefault setName(String name) {
        this.name = name;
        return this;
    }

    public int getAge() {
        return age;
    }

    public UserTestByDefault setAge(int age) {
        this.age = age;
        return this;
    }

}
