package cn.kanyun.cpa.util;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 单例线程池,可以用作全局线程池
 *
 * 线程池需要设置成单例,以便复用,否则一个线程进来就创建一个线程池,那用线程池就没有意义了
 * 该线程池如果不满足需要,则在需要的地方创建自定义的线程池，注意保持单例
 * 使用方法： Executor executor = CommonThreadPool.DEFAULT_THREAD_POOL.getThreadPoolExecutor()
 *
 * 形成单例是因为枚举本身就是十分优雅的单例实现(不能通过反射获得实例),所以可以通过得到枚举对象,再在枚举中定义
 * 私有方法,来实现线程池的单例
 *
 * 如果需要定义不同类型的线程池对象,则可以在枚举中定义一个抽象方法,那么枚举中的每个对象都必须实现这个方法
 * 如:该枚举中定义的getThreadPoolInfo()抽象方法
 */
public enum CommonThreadPool {

    DEFAULT_THREAD_POOL{
        @Override
        public String getThreadPoolInfo() {
            return "这是自定义的全局默认线程池:该线程池核心线程数为CPU核心数,最大核心数为CPU核心数的2倍,空闲线程存活时间是10秒,任务队列是大小为Integer.MAX_VALUE的有界队列";
        }
    };

    /**
     * 线程序列编号
     */
    private static final AtomicInteger threadSerialNumber = new AtomicInteger(1);
    /**
     * 全局线程池 线程名称 前缀
     */
    private final String threadNamePrefix = "DEFAULT_THREAD_POOL_";
    private ThreadPoolExecutor threadPoolExecutor;

    /**
     * 获取ThreadPoolExecutor对象的方法,ThreadPoolExecutor对象在枚举加载时就已经实例化,
     * 因为枚举加载时使用ClassLoad的loadClass方法,该方法加锁所以是线程安全的
     * 只有一个对象,所以就可以保证该ThreadPoolExecutor对象,全局只有一个
     * 线程池的单例
     * @return
     */
    public ThreadPoolExecutor getThreadPoolExecutor() {
        return this.threadPoolExecutor;
    }

    /**
     * 枚举中定义的抽象方法,该枚举中定义的对象【即实例】,都需要实现该方法,
     * 可以用来使用不同的枚举值【实例】,构造返回不同的对象等！
     * @return
     */
    public abstract String getThreadPoolInfo();

    /**
     * 自定义构造方法,用于构造threadPoolExecutor对象
     * 枚举的构造方法都是私有的
     */
    CommonThreadPool() {
//        CPU个数
        int coreSize = Runtime.getRuntime().availableProcessors();
        int maxSize = Runtime.getRuntime().availableProcessors() * 2;
//        当线程池运行线程大于核心线程数时,多出来的线程数的空闲存活时间
        int keepTime = 10;
//        存活时间单位
        TimeUnit unit = TimeUnit.SECONDS;
//        构造线程工厂,一般来说直接使用线程池的时候,可以不需要这个参数,用默认的就行
        ThreadFactory threadFactory = new ThreadFactory() {

            public Thread newThread(Runnable r) {
//                真正创建线程的地方
                Thread thread = new Thread(r, threadNamePrefix + threadSerialNumber.getAndIncrement());
//                判断是否是守护进程
                if (thread.isDaemon()) {
                    thread.setDaemon(false);
                }
//                默认是正常优先级
                if (thread.getPriority() != Thread.NORM_PRIORITY) {
                    thread.setPriority(Thread.NORM_PRIORITY);
                }
                return thread;
            }
        };

//        创建任务队列
        BlockingQueue workQueue = new LinkedBlockingQueue(Integer.MAX_VALUE);

//        构造线程池对象
        threadPoolExecutor = new ThreadPoolExecutor(coreSize, maxSize, keepTime, unit, workQueue, threadFactory);

    }



}
