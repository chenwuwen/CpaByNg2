package cn.kanyun.cpa.controller.itempool;

import cn.kanyun.cpa.model.constants.CpaConstants;
import cn.kanyun.cpa.model.dto.itempool.CpaRepertoryDto;
import cn.kanyun.cpa.model.entity.CpaResult;
import cn.kanyun.cpa.model.entity.user.AnswerRecord;
import cn.kanyun.cpa.model.entity.user.CpaUser;
import cn.kanyun.cpa.model.enums.ExamEnum;
import cn.kanyun.cpa.queue.UserAnswerLogTask;
import cn.kanyun.cpa.service.itempool.CpaSolutionService;
import cn.kanyun.cpa.service.user.AnswerRecordService;
import cn.kanyun.cpa.service.user.UserService;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.testng.annotations.Test;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/7/17.
 * @RunWith 这个注解，这个注解是Junit4的注解就是一个运行器
 * 有以下几种选择
 * @RunWith(JUnit4.class) 就是指用JUnit4来运行
 * @RunWith(SpringJUnit4ClassRunner.class), 让测试运行于Spring测试环境
 * @RunWith(Suite.class) 的话就是一套测试集合，
 * @RunWith(SpringRunner.class)
 * 这个注解是首要而且是必须有的，至于选择RunWith那种套件，由自己选择 其中SpringRunner这个类是SpringJunit4ClassRunner 的一个子类，可以理解为简称
 */
@RunWith(SpringJUnit4ClassRunner.class)  //必须要写，相当于提供了spring的环境
@WebAppConfiguration("src/test/resources")
@ContextConfiguration(locations = "classpath:*.xml")
public class CpaSolutionControllerTest {

    private static final Logger logger = LoggerFactory.getLogger(CpaRepertoryController.class);

    @Resource(name = CpaSolutionService.SERVICE_NAME)
    private CpaSolutionService cpaSolutionService;
    @Resource(name = AnswerRecordService.SERVICE_NAME)
    private AnswerRecordService answerRecordService;
    @Resource(name = UserService.SERVICE_NAME)
    private UserService userService;

    @Test
    public void testCorrectItem() throws Exception {
        CpaResult result = new CpaResult();
        try {
            CpaUser user = userService.findById(2L);
            CpaRepertoryDto cpaRepertoryDto = new CpaRepertoryDto();
            cpaRepertoryDto.setTestType(String.valueOf(ExamEnum.CPUACCOUNT));
            List<String> pAnswer = new ArrayList<>();
            pAnswer.add("20-C");
            pAnswer.add("19-C");
            cpaRepertoryDto.setpAnswer(pAnswer);
            Map<Long, String[]> peopleAnswer = new HashMap<>();
            Iterator iterator = cpaRepertoryDto.getpAnswer().iterator();
            while (iterator.hasNext()) {
                String str = (String) iterator.next();
                if (null != str && !"".equals(str)) {
                    String[] strr = str.split("-");
//                    获取试题ID(数组中第一项)
                    long k = Integer.valueOf(strr[0]);
//                    数组中其他项为value做数组，将数组最后一个值赋给数组第一个值
                    strr[0] = strr[strr.length - 1];
//                    数组缩容
                    strr = Arrays.copyOf(strr, strr.length - 1);
//                处理数组第二种方法,需要再建立一个数组 使用System.arraycopy方法：如果是数组比较大，那么使用System.arraycopy会比较有优势，因为其使用的是内存复制，省去了大量的数组寻址访问等时间
//                String[] str = new String[strr.length-1];
//                System.arraycopy(strr,1,str,0,strr.length-1);
//                System.out.println(Arrays.toString(str));

                    peopleAnswer.put(k, strr);
                }
            }
            result = cpaSolutionService.compareAnswer(peopleAnswer, cpaRepertoryDto.getTestType());
            AnswerRecord answerRecord = this.patchAnswerRecord(result, user);
//            TODO 添加测试记录,这个地方考虑使用任务队列,将做题记录保存在队列里,然后定时执行此队列
            UserAnswerLogTask.execute(answerRecord);
        } catch (Exception e) {
            logger.error("Error : /api/solution/correctItem " + e);
            result.setState(CpaConstants.OPERATION_ERROR);
        }
        System.out.println(result);

    }

    /**
     * @param result 回答记录的结果
     * @param user   回答试题用户
     * @return
     * @Description: 拼凑AnswerRecord
     */
    private AnswerRecord patchAnswerRecord(CpaResult result, CpaUser user) {
        AnswerRecord answerRecord = new AnswerRecord();
        answerRecord.setAnswerDate(LocalDateTime.now());
        answerRecord.setCorrectcount((Integer) ((Map) result.getData()).get("correctCount"));
        answerRecord.setItemType(((Map) result.getData()).get("typeCode").toString());
        answerRecord.setErrorcount((Integer) ((Map) result.getData()).get("errorCount"));
        answerRecord.setScore((Integer) ((Map) result.getData()).get("score"));
        answerRecord.setUsername(user.getUserName());
        answerRecord.setUserId(user.getId());
        answerRecord.setNickName(user.getNickName());
        answerRecord.setTotalcount((Integer) ((Map) result.getData()).get("totalCount"));
        return answerRecord;
    }
}