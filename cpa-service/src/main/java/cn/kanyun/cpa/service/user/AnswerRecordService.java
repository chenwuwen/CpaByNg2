package cn.kanyun.cpa.service.user;

import cn.kanyun.cpa.model.entity.user.AnswerRecord;
import cn.kanyun.cpa.service.CommonService;

public interface AnswerRecordService extends CommonService<Long, AnswerRecord> {
    public static final String SERVICE_NAME = "cn.kanyun.cpa.service.user.impl.AnswerRecordServiceImpl";

    /**
     * @Description: 保存用户答题记录
     * @param answerRecord
     */
    void saveUserAnswerRecord();

    void addAnswerRecord(AnswerRecord answerRecord);
}
