package cn.kanyun.cpa.job;

import cn.kanyun.cpa.util.IpMacUtil;
import com.alibaba.druid.support.http.util.IPAddress;
import com.alibaba.dubbo.common.URL;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.framework.recipes.locks.InterProcessSemaphoreMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;


/**
 * @describe: 用户相关的定时任务
 * @params:
 * @Author: Kanyun
 * @Date: 2017/12/15 0015 14:47
 */
public class UserThingJob {
    private static final Logger logger = LoggerFactory.getLogger(UserThingJob.class);
    /**
     * zookeeper地址[加端口号]，多个地址以逗号分隔
     */
    private static String zkAddr = "";

    /**
     * 超时时间
     */
    private static Integer timeout = 100;

    /**
     * 锁路径,该类的操作都是基于该目录进行的
     */
    private static String lockPath = "/temp";


    /**
     * 重试策略：初试时间为1s 重试10次
     */
    private static RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 10);

    private static CuratorFramework curatorFramework;

    /**
     * 通过工厂建立连接
     */
    static {
        curatorFramework = CuratorFrameworkFactory.builder().connectString(zkAddr).sessionTimeoutMs(timeout).retryPolicy(retryPolicy).build();
//        开启连接
        curatorFramework.start();
        try {
//            判断锁节点是否存在
            Stat stat = curatorFramework.checkExists().forPath(lockPath);
//            建立锁路径节点,指定节点类型（不加withMode默认为持久类型节点）、路径
            curatorFramework.create().creatingParentContainersIfNeeded().withMode(CreateMode.PERSISTENT).forPath(lockPath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void testJob() {
        logger.info("=====定时任务执行=====");
    }

    /**
     * @describe: 测试zookeeper分布式锁, 当同一份代码在多台机器上布置时, 需要进行分布式锁, 避免多台机器同时做一件事情
     * @params:
     * @Author: Kanyun
     * @Date: 2017/12/15 0015 15:07
     */

    /**
     * ZooKeeper是一个为分布式应用提供一致性服务的开源组件，它内部是一个分层的文件系统目录树结构，
     * 规定同一个目录下只能有一个唯一文件名。基于ZooKeeper实现分布式锁的步骤如下：
     * （1）创建一个目录mylock；
     * （2）线程A想获取锁就在mylock目录下创建临时顺序节点；
     * （3）获取mylock目录下所有的子节点，然后获取比自己小的兄弟节点，如果不存在，则说明当前线程顺序号最小，获得锁；
     * （4）线程B获取所有节点，判断自己不是最小节点，设置监听比自己次小的节点；
     * （5）线程A处理完，删除自己的节点，线程B监听到变更事件，判断自己是不是最小的节点，如果是则获得锁。
     * Apache的开源库Curator，它是一个ZooKeeper客户端，Curator提供的InterProcessMutex是分布式锁的实现，acquire方法用于获取锁，release方法用于释放锁。
     * <p>
     * 优点：无单点问题。[ZK是集群部署的，只要集群中有半数以上的机器存活，就可以对外提供服务,]
     * 具备高可用、可重入[客户端在创建节点的时候，把当前客户端的主机信息和线程信息直接写入到节点中，下次想要获取锁的时
     * 候和当前最小的节点中的数据比对一下就可以了。如果和自己的信息一样，那么自己直接获取到锁，如果不一样就再创建一个临时的顺序节点，
     * 参与排队。]、阻塞锁[使用Zookeeper可以实现阻塞的锁，客户端可以通过在ZK中创建顺序节点，并且在节点上绑定监听器
     * 一旦节点有变化，Zookeeper会通知客户端，客户端可以检查自己创建的节点是不是当前所有节点中序号最小的
     * 如果是，那么自己就获取到锁，便可以执行业务逻辑了] 特性，可解决失效死锁问题。
     * <p>
     * 缺点：因为需要频繁的创建和删除节点(因为每次在创建锁和释放锁的过程中，都要动态创建、销毁瞬时节点来实现锁功能。
     * ZK中创建和删除节点只能通过Leader服务器来执行，然后将数据同不到所有的Follower机器上)，性能上不如Redis方式。
     * 这种做法可能引发羊群效应，从而降低锁的性能
     */
    public void testzkDistributedlock() {

//      创建锁，为可重入锁，即是获锁后，还可以再次获取
        final InterProcessMutex lock = new InterProcessMutex(curatorFramework, lockPath);
//        创建锁，为不可重入锁，即是获锁后，不可以再次获取，这里不作例子，使用和重入锁类似
//        final InterProcessSemaphoreMutex lock = new InterProcessSemaphoreMutex(curatorFramework, lockPath);
        try {
//           获取锁
            lock.acquire();
//            测试是否可以重入,超时获取锁对象(第一个参数为时间,第二个参数为时间单位),因为锁已经被获取,所以返回 false
//            Assert.assertFalse(lock.acquire(2, TimeUnit.SECONDS));
//            todo 执行相应业务代码
            logger.info("IP为：{} 的节点成功执行了代码,时间：{}", IpMacUtil.getInternetIp(), LocalDateTime.now());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
//                解锁
                lock.release();
            } catch (Exception e) {
                e.printStackTrace();
            }
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
