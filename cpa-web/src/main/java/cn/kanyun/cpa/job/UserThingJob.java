package cn.kanyun.cpa.job;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @describe: 用户相关的定时任务
 * @params:
 * @Author: Kanyun
 * @Date: 2017/12/15 0015 14:47
 */
public class UserThingJob {
    private static final Logger logger = LoggerFactory.getLogger(UserThingJob.class);

    public void testJob() {
        logger.info("=====定时任务执行=====");
    }
    /**
     * @describe: 每天两点开始保存用户答题记录
     * @params:
     * @Author: Kanyun
     * @Date: 2017/12/15 0015 15:07
     */
    public void saveUserAnswerRecord(){
        logger.info("=====定时任务执行=====");
    }
}
