package cn.kanyun.cpa.service.user.impl;

import cn.kanyun.cpa.dao.user.IAnswerRecordDao;
import cn.kanyun.cpa.model.entity.user.AnswerRecord;
import cn.kanyun.cpa.service.CommonServiceImpl;
import cn.kanyun.cpa.service.user.IAnswerRecordService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.LinkedBlockingQueue;

@Service(IAnswerRecordService.SERVICE_NAME)
public class AnswerRecordServiceImpl extends CommonServiceImpl<Integer, AnswerRecord> implements IAnswerRecordService {

    private static final LinkedBlockingQueue<AnswerRecord> answerRecords = new LinkedBlockingQueue<AnswerRecord>();

    @Resource
    private IAnswerRecordDao answerRecordDao;
    @Override
    public void saveUserAnswerRecord() {

        answerRecordDao.saveAll(this.answerRecords);
    }

    @Override
    public void addAnswerRecord(AnswerRecord answerRecord) {
        answerRecords.offer(answerRecord);
    }
}
