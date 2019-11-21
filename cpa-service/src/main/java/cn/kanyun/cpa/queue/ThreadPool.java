package cn.kanyun.cpa.queue;

import java.util.List;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * 自定义线程池
 * 正确停止线程总结起来是以下三点：
 * 1. 使用violate boolean 变量来表示线程是否停止；
 * 2. 停止线程时，需要调用停止线程的interrupt()方法，因为线程有可能在wait()或者sleep()，提高停止线程的及时性；
 * 3. 对于blocking IO的处理，尽量使用interruptibleChannel来代替 blocking IO。
 *
 * @author KANYUN
 * @date 2018/1/21
 */
public final class ThreadPool {
    /**
     * 线程池中默认线程的个数为5
     * 相当于Java内置的线程池中的 核心线程数
     */
    private static int worker_num = 5;
    /**
     * 工作线程
     */
    private WorkThread[] workThreads;
    /**
     * 已完成的任务
     */
    private static volatile int finished_task = 0;
    /**
     * 任务队列，作为一个缓冲
     */
    private Queue<Runnable> taskQueue = new LinkedBlockingDeque();
    /**
     * 线程池
     */
    private static ThreadPool threadPool;

    /**
     * 私有构造方法
     * 单例模式
     */
    private ThreadPool() {
    }

    /**
     * 创建线程池，worker_num为线程池中工作线程的个数
     *
     * @param worker_num
     */
    private ThreadPool(int worker_num) {
        ThreadPool.worker_num = worker_num;
        workThreads = new WorkThread[worker_num];
        for (int i = 0; i < worker_num; i++) {
            workThreads[i] = new WorkThread();
            Thread thread = new Thread(workThreads[i]);
//			开启线程池中的线程；
//            workThreads[i].start();
            thread.start();
        }
    }

    /**
     * 单例模式，获得一个默认线程个数的线程池
     *
     * @return
     */
    public static ThreadPool getThreadPool() {
        return getThreadPool(ThreadPool.worker_num);
    }


    /**
     * 单态模式，获得一个指定线程个数的线程池，worker_num(>0)为线程池中的工作线程的个数
     * worker_num<=0创建默认的工作线程个数；
     *
     * @param worker_num1
     * @return
     */
    static ThreadPool getThreadPool(int worker_num1) {
        if (worker_num1 <= 0) {
            worker_num1 = ThreadPool.worker_num;
        }
        if (threadPool == null) {
            threadPool = new ThreadPool(worker_num1);
        }
        return threadPool;
    }

    /**
     * 执行任务，其实只是把任务加入任务队列，什么时候执行有线程池管理器决定
     *
     * @param task
     */
    public void execute(Runnable task) {
        synchronized (taskQueue) {
            taskQueue.add(task);
            taskQueue.notify();
        }
    }

    /**
     * 批量执行任务，其实只是把任务加入任务队列，什么时候执行有线程池决定
     *
     * @param task
     */
    public void execute(Runnable[] task) {
        synchronized (taskQueue) {
            for (Runnable t : task) {
                taskQueue.add(t);
            }
            taskQueue.notify();
        }
    }

    public void execute(List<Runnable> task) {
        synchronized (taskQueue) {
            for (Runnable t : task) {
                taskQueue.add(t);
            }
            taskQueue.notify();
        }
    }

    /**
     * 销毁线程池，该方法保证在所有任务完成的情况下才销毁所有线程，否则等待任务完成才销毁
     */
    public void destroy() {
        //如果还有任务没执行完成，就先睡会
        while (!taskQueue.isEmpty()) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
//		 工作线程停止工作，且置为null;
        for (int i = 0; i < worker_num; i++) {
//            workThreads[i].stop();
//            workThreads[i]=null;
            Thread thread = new Thread(workThreads[i]);
            thread.interrupt();
            thread = null;
        }
        threadPool = null;
        //清空任务队列
        taskQueue.clear();
    }

    /**
     * 返回工作线程的个数
     *
     * @return
     */
    public int getWorkThreadNumber() {
        return worker_num;
    }

    /**
     * 返回已完成任务的个数，这里的已完成是只出了任务队列的任务个数，可能该任务并没有实际执行完成
     *
     * @return
     */
    public int getFinishedTasknumber() {
        return finished_task;
    }

    /**
     * 返回任务队列的长度，即还没处理的任务个数
     *
     * @return
     */
    public int getWaitTaskNumber() {
        return taskQueue.size();
    }

    /**
     * 覆盖toString()方法，返回线程池信息，工作线程个数和已完成任务个数
     *
     * @return
     */
    @Override
    public String toString() {
        return "WorkThread number: " + worker_num + "  finished task number: "
                + finished_task + " wait task number: " + getWaitTaskNumber();
    }

    /**
     * 内部类，工作线程 工作线程被定义为私有内部类
     */
    class WorkThread implements Runnable {

        //该工作线程 是否有效，用于结束该工作线程
        private boolean isRunning = true;

        @Override
        public void run() {
            Runnable r = null;
            //该线程无效则自然结束run方法，该线程就没用了
            while (isRunning) {
                synchronized (taskQueue) {
                    //队列为空
                    while (isRunning && taskQueue.isEmpty()) {
                        try {
                            taskQueue.wait(20);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    if (!taskQueue.isEmpty()) {
                        //取出任务 queue的poll方法,获取队首的元素，并从队列中移除,如果获取不到元素返回null
                        r = taskQueue.poll();
                    }
                    if (r != null) {
                        //执行任务
                        r.run();
                    }
                    finished_task++;
                    r = null;
                }
            }
        }
    }
}
