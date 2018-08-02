package cn.kanyun.cpa.model.lombok;

import lombok.Cleanup;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Lomok 综合测试
 * Created by Administrator on 2018/8/1.
 * @Data
 * 该注解使用在类上，该注解会提供getter、setter、equals、canEqual、hashCode、toString方法。
 * Lomok详解：https://blog.csdn.net/motui/article/details/79012846
 */
@Slf4j
public class LomokCompositeTest {


    /**
     * @describe: Lomok @Cleanup注解将自动关闭流,其相比try resource语法更为简洁，该注解使用在属性前，该注解是用来保证分配的资源被释放
     * 在本地变量上使用该注解，任何后续代码都将封装在try/finally中，确保当前作用于中的资源被释放。默认@Cleanup清理的方法为close
     * 可以使用value指定不同的方法名称
     * byteArrayOutputStream的write方法会抛出异常，@SneakyThrows注解使用在方法上，这个注解用在方法上，
     * 可以将方法中的代码用 try-catch 语句包裹起来，捕获异常并在 catch 中用 Lombok.sneakyThrow(e) 把异常抛出，
     * 可以使用 @SneakyThrows(Exception.class) 的形式指定抛出哪种异常。该注解需要谨慎使用。也就是说最终还是把异常给抛出了
     * @params:
     * @Author: Kanyun
     * @Date: 2018/8/1 16:54
     */
    @SneakyThrows(IOException.class)
    public void exec() {
        @Cleanup ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//        try {
        byteArrayOutputStream.write(new byte[]{'k', 'a', 'n', 'y', 'u', 'n'});
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    /**
     * @describe:  @Slf4j 注解在类上,类似的注解还有几个,只不过使用的类不一样【所以使用的时候需要看当前系统里存在哪些日志框架的jar,然后再进行选择】
     * 1.@CommonsLog 使用
     * private static final org.apache.commons.logging.Log log = org.apache.commons.logging.LogFactory.getLog(LogExample.class);
     * 2.@JBossLog 使用
     * private static final org.jboss.logging.Logger log = org.jboss.logging.Logger.getLogger(LogExample.class);
     * 3.@Log 使用
     * private static final java.util.logging.Logger log = java.util.logging.Logger.getLogger(LogExample.class.getName());
     * 4.@Log4j 使用
     * private static final org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(LogExample.class);
     * 5.@Log4j2 使用
     * private static final org.apache.logging.log4j.Logger log = org.apache.logging.log4j.LogManager.getLogger(LogExample.class);
     * 6.@Slf4j 使用
     * private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(LogExample.class);
     * 7.@XSlf4j 使用
     * private static final org.slf4j.ext.XLogger log = org.slf4j.ext.XLoggerFactory.getXLogger(LogExample.class);
     * 默认情况下，记录器的主题（或名称）将是使用注释进行@Log注释的类的类名称。这可以通过指定topic参数来定制。例如：@XSlf4j(topic="reporting")
     * @params:
     * @Author: Kanyun
     * @Date: 2018/8/1 17:12
     */
    public void exec2() {
        log.info("Lomok 注解打印日志");
    }
}
