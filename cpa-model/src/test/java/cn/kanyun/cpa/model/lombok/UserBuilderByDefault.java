package cn.kanyun.cpa.model.lombok;

/**
 * Created by Administrator on 2018/8/1.
 * 普通的 建筑者模式  是现在比较推崇的一种构建值对象的方式。
 */
public class UserBuilderByDefault {
    private String name;
    private int age;

    public UserBuilderByDefault(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public static UserBuilderByDefaultBuilder builder() {
        return new UserBuilderByDefaultBuilder();
    }

    public static class UserBuilderByDefaultBuilder {
        private String name;
        private int age;

        UserBuilderByDefaultBuilder() {

        }

        public UserBuilderByDefaultBuilder name(String name) {
            this.name = name;
            return this;
        }

        public UserBuilderByDefaultBuilder age(int age) {
            this.age = age;
            return this;
        }

        public UserBuilderByDefault build() {
            return new UserBuilderByDefault(name, age);
        }
    }

}
