package cn.kanyun.cpa.util;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
/**
 * 通过该类即可在普通工具类【什么是普通的类，就是没有@Controller,@Service,@Repository,@Component等
 * 注解修饰的类，同时xml文件中，也没有相应的配置。适用场景，当我们要开发自己的框架的时候，
 * 就可能会用到这种技术手段。常规的开发是用不到的。当这个对象必须由我们创建的时候，但是又需要用到一些spring容器里面的对象，
 * 这个时候就可以适用当前场景了】,里获取spring管理的bean【类似于在普通类中使用@Resource 、@Autowired 注入对象】
 *
 * 此类需要加入到spring的配置文件里，<bean class="app.util.spring.SpringTool"/>
 * 然后就可以了，就可以在任何一个普通的工具类里，根据spring里配置好的bean的id，得到这个注入好的对象了
 * 使用方法： ArticleService articleService = (ArticleService) SpringTool.getBeanById("articleService");
 * 需要注意的是 不管是使用SpringTool.getBeanById() 获取bean 或者是使用SpringTool.getBeanByClassName()来获取
 * 都需要将参数填写准确,否则报错无法获取Bean【No bean named 'xxxx' available】,什么是准确的呢?如果你要注入的Bean是Service，那么该Service上的
 * @Service 注解中是否存在自定义id,如果存在自定义id 例如 注解形式如 @Service(xx) ,那么只能使用SpringTool.getBeanById(xx)
 * 来获取,如果注解形式如 @Service 则使用getBeanById() getBeanByClassName() 都可以【即上述的使用方法】
 * @author kanyun
 *
 */
public class SpringTools implements ApplicationContextAware {

    private static ApplicationContext applicationContext = null;

    /**
     * 该复写方法会在项目启动时执行
     * @param applicationContext
     * @throws BeansException
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (SpringTools.applicationContext == null) {
            SpringTools.applicationContext = applicationContext;
            System.out.println(
                    "========ApplicationContext配置成功,在普通类可以通过调用SpringTools.getAppContext()获取applicationContext对象,applicationContext="
                            + applicationContext + "========");
        }
    }

    /**
     * 该方法用于外界获取ApplicationContext对象
     * @return
     */
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    /**
     * 根据类名获取到bean
     * @param clazz
     * @param <T>
     * @return
     * @throws BeansException
     */
    public static <T> T getBeanByClassName(Class<T> clazz) {
            char[] cs = clazz.getSimpleName().toCharArray();
            // 首字母大写转换为小写
            cs[0] += 32;
            return (T) applicationContext.getBean(String.valueOf(cs));
        }


    /**
     * 该方法用于外界通过bean的id【即类型首字母小写 <bean id="" class="xxx" />】获取Bean对象
     * @param name
     * @return
     */
    public static <T> T getBeanById(String name) {
        return (T) applicationContext.getBean(name);
    }

    /**
     * 判断Bean是否包含Bean
     * @param name
     * @return
     */
    public static boolean containsBean(String name) {
        return applicationContext.containsBean(name);
    }

    /**
     * 判断Bean是否为单例模式
     * @param name
     * @return
     * @throws NoSuchBeanDefinitionException
     */
    public static boolean isSingleton(String name) throws NoSuchBeanDefinitionException {
        return applicationContext.isSingleton(name);
    }

}