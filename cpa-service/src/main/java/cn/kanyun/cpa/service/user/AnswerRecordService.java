package cn.kanyun.cpa.service.user;

import cn.kanyun.cpa.model.entity.user.AnswerRecord;
import cn.kanyun.cpa.service.CommonService;

/**
 * @author Administrator
 */
public interface AnswerRecordService extends CommonService<Long, AnswerRecord> {

     String SERVICE_NAME = "cn.kanyun.cpa.service.user.impl.AnswerRecordServiceImpl";

    /**
     * @Description: 保存用户答题记录
     * @param answerRecord
     */
    void saveUserAnswerRecord();

    void addAnswerRecord(AnswerRecord answerRecord);
}
