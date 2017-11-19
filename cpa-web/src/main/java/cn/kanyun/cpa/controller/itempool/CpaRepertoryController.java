package cn.kanyun.cpa.controller.itempool;

import cn.kanyun.cpa.model.dto.itempool.CpaRepertoryDto;
import cn.kanyun.cpa.model.dto.itempool.ItemForm;
import cn.kanyun.cpa.model.entity.CpaConstants;
import cn.kanyun.cpa.model.entity.CpaResult;
import cn.kanyun.cpa.model.entity.Page;
import cn.kanyun.cpa.model.entity.itempool.CpaOption;
import cn.kanyun.cpa.model.entity.itempool.CpaRepertory;
import cn.kanyun.cpa.model.entity.itempool.CpaSolution;
import cn.kanyun.cpa.service.itempool.ICpaRepertoryService;
import cn.kanyun.cpa.util.WordUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by KANYUN on 2017/6/17.
 */

/**
 * 在方法级@RequestMapping的注解增加了一个RequestMothod.GET，意思是只有以GET方式提交的"/fileupload"URL请求才会进入fileUploadForm()
 * 方法，其实根据HTTP协议，HTTP支持一系列提交方法（GET，POST，PUT，DELETE），同一个URL都可以使用这几种提交方式
 * 事实上SpringMVC正是通过将同一个URL的不同提交方法对应到不同的方法上达到RESTful
 */
@Controller
@RequestMapping("/api/unitExam")
public class CpaRepertoryController {

    private static final Logger logger = LoggerFactory.getLogger(CpaRepertoryController.class);

    @Resource(name = ICpaRepertoryService.SERVICE_NAME)
    private ICpaRepertoryService cpaRepertoryService;

    /**
     * @Author: kanyun
     * @Description: 获取试题列表（单元测试）
     * @Date: 2017/8/16 14:58
     * @params:
     */
    @RequestMapping("/getUnitExam/{typeCode}")
    @ResponseBody
    public CpaResult getUnitExam(@PathVariable("typeCode") String typeCode, Integer pageNo, Integer pageSize) {
        Object[] params = {typeCode};
        String where = "o.testType=? ";
        Page page = new Page();
        pageNo = pageNo == null || pageNo == 0 ? page.getTopPageNo() : pageNo;  //如果pageNo为0，则设置pageNo为1,否则为本身
        pageSize = pageSize == null || pageSize == 0 ? page.getPageSize() : pageSize;
        //总记录数
        Long totalRecords = cpaRepertoryService.getTotalCount(where, params);
        Integer firstResult = page.countOffset(pageNo, pageSize);
        CpaResult result = cpaRepertoryService.getUnitExam(firstResult, pageSize, where, params);
        return result;
    }

    /**
     * @Author: kanyun
     * @Description: 保存单元测试（试题）
     * @Date: 2017/9/18 17:21
     * @params: ,List<CpaOption> cpaOptions,CpaSolution cpaSolution
     */
    @RequestMapping("/addUnitExam")
    @ResponseBody
    public CpaResult addUnitExam( @RequestBody ItemForm itemForm) {
        CpaResult result = new CpaResult();
        try {
            CpaRepertory cpaRepertory = itemForm.getCpaRepertory();
            List<CpaOption> cpaOptions = itemForm.getCpaOptions();
            CpaSolution cpaSolution = itemForm.getCpaSolution();
            result.setData(cpaRepertoryService.saveUnitExam(cpaRepertory, cpaOptions, cpaSolution));
            result.setState(CpaConstants.OPERATION_SUCCESS);
        }catch (Exception e){
            logger.error("/api/unitExam/addUnitExam  新增试题异常：  " + e);
            result.setState(CpaConstants.OPERATION_ERROR);
        }

        return result;
    }
    
    /**    
     *   
     * @author Kanyun 
     * @Description: 导出word
     * @date 2017/11/16 11:39  
     * @param   
     * @return
     * Controller中定义void方法，这种场景一般是通过HttpServletResponse对象来输出页面内容。注意：Controller的void方法中一定要声明HttpServletResponse类型的方法入参！
     *注意：在Controller中，@RequestMapping注解的方法，在调用这个方法时候，
     *  如果有定义HttpServletResponse类型的入参，Spring MVC框架会自动传入一个HttpServletResponse对象作为方法参数；
     *  如果有定义HttpServletRequest类型的入参，Spring MVC框架会自动传入一个HttpServletRequest对象作为方法参数。
     * void方法不定义HttpServletResponse类型的入参，HttpServletResponse对象通过RequestContextHolder上下文获取
     *  注意：这种方式是不可行的，void方法不定义HttpServletResponse类型的入参，Spring MVC会认为@RequestMapping注解中指定的路径就是要返回的视图name，(如果没有该name的页面后台报错,返回404)
     */
    @RequestMapping(value="/exportWord/{typeCode}",method= RequestMethod.POST,produces = {"application/vnd.ms-excel;charset=UTF-8"})
    public void exportWord(@PathVariable("typeCode") String typeCode, HttpServletResponse reponse){
        try {
            Object[] params = {typeCode};
            String where = "o.testType=? ";
            Long totalRecords = cpaRepertoryService.getTotalCount(where, params);
            CpaResult result = cpaRepertoryService.getUnitExam(-1, -1, where, params);
            List<CpaRepertoryDto> list = (List) result.getData();
            Map map = new HashMap<>();
            map.put("cpaRepertoryDtos",list);
            WordUtil.exportWord(map,  reponse);
        }catch (Exception e){
            logger.error("/api/unitExam/exportWord  导出word试题异常：  " + e);
        }

    }
    
}
