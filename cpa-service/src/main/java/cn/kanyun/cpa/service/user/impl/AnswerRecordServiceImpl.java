package cn.kanyun.cpa.service.user.impl;

import cn.kanyun.cpa.dao.HibernateSessionFactory;
import cn.kanyun.cpa.dao.user.IAnswerRecordDao;
import cn.kanyun.cpa.model.entity.CpaResult;
import cn.kanyun.cpa.model.entity.user.AnswerRecord;
import cn.kanyun.cpa.model.entity.user.CpaUser;
import cn.kanyun.cpa.service.CommonServiceImpl;
import cn.kanyun.cpa.service.user.IAnswerRecordService;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.Map;

@Service(IAnswerRecordService.SERVICE_NAME)
public class AnswerRecordServiceImpl extends CommonServiceImpl<Integer, AnswerRecord> implements IAnswerRecordService {
    @Resource
    private IAnswerRecordDao answerRecordDao;
    @Override
    public void saveUserAnswerRecord(CpaResult result, CpaUser user) {
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
        answerRecordDao.save(answerRecord);
    }
}
