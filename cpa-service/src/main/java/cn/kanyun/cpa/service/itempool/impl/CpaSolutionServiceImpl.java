package cn.kanyun.cpa.service.itempool.impl;

import cn.kanyun.cpa.dao.itempool.ICpaSolutionDao;
import cn.kanyun.cpa.dao.itempool.impl.CpaSolutionDaoImpl;
import cn.kanyun.cpa.model.dto.itempool.CpaRepertoryDto;
import cn.kanyun.cpa.model.entity.CpaResult;
import cn.kanyun.cpa.model.entity.itempool.CpaSolution;
import cn.kanyun.cpa.service.CommonServiceImpl;
import cn.kanyun.cpa.service.itempool.ICpaSolutionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Administrator on 2017/6/16.
 */
@Service(ICpaSolutionService.SERVICE_NAME)
public class CpaSolutionServiceImpl extends CommonServiceImpl<Integer, CpaSolution> implements ICpaSolutionService {

    @Resource
    private ICpaSolutionDao cpaSolutionDao;

    @Override
    public Map<Integer, String> getSolution(List<Integer> respertoryIds) {
        Map mapSolution = cpaSolutionDao.getAnswer(respertoryIds);
        Map<Integer, String> basicAnswer = new HashMap();
        Iterator iterator = mapSolution.entrySet().iterator();
        while (iterator.hasNext()){
            Map.Entry<Integer,CpaSolution> entry = (Map.Entry<Integer, CpaSolution>) iterator.next();
            basicAnswer.put(entry.getKey(),entry.getValue().getResult());
        }

        return basicAnswer;
    }

    @Override
    public CpaResult compareAnswer(Map<Integer,String> peopleAnswer,String typeCode) {
        CpaResult result = new CpaResult();
        Map<Integer,String> basicAnswer = getSolution((new ArrayList<Integer>(peopleAnswer.keySet())));
        Iterator iterator = peopleAnswer.entrySet().iterator();
        Integer score = 0;
        Map resultmap = new HashMap();
        List errorList = new ArrayList();
        while (iterator.hasNext()){
            Map.Entry<Integer,String> entry = (Map.Entry) iterator.next();
            String pav = entry.getValue() == null ? "" :  entry.getValue();
            String bav = basicAnswer.get(entry.getKey())==null ? "" : basicAnswer.get(entry.getKey());
            if (pav.equals(bav)){
                score ++ ;
            }else {
                Map map = new HashMap();
                map.put("errorItemId",entry.getKey());
                map.put("errorItemAnswer",pav);
                map.put("correctItemAnswer",bav);
                errorList.add(map);
            }
        }
        resultmap.put("typeCode",typeCode);
        resultmap.put("score",score);
        resultmap.put("errorList",errorList);
        result.setData(resultmap);
        result.setState(1);
        return result;
    }
}
