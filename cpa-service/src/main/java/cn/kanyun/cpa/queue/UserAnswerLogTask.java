package cn.kanyun.cpa.queue;

import cn.kanyun.cpa.service.user.IAnswerRecordService;

import java.util.concurrent.BlockingQueue;

/**
 * Created by KANYUN on 2017/10/21.
 */
public class UserAnswerLogTask extends Thread {
    private BlockingQueue<IAnswerRecordService> taskQueue;
    private Boolean workSattus = false;

    public UserAnswerLogTask(BlockingQueue<IAnswerRecordService> taskQueue) {
        this.taskQueue = taskQueue;
    }

    public Integer addWork(IAnswerRecordService task) {
        if (!taskQueue.contains(task) && null != task) {
            taskQueue.add(task);
        }
        return taskQueue.size();
    }

    public void work(){

    }
}
