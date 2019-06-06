package cn.kanyun.cpa.model.lombok;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Administrator on 2018/7/31.
 */
public class ExecTest {

    @Test
    public void execChain() {
        UserTestByDefault userTestByDefault = new UserTestByDefault().setAge(10).setName("kanyun");
        UserTestByLombok userTestByLombok = new UserTestByLombok().setName("wuweun").setAge(20);
        System.out.println("普通链式调用方法：" + userTestByDefault.getName() + "  " + userTestByDefault.getAge());
        System.out.println("Lombok调用方法：" + userTestByLombok.getName() + "  " + userTestByLombok.getAge());
        Assert.assertTrue(userTestByDefault.getAge() < userTestByLombok.getAge());
    }

    @Test
    public void execBuild() {
        UserBuilderByDefault userBuilderByDefault = UserBuilderByDefault.builder().name("kanyun").age(10).build();
        UserBuilderByLombok userBuilderByLombok = UserBuilderByLombok.builder().name("wuwen").age(20).build();
        System.out.println("普通Build调用方法：" + userBuilderByDefault.getName() + "  " + userBuilderByDefault.getAge());
        System.out.println("Lombok调用方法：" + userBuilderByLombok.getName() + "  " + userBuilderByLombok.getAge());
        Assert.assertTrue(userBuilderByDefault.getAge() > userBuilderByLombok.getAge());

    }
}
