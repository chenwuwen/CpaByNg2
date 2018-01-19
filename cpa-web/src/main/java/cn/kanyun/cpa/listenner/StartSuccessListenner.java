package cn.kanyun.cpa.listenner;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.io.*;

/**
 * spring spring MVC 启动成功监听器
 * 应用场景：很多时候我们想要在某个类加载完毕时干某件事情，但是使用了spring管理对象，我们这个类引用了其他类（可能是更复杂的关联），
 * 所以当我们去使用这个类做事情时发现包空指针错误，这是因为我们这个类有可能已经初始化完成，但是引用的其他类不一定初始化完成，所以发生了空指针错误，解决方案如下：
 * 1、写一个类继承spring的ApplicationListener监听，并监控ContextRefreshedEvent事件（容易初始化完成事件）
 * 2、定义简单的bean：<bean id="beanDefineConfigue" class="com.creatar.portal.webservice.BeanDefineConfigue"></bean> 或者直接使用@Component("BeanDefineConfigue")注解方式
 * 系统启动成功后控制台打印图案,类似SpringBoot启动图案,当然也可以在启动成功之后做其他事情
 */

/**系统启动成功后控制台打印图案,类似SpringBoot启动图案,当然也可以在启动成功之后做其他事情*/

/**ContextRefreshedEvent为初始化完毕事件，spring还有很多事件可以利用*/
@Component("StartSuccessListenner")
public class StartSuccessListenner implements ApplicationListener<ContextRefreshedEvent> {

    /**
     * 当一个ApplicationContext被初始化或刷新触发
     */

    /**
     * applicationontext和使用MVC之后的webApplicationontext会两次调用上面的方法，如何区分这个两种容器呢？
     *但是这个时候，会存在一个问题，在web 项目中（spring mvc），系统会存在两个容器，一个是root application context ,
     * 另一个就是我们自己的 projectName-servlet context（作为root application context的子容器）。
     *这种情况下，就会造成onApplicationEvent方法被执行两次。为了避免上面提到的问题，
     * 我们可以只在root application context初始化完成后调用逻辑代码，其他的容器的初始化完成，则不做任何处理，修改后代码
     * @param contextRefreshedEvent
     */
    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        if (contextRefreshedEvent.getApplicationContext().getDisplayName().equals("Root WebApplicationContext")) {
            drawTip(7, 26);
            //读取文本内容,将文本内容打印出来有两种办法： 1.使用标准输出流(system.out)来做。 2.使用打印流; (PrintWriter)来做。
            //获取classpath路径
            System.out.println("classpath路径： " + this.getClass().getClassLoader().getResource("").getPath());
            String path = this.getClass().getClassLoader().getResource("").getPath();
            //获取当前类的加载路径
            System.out.println("当前类加载路径： " + this.getClass().getResource("").getPath());
            /**
             * classpath实际上就是编译后的 以 classes 文件夹为起点的路径
             * getResource 与 class.getClassLoader().getResource 两者的区别：
             * 前者获取的是当前类加载的路径，如果用此方法读取文件则有两种方法，与相对路径绝对路径非常类似
             * 后者获取的是类加载器的路径，即会到classpath路径下。可以理解当前在 classes/ 目录下，要想访问哪个文件，直接填写路径即可，
             * 不用区分相对路径和绝对路径。显然，此方法比较容易写出。推荐。
             */

//            this.readSystemOut1(path + "banner.txt");
            this.readPrint(path + "banner.txt");
//            this.readSystemOut(path + "banner.txt");
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
                put.write(len);
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

    /**
     * 系统气筒提示
     * @param n  图案行数
     * @param m  图案列数
     */
    private static void drawTip(int n, int m) {
        for (int i = 0; i < n; i++) {
            switch (i) {
                case 0:
                    for (int j = 0; j < m - 1; j++) {
                        System.out.print("*");
                    }
                    System.out.println("*");
                    break;
                case 3:
                    System.out.print("* ");
                    System.out.print("cpa startup successful");
                    System.out.println(" *");
                    break;
                case 6:
                    for (int j = 0; j < m - 1; j++) {
                        System.out.print("*");
                    }
                    System.out.println("*");
                    break;
                default:
                    for (int j = 0; j < m; j++) {
                        if (j == 0) {
                            System.out.print("*");
                        } else if (j == m - 1) {
                            System.out.println("*");
                        } else {
                            System.out.print(" ");
                        }

                    }
                    break;
            }
        }

    }


    public static void main(String[] args) {
        drawTip(7, 26);
    }
}
