package cn.kanyun.cpa.controller.itempool;

import cn.kanyun.cpa.model.dto.itempool.CpaRepertoryDto;
import cn.kanyun.cpa.model.entity.CpaConstants;
import cn.kanyun.cpa.model.entity.CpaResult;
import cn.kanyun.cpa.model.entity.user.AnswerRecord;
import cn.kanyun.cpa.model.entity.user.CpaUser;
import cn.kanyun.cpa.service.itempool.ICpaSolutionService;
import cn.kanyun.cpa.service.itempool.impl.CpaSolutionServiceImpl;
import cn.kanyun.cpa.service.user.IAnswerRecordService;
import cn.kanyun.cpa.util.WebUtil;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.*;

/**
 * Created by KANYUN on 2017/6/17.
 */
@Controller
@RequestMapping("/api/solution")
public class CpaSolutionController {

    private static final Logger logger = LoggerFactory.getLogger(CpaRepertoryController.class);

    @Resource(name = ICpaSolutionService.SERVICE_NAME)
    private ICpaSolutionService cpaSolutionService;
    @Resource(name = IAnswerRecordService.SERVICE_NAME)
    private IAnswerRecordService answerRecordService;

    /**
     * @Author: kanyun
     * @Description: 检查试题答案
     * @Date: 2017/8/16 15:03
     * @params:
     */
    @RequestMapping("/correctItem")
    @ResponseBody
/*    @RequestBody接收的是一个Json对象的字符串，而不是一个Json对象。然而在ajax请求往往传的都是Json对象，后来发现用 JSON.stringify(data)的方式就能将对象变成字符串。
    同时ajax请求的时候也要指定dataType: "json",contentType:"application/json" 这样就可以轻易的将一个对象或者List传到Java端，使用@RequestBody即可绑定对象或者List*/
    public CpaResult correctItem(@RequestBody CpaRepertoryDto cpaRepertoryDto, HttpServletRequest request) {
        CpaResult result = new CpaResult();
        try {
            CpaUser user = WebUtil.getSessionUser(request);
            Map<Integer, String[]> peopleAnswer = new HashMap<>();
            Iterator iterator = cpaRepertoryDto.getpAnswer().iterator();
            while (iterator.hasNext()) {
                String str = (String) iterator.next();
                if (null != str && !"".equals(str)) {
                    String[] strr = str.split("-");
                    Integer k = Integer.valueOf(strr[0]); //获取试题ID（数组中第一项）
                    strr[0] = strr[strr.length - 1]; //（数组中其他项为value做数组，将数组最后一个值赋给数组第一个值）
                    strr = Arrays.copyOf(strr, strr.length - 1); //数组缩容
              /*  处理数组第二种方法,需要再建立一个数组 使用System.arraycopy方法：如果是数组比较大，那么使用System.arraycopy会比较有优势，因为其使用的是内存复制，省去了大量的数组寻址访问等时间*/
//                String[] str = new String[strr.length-1];
//                System.arraycopy(strr,1,str,0,strr.length-1);
//                System.out.println(Arrays.toString(str));

                    peopleAnswer.put(k, strr);
                }
            }
            result = cpaSolutionService.compareAnswer(peopleAnswer, cpaRepertoryDto.getTypeCode());
            AnswerRecord answerRecord = this.patchAnswerRecord(result, user);
            answerRecordService.addAnswerRecord(answerRecord);
        } catch (Exception e) {
            logger.error("Error : /api/solution/correctItem " + e);
            result.setState(CpaConstants.OPERATION_ERROR);
        }
        return result;
    }

    /**
     * @Description: 拼凑AnswerRecord
     * @param result 回答记录的结果
     * @param user  回答试题用户
     * @return
     */
    private AnswerRecord patchAnswerRecord(CpaResult result,CpaUser user){
        AnswerRecord answerRecord = new AnswerRecord();
        answerRecord.setAnswerDate(new Timestamp(System.currentTimeMillis()));
        answerRecord.setCorrectcount((Integer) ((Map)result.getData()).get("correctCount"));
        answerRecord.setItemType(((Map)result.getData()).get("typeCode").toString());
        answerRecord.setErrorcount((Integer) ((Map)result.getData()).get("errorCount"));
        answerRecord.setScore((Integer) ((Map)result.getData()).get("score"));
        answerRecord.setUsername(user.getUserName());
        answerRecord.setUserId(user.getId());
        answerRecord.setPetname(user.getPetName());
        answerRecord.setTotalcount((Integer) ((Map)result.getData()).get("totalCount"));
        return answerRecord;
    }

}
