package cn.kanyun.cpa.controller.itempool;

import cn.kanyun.cpa.model.dto.itempool.CpaRepertoryDto;
import cn.kanyun.cpa.model.entity.CpaResult;
import cn.kanyun.cpa.service.itempool.ICpaRepertoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by KANYUN on 2017/6/17.
 */
@Controller
@RequestMapping("/unitExam")
public class CpaRepertoryController {

    private static final Logger logger = LoggerFactory.getLogger(CpaRepertoryController.class);

    @Resource(name = ICpaRepertoryService.SERVICE_NAME)
    private ICpaRepertoryService cpaRepertoryService;

    /**
     *@Author: kanyun
     *@Description: 获取试题列表（单元测试）
     *@Date: 2017/8/16 14:58
     *@params:
     */
    @RequestMapping("/getUnitExam/{typeCode}")
    @ResponseBody
    public CpaResult getUnitExam(@PathVariable("typeCode") String typeCode) {
        Object[] params = {typeCode};
        String where = "o.testType=? ";
        CpaResult result = cpaRepertoryService.getUnitExam(where, params);
        return result;
    }


}
