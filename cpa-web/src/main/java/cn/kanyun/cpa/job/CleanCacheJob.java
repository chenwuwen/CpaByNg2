package cn.kanyun.cpa.job;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

/**
 * @describe: 定时清除系统缓存(如 : 用户上传的图片)
 * @params:
 * @Author: Kanyun
 * @Date: 2018/2/1 0001 17:18
 */
public class CleanCacheJob {
    private static final Logger logger = LoggerFactory.getLogger(UserThingJob.class);

    public void cleanImg() {
        logger.info("======清除图片定时任务======");
        String path = CleanCacheJob.class.getClassLoader().getResource("").getPath() + "/images";
        try {
            FileUtils.cleanDirectory(new File(path));
        } catch (IOException e) {
            logger.error("定时清除图片任务异常：", e);
            e.printStackTrace();
        }
    }
}
