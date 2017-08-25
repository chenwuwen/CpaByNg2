package cn.kanyun.cpa.controller.itempool;

import cn.kanyun.cpa.model.dto.itempool.CpaRepertoryDto;
import cn.kanyun.cpa.model.entity.CpaResult;
import cn.kanyun.cpa.service.itempool.ICpaSolutionService;
import cn.kanyun.cpa.service.itempool.impl.CpaSolutionServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by KANYUN on 2017/6/17.
 */
@Controller("/solution")
public class CpaSolutionController {

    private static final Logger logger = LoggerFactory.getLogger(CpaRepertoryController.class);

    @Resource
    private ICpaSolutionService cpaSolutionService;
    /**
     *@Author: kanyun
     *@Description: 检查试题答案
     *@Date: 2017/8/16 15:03
     *@params:
     */
    @RequestMapping("/correctItem")
    @ResponseBody
    public CpaResult correctItem(List<Map<Integer,String>> list){
        Map<Integer,String> peopleAnswer = new HashMap<>();
        Iterator iterator = list.iterator();
        while (iterator.hasNext()){
            Map.Entry<Integer,String> entry = (Map.Entry) iterator.next();
            peopleAnswer.put(entry.getKey(),entry.getValue());
        }
        CpaResult result = cpaSolutionService.compareAnswer(peopleAnswer);
        return result;
    }
}
