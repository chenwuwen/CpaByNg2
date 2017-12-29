package cn.kanyun.cpa.queue;

import cn.kanyun.cpa.service.user.AnswerRecordService;

import java.util.concurrent.BlockingQueue;

/**
 * Created by KANYUN on 2017/10/21.
 */
public class UserAnswerLogTask extends Thread {
    private BlockingQueue<AnswerRecordService> taskQueue;
    private Boolean workSattus = false;

    public UserAnswerLogTask(BlockingQueue<AnswerRecordService> taskQueue) {
        this.taskQueue = taskQueue;
    }

    public Integer addWork(AnswerRecordService task) {
        if (!taskQueue.contains(task) && null != task) {
            taskQueue.add(task);
        }
        return taskQueue.size();
    }

    public void work(){

    }
}
