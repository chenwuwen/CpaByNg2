package cn.kanyun.cpa.controller.itempool;

import cn.kanyun.cpa.model.dto.itempool.CpaRepertoryDto;
import cn.kanyun.cpa.model.entity.CpaResult;
import cn.kanyun.cpa.service.itempool.ICpaSolutionService;
import cn.kanyun.cpa.service.itempool.impl.CpaSolutionServiceImpl;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
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
@Controller
@RequestMapping("/api/solution")
public class CpaSolutionController {

    private static final Logger logger = LoggerFactory.getLogger(CpaRepertoryController.class);

    @Resource
    private ICpaSolutionService cpaSolutionService;

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
    public CpaResult correctItem(String pAnswers,String typeCode) {
        Map<Integer, String> peopleAnswer = new HashMap<>();
//        Iterator iterator = pAnswers.iterator();
//        while (iterator.hasNext()) {
//            String str = (String) iterator.next();
//            if (null != str && !"".equals(str)) {
//                String[] strr = str.split("-");
//                peopleAnswer.put(Integer.valueOf(strr[0]), strr[1]);
//            }
//        }
        CpaResult result = cpaSolutionService.compareAnswer(peopleAnswer,typeCode);
        return result;
    }

}
