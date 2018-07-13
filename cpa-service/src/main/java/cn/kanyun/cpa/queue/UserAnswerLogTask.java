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
 * Created by KANYUN on 2017/10/21.
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
     * 实例化CountDownLatch,且设置当其实例发送连个count请求时，开始消费
     */
    private static CountDownLatch countDownLatch = new CountDownLatch(1);

    /**
     * 当前队列大小(使用原子类,保证变量原子性)
     */
    private static final AtomicInteger size = new AtomicInteger(0);

    /**
     * 核心线程数 即 初始化线程数
     */
    private static final int corePoolSize = 2;
    /**
     * 最大线程数
     */
    private static final int maximumPoolSize = 2;
    /**
     * 线程存活时间
     */
    private static final long keepAliveTime = 20L;
    /**
     * 存活时间单位
     */
    private static final TimeUnit unit = TimeUnit.SECONDS;
    /**
     * 线程池内的工作队列[这里设置为无界队列]
     */
    private static BlockingQueue workQueue = new LinkedBlockingQueue();
    /**
     * 实例化线程池
     */
    private static ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);

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
            countDownLatch.countDown();
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
            countDownLatch.await();
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
        } catch (InterruptedException e) {
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
        threadPoolExecutor.execute(() -> userAnswerLogTask.putAnswerRecord(answerRecord));
        threadPoolExecutor.execute(() -> userAnswerLogTask.consumeAnswerRecord());
    }
}
