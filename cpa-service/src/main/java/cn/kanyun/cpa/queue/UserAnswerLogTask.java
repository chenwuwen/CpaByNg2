package cn.kanyun.cpa.queue;

import cn.kanyun.cpa.model.entity.user.AnswerRecord;
import cn.kanyun.cpa.service.user.AnswerRecordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 * @author KANYUN
 * @date 2017/10/21
 * <p>
 * 当把一个任务交给线程池来处理的时候，线程池的执行原理:
 * ①首先会判断核心线程池里是否有线程可执行，有空闲线程则创建一个线程来执行任务。
 * ②当核心线程池里已经没有线程可执行的时候，此时将任务丢到任务队列中去。
 * ③如果任务队列（有界）也已经满了的话，但运行的线程数小于最大线程池的数量的时候，
 * 此时将会新建一个线程用于执行任务，但如果运行的线程数已经达到最大线程池的数量的时候，此时将无法创建线程执行任务。(此时执行拒绝策略)
 */
//@Component
public class UserAnswerLogTask {

    private static final Logger logger = LoggerFactory.getLogger(UserAnswerLogTask.class);

    @Resource(name = AnswerRecordService.SERVICE_NAME)
    private AnswerRecordService answerRecordService;

    /**
     * 设置队列最大容量为1000
     */
    private static final int maxSize = 10;
    /**
     * 设定最小阈值为80%
     */
    private static final float minThreshold = 0.8F;
    /**
     * 设定最大阈值为90%
     */
    private static final float maxThreshold = 0.9F;
    /**
     * 定义一个有界队列,如果不传入，需要传入当前队列大小，默认是Integer.MAX_VALUE
     */
    private static final BlockingQueue<AnswerRecord> taskQueue = new ArrayBlockingQueue<AnswerRecord>(maxSize);
    /**
     * 定义一个内存可见的工作状态
     */
    private static volatile Boolean workStatus = false;
    /**
     * 实例化CountDownLatch,且设置当其实例发送一个count请求时，开始消费[不再使用CountDownLatch,因为其不能复用(使用CountDownLatch需要注意的是
     * CountDownLatch是线程组之间的等待，即一个(或多个)线程等待N个线程完成某件事情之后再执行,所以我们尽量不要使用await() 方法，因为他会一直阻塞线程
     * 在countDown() 方法执行前,所以我们需要使用 await(long timeout, TimeUnit unit)设定超时时间，如果超时，将返回false,
     * 这样我们得知超时后，可以做异常处理，而await()是void类型，没有返回值，我们无法得知超时信息)]
     */
//    private static CountDownLatch countDownLatch = new CountDownLatch(1);

    /**
     * 实例化CyclicBarrier,且设置当其实例发送一个await请求[即参与一个线程]时，开始执行消费
     */
    private CyclicBarrier cyclicBarrier = new CyclicBarrier(1,()-> consumeAnswerRecord());

    /**
     * 当前队列大小(使用原子类,保证变量原子性)
     */
    private static final AtomicInteger size = new AtomicInteger(0);

    /**
     * 核心线程数 即 初始化线程数[默认情况下核心线程会一直存活，即使处于闲置状态也不会受存keepAliveTime限制。除非将allowCoreThreadTimeOut设置为true]
     */
    private static final int corePoolSize = 2;
    /**
     * 最大线程数
     */
    private static final int maxPoolSize = 10;
    /**
     * 线程存活时间
     */
    private static final long keepAliveTime = 20L;
    /**
     * 存活时间单位
     */
    private static final TimeUnit unit = TimeUnit.MINUTES;
    /**
     * 线程池内的工作队列[这里设置为有界队列]
     */
    private static BlockingQueue workQueue = new ArrayBlockingQueue(5);
    /**
     * 实例化线程池
     */
    private static ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(corePoolSize, maxPoolSize, keepAliveTime, unit, workQueue);

    /**
     * 初始化类实例为空（双重锁单例模式）
     */
    private static UserAnswerLogTask userAnswerLogTask = null;


    private UserAnswerLogTask() {
    }

    public static UserAnswerLogTask getInstance() {
        if (null == userAnswerLogTask) {
            synchronized (UserAnswerLogTask.class) {
                if (null == userAnswerLogTask) {
                    userAnswerLogTask = new UserAnswerLogTask();
                }
            }
        }
        return userAnswerLogTask;
    }

    /**
     * @describe: 队列添加数据
     * @params:
     * @Author: Kanyun
     * @Date: 2018/6/29 0029 13:53
     */
    public void putAnswerRecord(AnswerRecord answerRecord) {
        taskQueue.add(answerRecord);
        size.incrementAndGet(); // size自加
        if (size.intValue() / maxSize >= maxThreshold) {
//            不使用CountDownLatch作为同步的工具,其不能复用
//            countDownLatch.countDown();
            try {
                cyclicBarrier.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
            logger.info("当前时间：{}，线程：{}，插入一条答题记录，目前队列中存在 {} 条数据，通知消费线程执行", LocalDateTime.now(), Thread.currentThread().getName(), size.get());
        }
        logger.info("当前时间：{}，线程：{}，插入一条答题记录，目前队列中存在 {} 条数据！", LocalDateTime.now(), Thread.currentThread().getName(), size.get());
    }

    /**
     * @describe: 消费数据
     * @params:
     * @Author: Kanyun
     * @Date: 2018/6/29 0029 13:56
     */
    public void consumeAnswerRecord() {
        try {
            logger.info("当前时间：{}，线程：{}，插入一条答题记录，目前队列中存在{}条数据！消费线程被阻塞", LocalDateTime.now(), Thread.currentThread().getName(), size.get());
//           不使用CountDownLatch作为同步的工具,同时注意await()方法有两个实现,需要根据具体业务来选择使用
//            countDownLatch.await(1L, TimeUnit.SECONDS);
            List<AnswerRecord> answerRecords = new ArrayList<>();
            while (true) {
                answerRecords.add(taskQueue.poll());
                size.decrementAndGet(); //size自减
                if (size.intValue() / maxSize < minThreshold) {
                    break;
                }
            }
            logger.info("当前时间：{}，线程：{}，插入一条答题记录，目前队列中存在 {} 条数据！消费线程开始消费", LocalDateTime.now(), Thread.currentThread().getName(), size.get());
            answerRecordService.saveAll(answerRecords);
//            消费完成后重置CyclicBarrier
            cyclicBarrier.reset();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @describe: 当有提交答案操作时, 一个线程将答题记录插入队列，同时另一个线程去判断是否执行消费
     * @params:
     * @Author: Kanyun
     * @Date: 2018/7/13 15:51
     */
    public static void execute(AnswerRecord answerRecord) {
        userAnswerLogTask = getInstance();
        logger.info("判断是否是单例模式,当前执行线程：{} , 当前类实例：{}", Thread.currentThread().getName(), userAnswerLogTask.toString());
        logger.info("执行任务前,当前执行线程：{} , 空闲线程数：{}", Thread.currentThread().getName(), threadPoolExecutor.getActiveCount());
        threadPoolExecutor.execute(() -> userAnswerLogTask.putAnswerRecord(answerRecord));
        threadPoolExecutor.execute(() -> userAnswerLogTask.consumeAnswerRecord());
        logger.info("执行任务后,当前执行线程：{} , 空闲线程数：{}", Thread.currentThread().getName(), threadPoolExecutor.getActiveCount());
        logger.info("已有答题记录数：{} ,当前执行线程： {}", taskQueue.size(), Thread.currentThread().getName());
    }
}
