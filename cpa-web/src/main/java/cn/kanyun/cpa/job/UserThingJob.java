package cn.kanyun.cpa.job;

import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.remoting.zookeeper.curator.CuratorZookeeperClient;
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

    public static String zkProtocol = "";
    public static String zkHost = "";
    public static int zkPort = 2181;

    private com.alibaba.dubbo.common.URL url = new URL(zkProtocol, zkHost, zkPort);

    private CuratorZookeeperClient curatorZookeeperClient = new CuratorZookeeperClient(url);


    public void testJob() {
        logger.info("=====定时任务执行=====");
    }

    /**
     * @describe: 测试zookeeper分布式锁, 当同一份代码在多台机器上布置时, 需要进行分布式锁, 避免多台机器同时做一件事情
     * @params:
     * @Author: Kanyun
     * @Date: 2017/12/15 0015 15:07
     */
    public void testzkDistributedlock() {
        //先检查节点是否存在,因为zookeeper是基于内存且是原子的,如果存在该路径,表明已经由机器在zookeeper设置节点了
        boolean exists = curatorZookeeperClient.checkExists("/temp");
        if (exists){
//            curatorZookeeperClient.createPersistent();
        }
    }

    /**
     * @describe: 每天两点开始保存用户答题记录
     * @params:
     * @Author: Kanyun
     * @Date: 2017/12/15 0015 15:07
     */
    public void saveUserAnswerRecord() {
        logger.info("=====定时任务执行=====");
    }
}
