package cn.kanyun.cpa.util;

import com.sun.mail.iap.CommandFailedException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class OSUtil {
    public static Properties props = System.getProperties(); //获得系统属性集

    /**
     * 操作系统名称
     *
     * @return String
     */
    public static String getOSName() {
        return OSUtil.getProperty("os.name");
    }

    /**
     * 操作系统架构
     *
     * @return String
     */
    public static String getOSArch() {
        return OSUtil.getProperty("os.arch");
    }

    /**
     * 操作系统版本
     *
     * @return String
     */
    public static String getOSVersion() {
        return OSUtil.getProperty("os.version");
    }

    /**
     * Java 运行时环境版本
     *
     * @return String
     */
    public static String getJavaVersion() {
        return OSUtil.getProperty("java.version");
    }

    /**
     * Java 运行时环境供应商
     *
     * @return String
     */
    public static String getJavaVendor() {
        return OSUtil.getProperty("java.vendor");
    }

    /**
     * Java 供应商的 URL
     *
     * @return String
     */
    public static String getJavaVendorURL() {
        return OSUtil.getProperty("java.vendor.url");
    }

    /**
     * Java 安装目录
     *
     * @return String
     */
    public static String getJavaHome() {
        return OSUtil.getProperty("java.home");
    }

    /**
     * Java 虚拟机规范版本
     *
     * @return String
     */
    public static String getJavaVmSpecificationVersion() {
        return OSUtil.getProperty("java.vm.specification.version");
    }

    /**
     * Java 虚拟机规范供应商
     *
     * @return String
     */
    public static String getJavaVmSpecificationVendor() {
        return OSUtil.getProperty("java.vm.specification.vendor");
    }

    /**
     * Java 虚拟机规范名称
     *
     * @return String
     */
    public static String getJavaVmSpecificationName() {
        return OSUtil.getProperty("java.vm.specification.name");
    }

    /**
     * Java 虚拟机实现版本
     *
     * @return String
     */
    public static String getJavaVmVersion() {
        return OSUtil.getProperty("java.vm.version");
    }

    /**
     * Java 虚拟机实现供应商
     *
     * @return String
     */
    public static String getJavaVmVendor() {
        return OSUtil.getProperty("java.vm.vendor");
    }

    /**
     * Java 虚拟机实现名称
     *
     * @return String
     */
    public static String getJavaVmName() {
        return OSUtil.getProperty("java.vm.name");
    }

    /**
     * Java 运行时环境规范版本
     *
     * @return String
     */
    public static String getJavaSpecificationVersion() {
        return OSUtil.getProperty("java.specification.version");
    }

    /**
     * Java 运行时环境规范供应商
     *
     * @return String
     */
    public static String getJavaSpecificationVendor() {
        return OSUtil.getProperty("java.specification.vendor");
    }

    /**
     * Java 运行时环境规范名称
     *
     * @return String
     */
    public static String getJavaSpecificationName() {
        return OSUtil.getProperty("java.specification.name");
    }

    /**
     * Java 类格式版本号
     *
     * @return String
     */
    public static String getJavaClassVersion() {
        return OSUtil.getProperty("java.class.version");
    }

    /**
     * Java 类路径
     *
     * @return String
     */
    public static String getJavaClassPath() {
        return OSUtil.getProperty("java.class.path");
    }

    /**
     * 加载库时搜索的路径列表
     *
     * @return String
     */
    public static String getJavaLibraryPath() {
        return OSUtil.getProperty("java.library.path");
    }

    /**
     * 默认的临时文件路径
     *
     * @return String
     */
    public static String getJavaIOTmpdir() {
        return OSUtil.getProperty("java.io.tmpdir");
    }

    /**
     * 要使用的 JIT 编译器的名称
     *
     * @return String
     */
    public static String getJavaCompiler() {
        return OSUtil.getProperty("java.compiler");
    }

    /**
     * 一个或多个扩展目录的路径
     *
     * @return String
     */
    public static String getJavaExtDirs() {
        return OSUtil.getProperty("java.ext.dirs");
    }

    /**
     * 文件分隔符（在 UNIX 系统中是“/”）
     *
     * @return String
     */
    public static String getFileSeparator() {
        return OSUtil.getProperty("file.separator");
    }

    /**
     * 路径分隔符（在 UNIX 系统中是“:”）
     *
     * @return String
     */
    public static String getPathSeparator() {
        return OSUtil.getProperty("path.separator");
    }

    /**
     * 行分隔符（在 UNIX 系统中是“:”）
     *
     * @return String
     */
    public static String getLineSeparator() {
        return OSUtil.getProperty("line.separator");
    }

    /**
     * 用户的账户名称
     *
     * @return String
     */
    public static String getUserName() {
        return OSUtil.getProperty("user.name");
    }

    /**
     * 用户的主目录
     *
     * @return String
     */
    public static String getUserHome() {
        return OSUtil.getProperty("user.home");
    }

    /**
     * 用户的当前工作目录
     *
     * @return String
     */
    public static String getUserDir() {
        return OSUtil.getProperty("user.dir");
    }

    /**
     * 获取Windows 平台CMD 窗口 编码格式 (仅适用于Windows平台)
     *
     * @return
     */
    public static String getWindowsCmdCharSet() throws IOException {
        String chatsetName = "";
        Runtime runtime = Runtime.getRuntime();
        Process process = runtime.exec("cmd.exe /c chcp");
//        由于在windows上执行上面的命令,我们只需要获取命令返回结果中包含的数字,所以,使用默认编码即可
        String commandResult = StreamUtils.copyToString(process.getInputStream(), Charset.defaultCharset());
//        去掉返回结果中的非数字字符,剩余的数字字符拼接成字符串
        String chcpResult = StringUtils.getDigits(commandResult);
        switch (chcpResult) {
            case "936":  // 简体中文（默认）
                chatsetName = "GBK";
                break;
            case "20936":
                chatsetName = "GB2312";
                break;
            default: //"65001"
                chatsetName = "UTF-8";
        }
        return chatsetName;
    }

    /**
     * 通过Java执行系统命令，与cmd中或者终端上一样执行shell命令
     * 最典型的用法就是使用Runtime.getRuntime().exec(command)或者new ProcessBuilder(cmdArray).start()
     * Runtime类是Java程序的运行时环境。不能new出一个Runtime对象，只能通过getRuntime()方法获取当前Runtime运行时对象的引用。然后可以调用Runtime的方法查看和修改Java虚拟机的状态
     * Runtime和ProcessBuilder的不同点就是，启动子进程时的命令形式不同，Runtime.getRuntime.exec()可以把命令和参数写在一个String中，用空格分开，ProcessBuilder则是构造函数的参数中，传递一个由命令和参数组成的list或数组。
     * 但是不推荐用java 1.0引入的Process，而是用java 5.0的ProcessBuilder替代
     *
     * 需要注意的是调用此方法时,需要在调用方捕获异常,且判断异常是否程序异常,还是执行命令时,操作系统抛出的异常
     *
     * @param command
     * @throws IOException
     */
    public static String execSystemCommand(String command) throws IOException, CommandFailedException, InterruptedException, ExecutionException {
//         不推荐这个方式
//        Runtime runtime = Runtime.getRuntime();
//        process = runtime.exec(String类型)
        List<String> commands = new ArrayList<>();
        ProcessBuilder pb = null;
//        获取当前操作系统版本
        String osName = System.getProperty("os.name").toLowerCase();
//        获取当前操作系统字符集
        String charsetName = System.getProperty("file.encoding").toUpperCase();
//        判断操作系统
        if ((osName.indexOf("windows") > -1)) {
//           开关/C指明后面跟随的字符串是命令，并在执行命令后关闭DOS窗口，使用cmd /?查看帮助
//           process = runtime.exec("cmd.exe /c " + command);
            commands.add("cmd.exe");
            commands.add("/c");
            commands.add(command);
            charsetName = getWindowsCmdCharSet();
        } else { // Linux,Unix
//          process = runtime.exec(command);
            commands.add(command);
        }
        pb = new ProcessBuilder(commands);
//        执行命令(异步的),该方法会创建一个子进程，用于执行shell /exe 脚本。子进程创建后会和主进程分别独立运行。 因为主进程需要等待脚本执行完成，然后对脚本返回值或输出进行处理，所以需要主进程调用Process.waitfor等待子进程完成
//        子进程执行过程就是不断的打印信息。主进程中可以通过Process.getInputStream和Process.getErrorStream获取并处理。
//        这时候子进程不断向主进程发生数据，而主进程调用Process.waitfor后已挂起。当前子进程和主进程之间的缓冲区塞满后，子进程不能继续写数据，然后也会挂起
//        这样子进程等待主进程读取数据，主进程等待子进程结束，两个进程相互等待，最终导致死锁。
        final Process process = pb.start();
        final Charset charset = Charset.forName(charsetName);

//        创建两个线程来处理成功流和错误流,避免process.waitFor()发生死锁
        ExecutorService executor = Executors.newFixedThreadPool(2);
        Future error = executor.submit(new Callable<String>() {
            @Override
            public String call() throws Exception {
//        获取错误输出流(明明是获取输出信息，为什么是InputStream呢？因为相对于main进程来说，子进程的输出就是main进程的输入，所以是InputStream,同理如果要向子进程传递参数或者输入信息，则应该用OutputStream)
//        这个地方需要注意的是,该错误不是程序的错误,而是操作系统在执行传入的命令时发生的错误,比如命令错了,或者操作系统没有安装相应的软件等等
                InputStream errorStream = process.getErrorStream();
                return StreamUtils.copyToString(errorStream, charset);
            }
        });

        Future success = executor.submit(new Callable<String>() {
            @Override
            public String call() throws Exception {
//                获取成功输出流
                InputStream stream = process.getInputStream();
                return StreamUtils.copyToString(stream, charset);
            }
        });
//        执行到该方法,程序会阻塞,直到命令完成,所以当上面执行的命令,是打开一个文件或者是打开一个GUI的窗口,那么只有等待文件关闭窗口关闭,程序才会继续向下执行,
//        该方法可能导致进程阻塞，甚至死锁 该方法返回值的含义：https://blog.csdn.net/qq_35661171/article/details/79096786
        int code = process.waitFor();
        try {
//        如果操作系统执行完命令返回了错误信息,则抛出异常
            if (code != 0) {
                throw new CommandFailedException("操作系统执行命令异常 \n" + error.get());
            }
            return success.get().toString();
        } finally {
            System.out.println("finally 会在 return 前执行");
//            杀死子进程，该Process对象表示的子进程被强制终止
            process.destroy();
//            关闭刚刚创建的线程池[并不是马上就关闭,等线程池任务都完成了才关闭,需要确保线程池中的内容不会处于永久阻塞的情况]
            executor.shutdown();
        }
    }

    private static String getProperty(String key) {
        return props.getProperty(key);
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        try {
//            String msg = execSystemCommand("net start mysql");
            String msg = execSystemCommand("ipconfig");
            System.out.println(msg);
        } catch (IOException e) {
            System.out.println("程序异常");
            e.printStackTrace();
        } catch (CommandFailedException e) {
            e.printStackTrace();
            System.out.println("操作系统执行命令异常");
        } catch (InterruptedException e) {
            e.printStackTrace();
            System.out.println("process.waitFor();方法异常");
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}