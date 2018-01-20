package cn.kanyun.cpa.listenner;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;


/**
 * 监听Spring关闭,打印图案
 * Created by KANYUN on 2018/1/20.
 */
@Component("StopApplicationListenner")
public class StopApplicationListenner implements ApplicationListener<ContextClosedEvent> {


    /**
     * ContextRefreshedEvent：ApplicationContext容器初始化或者刷新时触发该事件。
     * ContextStartedEvent：当使用ConfigurableApplicationContext接口的start()方法启动ApplicationContext容器时触发该事件。
     * ContextClosedEvent：当使用ConfigurableApplicationContext接口的close()方法关闭ApplicationContext容器时触发该事件。
     * ContextStopedEvent: 当使用ConfigurableApplicationContext接口的stop()方法停止ApplicationContext容器时触发该事件。
     */

    @Override
    public void onApplicationEvent(ContextClosedEvent contextClosedEvent) {
        if (contextClosedEvent.getApplicationContext().getParent() == null) {
            String path = this.getClass().getClassLoader().getResource("").getPath();
            this.readSystemOut1(path + "stopBanner.txt");
//            this.readPrint(path + "stopBanner.txt");
//            this.readSystemOut(path + "stopBanner.txt");
        }
    }

    /**
     * 使用标准输出流(system.out)来做。
     */
    private void readSystemOut1(String path) {
        BufferedReader br = null;
        OutputStreamWriter put = null;
        try {
            br = new BufferedReader(new FileReader(path));
            put = new OutputStreamWriter(System.out);
            String len;
            while ((len = br.readLine()) != null) {
                put.write(len + "\r\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null || put != null) {
                try {
                    br.close(); // 关闭流
                    put.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    /**
     * 使用标准输出流(system.out)来做。
     * 与上面方法的区别是：此方法使用了JDK1.7的 新特性try-with-resources语句,避免了上述方法中的finally块中再次进行try catch
     * try-with-resources的特性就是，在try( ...)里声明的资源，会在try-catch代码块结束后自动关闭掉。
     * 1. 凡是实现了AutoCloseable接口的类，在try()里声明该类实例的时候，在try结束后，close方法都会被调用
     * 2. try结束后自动调用的close方法，这个动作会早于finally里调用的方法。
     * 3. 不管是否出现异常（int i=1/0会抛出异常），try()里的实例都会被调用close方法
     * 4. 越晚声明的对象，会越早被close掉。
     */

    /**
     * 使用try-with-resources需要注意的是,将流定义在try的声明的参数里,多个参数,用分号间隔,流定义在其他位置,
     * 将会导致不能输出流,如下面的注释！
     *
     * @param path
     */
    private void readSystemOut(String path) {
        try (BufferedReader br = new BufferedReader(new FileReader(path)); OutputStreamWriter put = new OutputStreamWriter(System.out)) {
//            OutputStreamWriter put = new OutputStreamWriter(System.out);
            String len;
            while ((len = br.readLine()) != null) {
                put.write(len + "\r\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 使用打印流; (PrintWriter)来做。
     */
    private void readPrint(String path) {
//        PrintWriter pw = new PrintWriter(System.out);
        try (BufferedReader br = new BufferedReader(new FileReader(path)); PrintWriter pw = new PrintWriter(System.out)) {
//            PrintWriter pw = new PrintWriter(System.out);
            String len;
            while ((len = br.readLine()) != null) {
                pw.println(len);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
