package cn.kanyun.cpa.controller.itempool;

import cn.kanyun.cpa.model.entity.CpaResult;
import cn.kanyun.cpa.service.itempool.ICpaRepertoryService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * Created by KANYUN on 2017/6/17.
 */
@Controller
@RequestMapping("/unitExam")
public class CpaRepertoryController {
    @Resource(name = ICpaRepertoryService.SERVICE_NAME)
    private ICpaRepertoryService cpaRepertoryService;
    @RequestMapping("/getUnitExam/{typeCode}")
    @ResponseBody
    public CpaResult getUnitExam(@PathVariable("typeCode") String typeCode) {
        Object[] params = {typeCode};
        String where = "o.testType=? ";
        CpaResult result = cpaRepertoryService.getUnitExam(where, params);
        return result;
    }
}
