package cn.kanyun.cpa.service.user.impl;

import cn.kanyun.cpa.dao.user.AnswerRecordDao;
import cn.kanyun.cpa.model.entity.user.AnswerRecord;
import cn.kanyun.cpa.service.CommonServiceImpl;
import cn.kanyun.cpa.service.user.AnswerRecordService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.LinkedBlockingQueue;

@Service(AnswerRecordService.SERVICE_NAME)
public class AnswerRecordServiceImpl extends CommonServiceImpl<Long, AnswerRecord> implements AnswerRecordService {

    private static final LinkedBlockingQueue<AnswerRecord> answerRecords = new LinkedBlockingQueue<AnswerRecord>();

    @Resource(name = AnswerRecordDao.REPOSITORY_NAME)
    private AnswerRecordDao answerRecordDao;
    @Override
    public void saveUserAnswerRecord() {

        answerRecordDao.saveAll(this.answerRecords);
    }

    @Override
    public void addAnswerRecord(AnswerRecord answerRecord) {
        answerRecords.offer(answerRecord);
    }
}
