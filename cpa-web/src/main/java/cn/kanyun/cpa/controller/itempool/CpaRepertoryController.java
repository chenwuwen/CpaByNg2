package cn.kanyun.cpa.controller.itempool;

import cn.kanyun.cpa.model.dto.itempool.CpaOptionDto;
import cn.kanyun.cpa.model.dto.itempool.CpaRepertoryDto;
import cn.kanyun.cpa.model.dto.itempool.ItemForm;
import cn.kanyun.cpa.model.entity.CpaConstants;
import cn.kanyun.cpa.model.entity.CpaResult;
import cn.kanyun.cpa.model.myenum.ExamEnum;
import cn.kanyun.cpa.model.entity.Page;
import cn.kanyun.cpa.model.entity.itempool.CpaOption;
import cn.kanyun.cpa.model.entity.itempool.CpaRepertory;
import cn.kanyun.cpa.model.entity.itempool.CpaSolution;
import cn.kanyun.cpa.redis.service.RedisService;
import cn.kanyun.cpa.service.itempool.CpaRepertoryService;
import cn.kanyun.cpa.util.WordUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

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

    @Resource(name = CpaRepertoryService.SERVICE_NAME)
    private CpaRepertoryService cpaRepertoryService;
    @Resource
    private RedisService redisService;

    /**
     * @Author: kanyun
     * @Description: 获取试题列表（单元测试）
     * @Date: 2017/8/16 14:58
     * @params:
     */
    @RequestMapping("/getUnitExam/{typeCode}")
    @ResponseBody
    public CpaResult getUnitExam(@PathVariable("typeCode") String typeCode, Integer pageNo, Integer pageSize) {
        CpaResult result = null;
        try {
            Object[] params = {typeCode};
            String where = "o.testType=?";
            Page page = new Page();
            pageNo = pageNo == null || pageNo == 0 ? page.getTopPageNo() : pageNo;  //如果pageNo为0，则设置pageNo为1,否则为本身
            pageSize = pageSize == null || pageSize == 0 ? page.getPageSize() : pageSize;
//            key由当前类名+方法名+查询条件+参数+分页组成,尽可能保证key的唯一性
            String key = this.getClass().getName() + Thread.currentThread().getStackTrace()[1].getMethodName() + where + StringUtils.join(params) + pageNo + pageSize;
            logger.info("Redis缓存的Key：" + key);
            try {
                result = (CpaResult) redisService.getCacheObject(key);
            } catch (Exception e) {
                logger.error("ERROR： /api/unitExam/getUnitExam Redis {}" + e);
            }
            if (null == result) {
                Integer firstResult = page.countOffset(pageNo, pageSize);
                result = cpaRepertoryService.getUnitExam(firstResult, pageSize, where, params);
                //总记录数
                page.setTotalRecords(result.getTotalCount().intValue());
                //总页数(返回的记录中已包含总记录数,无需再次查询)
                result.setTotalPage(page.getTotalPages());
                try {
                    redisService.setCacheObject(key, result);
                } catch (Exception e) {
                    logger.error("ERROR： /api/unitExam/getUnitExam Redis Error: {}" + e);
                }
            }
        } catch (Exception e) {
            logger.error("ERROR： /api/unitExam/getUnitExam Error: {}" + e);
            result.setState(CpaConstants.OPERATION_ERROR);
        }
        return result;
    }

    /**
     * @Author: kanyun
     * @Description: 新增试题【保存单元测试】
     * @Date: 2017/9/18 17:21
     * @params: , List<CpaOption> cpaOptions,CpaSolution cpaSolution
     */
    @RequestMapping("/addUnitExam")
    @ResponseBody
    public CpaResult addUnitExam(@RequestBody ItemForm itemForm) {
        CpaResult result = new CpaResult();
        try {
            CpaRepertory cpaRepertory = itemForm.getCpaRepertory();
            List<CpaOption> cpaOptions = itemForm.getCpaOptions();
            CpaSolution cpaSolution = itemForm.getCpaSolution();
            result.setData(cpaRepertoryService.saveUnitExam(cpaRepertory, cpaOptions, cpaSolution));
            result.setState(CpaConstants.OPERATION_SUCCESS);
        } catch (Exception e) {
            logger.error("ERROR： /api/unitExam/addUnitExam  新增试题异常：  {}" + e);
            result.setState(CpaConstants.OPERATION_ERROR);
        }
        return result;
    }

    /**
     * @param
     * @return Controller中定义void方法，这种场景一般是通过HttpServletResponse对象来输出页面内容。注意：Controller的void方法中一定要声明HttpServletResponse类型的方法入参！
     * 注意：在Controller中，@RequestMapping注解的方法，在调用这个方法时候，
     * 如果有定义HttpServletResponse类型的入参，Spring MVC框架会自动传入一个HttpServletResponse对象作为方法参数；
     * 如果有定义HttpServletRequest类型的入参，Spring MVC框架会自动传入一个HttpServletRequest对象作为方法参数。
     * void方法不定义HttpServletResponse类型的入参，HttpServletResponse对象通过RequestContextHolder上下文获取
     * 注意：这种方式是不可行的，void方法不定义HttpServletResponse类型的入参，Spring MVC会认为@RequestMapping注解中指定的路径就是要返回的视图name，(如果没有该name的页面后台报错,返回404)
     * @author Kanyun
     * @Description: 导出word
     * @date 2017/11/16 11:39
     */
    @RequestMapping(value = "/exportWord/{typeCode}", method = RequestMethod.GET, produces = {"application/msword;charset=UTF-8"})
    public void exportWord(@PathVariable("typeCode") String typeCode, HttpServletResponse response) {
        try {
            Object[] params = {typeCode};
            String where = "o.testType=? ";
            CpaResult result = cpaRepertoryService.getUnitExam(-1, -1, where, params);
            List<CpaRepertoryDto> list = (List) result.getData();
            Map map = new HashMap<>();
            map.put("cpaRepertoryDtos", list);
            map.put("total", result.getTotalCount());
            map.put("type", ExamEnum.valueOf(typeCode.toUpperCase()));
            WordUtil.exportWord(map, response);
        } catch (Exception e) {
            logger.error("ERROR：/api/unitExam/exportWord  导出word试题异常：  {}" + e);
        }

    }

    /**
     * @describe: 获取单个试题详情
     * @params:
     * @Author: Kanyun
     * @Date: 2018/1/12  13:38
     */
    @RequestMapping("/getExamDetail/{id}")
    @ResponseBody
    public CpaResult getExamDetail(@PathVariable("id") Long id) {
        CpaResult result = new CpaResult();
        try {
            CpaRepertory cpaRepertory = cpaRepertoryService.findById(id);
            CpaRepertoryDto cpaRepertoryDto = new CpaRepertoryDto();
            cpaRepertoryDto.setId(id);
            cpaRepertoryDto.setTestStem(cpaRepertory.getTestStem());
            cpaRepertoryDto.setTestType(cpaRepertory.getTestType());
            cpaRepertoryDto.setChoice(cpaRepertory.getChoice());
            cpaRepertoryDto.setBresult(cpaRepertory.getCpaSolution().getResult());
            List<CpaOptionDto> cpaOptionDtoList = new ArrayList<>();
            Set<CpaOption> cpaOptions = cpaRepertory.getCpaOptions();
            List<CpaOption> cpaOptionList = new ArrayList(new HashSet(cpaOptions));
//        Java 8 Lambda表达式对列表进行排序,但是这个方法还可以在进行精简
//        Collections.sort(cpaOptionList, (o1, o2) -> o1.getSelectData().compareTo(o2.getSelectData()));
            Collections.sort(cpaOptionList, Comparator.comparing(CpaOption::getSelectData));
            cpaOptionList.forEach(cpaOption -> {
                CpaOptionDto cpaOptionDto = new CpaOptionDto();
                BeanUtils.copyProperties(cpaOption, cpaOptionDto);
                cpaOptionDtoList.add(cpaOptionDto);
            });
            cpaRepertoryDto.setCpaOptionDtos(cpaOptionDtoList);
            result.setData(cpaRepertoryDto);
            result.setState(CpaConstants.OPERATION_SUCCESS);
        } catch (Exception e) {
            logger.error("ERROR：/api/unitExam/getExamDetail: {}" + e);
            result.setState(CpaConstants.OPERATION_ERROR);
        }
        return result;

    }


    /**
     * @describe: 删除试题【单元测试】
     * @params:
     * @Author: Kanyun
     * @Date: 2018/1/9  17:40
     */
    @RequestMapping("/delUnitExam/{reId}")
    @ResponseBody
    public CpaResult delUnitExam(@PathVariable("reId") Long reId) {
        CpaResult result = new CpaResult();
        try {
            cpaRepertoryService.deleteById(reId);
            result.setState(CpaConstants.OPERATION_SUCCESS);
        } catch (Exception e) {
            result.setState(CpaConstants.OPERATION_ERROR);
            logger.error("ERROR：/api/unitExam/delUnitExam: {}" + e);
        }
        return result;
    }

    /**
     * @describe: 修改试题
     * @params:
     * @Author: Kanyun
     * @Date: 2018/1/11 0011 11:39
     */
    @RequestMapping("/updUnitExam")
    @ResponseBody
    public CpaResult updUnitExam(ItemForm itemForm) {
        CpaResult result = new CpaResult();
        try {
            CpaRepertory cpaRepertory = itemForm.getCpaRepertory();
            List<CpaOption> cpaOptions = itemForm.getCpaOptions();
            CpaSolution cpaSolution = itemForm.getCpaSolution();
            result.setData(cpaRepertoryService.updUnitExam(cpaRepertory, cpaOptions, cpaSolution));
            result.setState(CpaConstants.OPERATION_SUCCESS);
        } catch (Exception e) {
            result.setState(CpaConstants.OPERATION_ERROR);
            logger.error("ERROR：/api/unitExam/updUnitExam: {}" + e);
        }
        return result;
    }

    /**
     * @describe: 获取试题列表（仅试题本身,删除修改试题使用）
     * @params:
     * @Author: Kanyun
     * @Date: 2018/1/11  9:32
     */
    @RequestMapping("/getListExam")
    @ResponseBody
    public CpaResult getListExam(CpaRepertory cpaRepertory, Integer pageNo, Integer pageSize) {
        CpaResult result = null;
        Page page = new Page();
        pageNo = pageNo == null || pageNo == 0 ? page.getTopPageNo() : pageNo;  //如果pageNo为0，则设置pageNo为1,否则为本身
        pageSize = pageSize == null || pageSize == 0 ? page.getPageSize() : pageSize;
        Integer firstResult = page.countOffset(pageNo, pageSize);
        LinkedHashMap orderby = new LinkedHashMap() {{
            put("id", "desc");
        }};
        try {
            result = cpaRepertoryService.getListItem(cpaRepertory, firstResult, pageSize, orderby);
            result.setState(CpaConstants.OPERATION_SUCCESS);
        } catch (Exception e) {
            logger.error("ERROR：/api/unitExam/getListExam ：{}" + e);
            result.setState(CpaConstants.OPERATION_ERROR);

        }
        return result;
    }

}
