package cn.kanyun.cpa.service.itempool.impl;

import cn.kanyun.cpa.dao.itempool.CpaSolutionDao;
import cn.kanyun.cpa.model.entity.CpaResult;
import cn.kanyun.cpa.model.entity.itempool.CpaSolution;
import cn.kanyun.cpa.redis.service.RedisService;
import cn.kanyun.cpa.service.CommonServiceImpl;
import cn.kanyun.cpa.service.itempool.CpaSolutionService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 *
 * @author Kanyun
 * @date 2017/6/16
 */
@Service(CpaSolutionService.SERVICE_NAME)
public class CpaSolutionServiceImpl extends CommonServiceImpl<Long, CpaSolution> implements CpaSolutionService {

    private static final Logger logger = org.slf4j.LoggerFactory.getLogger(CpaSolutionServiceImpl.class);

    @Resource
    private CpaSolutionDao cpaSolutionDao;
    @Resource
    private RedisService redisService;

    @Override
    public Map<Integer, String[]> getSolution(List<Long> questionIds, String typeCode) {
        Map mapSolution = null;
        String key = typeCode + StringUtils.join(",", questionIds.toArray());
        try {
            mapSolution = redisService.getCacheMap(key);
        } catch (Exception e) {
            logger.error("Error: 从Redis获取数据出错：{}", e);
        }
        if (null == mapSolution) {
            mapSolution = cpaSolutionDao.getAnswer(questionIds);
            try{
                redisService.setCacheMap(key, mapSolution);
            }catch (Exception e) {
                logger.error("Error: Redis保存数据出错：{}", e);
            }
        }
        Map<Integer, String[]> basicAnswer = new HashMap();
        Iterator iterator = mapSolution.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<Integer, CpaSolution> entry = (Map.Entry<Integer, CpaSolution>) iterator.next();
            basicAnswer.put(entry.getKey(), entry.getValue().getResult().split(","));
        }

        return basicAnswer;
    }

    @Override
    public CpaResult compareAnswer(Map<Long, String[]> peopleAnswer, String typeCode) {
        CpaResult result = new CpaResult();
        Map<Integer, String[]> basicAnswer = getSolution((new ArrayList(peopleAnswer.keySet())), typeCode);
        Iterator iterator = peopleAnswer.entrySet().iterator();
        Integer score = 0;
        Map resultMap = new HashMap();
        List errorList = new ArrayList();
        while (iterator.hasNext()) {
            Map.Entry<Integer, String[]> entry = (Map.Entry) iterator.next();
//            用户答案数组
            String pav[] = entry.getValue() == null || entry.getValue().length < 1 ? new String[0] : entry.getValue();
//            标准答案数组
            String bav[] = basicAnswer.get(entry.getKey()) == null || basicAnswer.get(entry.getKey()).length < 1 ? new String[0] : basicAnswer.get(entry.getKey());
            Arrays.sort(pav);
            Arrays.sort(bav);
            if (Arrays.equals(pav, bav)) {
                score++;
            } else {
                Map map = new HashMap();
                map.put("errorItemId", entry.getKey());
                map.put("errorItemAnswer", pav);
                map.put("correctItemAnswer", bav);
                errorList.add(map);
            }
        }
        resultMap.put("typeCode", typeCode);
        resultMap.put("score", score);
        resultMap.put("errorList", errorList);
        resultMap.put("errorCount", errorList.size());
        resultMap.put("totalCount", peopleAnswer.size());
        resultMap.put("correctCount", peopleAnswer.size() - errorList.size());
        result.setData(resultMap);
        result.setState(1);
        return result;
    }


}
