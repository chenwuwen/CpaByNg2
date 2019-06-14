package cn.kanyun.cpa.controller.itempool;

import cn.kanyun.cpa.model.constants.CpaConstants;
import cn.kanyun.cpa.model.dto.itempool.CpaOptionDto;
import cn.kanyun.cpa.model.dto.itempool.CpaRepertoryDto;
import cn.kanyun.cpa.model.dto.itempool.ItemForm;
import cn.kanyun.cpa.model.entity.CpaResult;
import cn.kanyun.cpa.model.entity.Page;
import cn.kanyun.cpa.model.entity.itempool.CpaOption;
import cn.kanyun.cpa.model.entity.itempool.CpaRepertory;
import cn.kanyun.cpa.model.entity.itempool.CpaSolution;
import cn.kanyun.cpa.model.enums.ExamClassificationEnum;
import cn.kanyun.cpa.redis.CacheLoad;
import cn.kanyun.cpa.redis.CacheServiceTemplate;
import cn.kanyun.cpa.redis.RedisService;
import cn.kanyun.cpa.service.itempool.CpaRepertoryService;
import cn.kanyun.cpa.util.WordUtil;
import com.alibaba.fastjson.TypeReference;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.formula.functions.T;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Created by KANYUN on 2017/6/17.
 */

/**
 * 在方法级@RequestMapping的注解增加了一个RequestMothod.GET，意思是只有以GET方式提交的"/fileupload"URL请求才会进入fileUploadForm()
 * 方法，其实根据HTTP协议，HTTP支持一系列提交方法（GET，POST，PUT，DELETE），同一个URL都可以使用这几种提交方式
 * 事实上SpringMVC正是通过将同一个URL的不同提交方法对应到不同的方法上达到RESTful
 */
@Api(value = "/api/unitExam", tags = "试题管理模块")
@Controller
@RequestMapping("/api/unitExam")
public class CpaRepertoryController {

    private static final Logger logger = LoggerFactory.getLogger(CpaRepertoryController.class);

    @Resource(name = CpaRepertoryService.SERVICE_NAME)
    private CpaRepertoryService cpaRepertoryService;
    @Resource
    private RedisService redisService;
    @Resource
    private CacheServiceTemplate cacheServiceTemplate;

    /**
     * @Author: kanyun
     * @Description: 获取试题列表（单元测试）required = false表示参数为可选,
     * 如果不加此配置,当请求的uri不带此参数时会报400错误码
     * @Date: 2017/8/16 14:58
     * @params:
     */
    @ApiOperation(value = "/getUnitExam/{testType}/{pageNo}/{pageSize}", notes = "请求试题列表【单元测试】", httpMethod = "POST", response = CpaResult.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "testType", value = "试题类型", dataType = "String", paramType = "path", required = true),
            @ApiImplicitParam(name = "pageNo", value = "页码", dataType = "Integer", paramType = "path", required = false),
            @ApiImplicitParam(name = "pageSize", value = "每页显示数量", dataType = "Integer", paramType = "path", required = false)
    })
    @RequestMapping("/getUnitExam/{testType}/{pageNo}/{pageSize}")
    @ResponseBody
    public CpaResult getUnitExam(@PathVariable("testType") String testType, @PathVariable(value = "pageNo", required = false) Integer pageNo, @PathVariable(value = "pageSize", required = false) Integer pageSize) {
        CpaResult result = null;
        try {
            Object[] params = {testType};
            String where = "o.testType=?";
            Page page = new Page();
//            如果pageNo为0，则设置pageNo为1,否则为本身
            pageNo = pageNo == null || pageNo == 0 ? page.getTopPageNo() : pageNo;
            pageSize = pageSize == null || pageSize == 0 ? page.getPageSize() : pageSize;
//            从jdk1.8开始可以在内部匿名类中使用非final变量,不过前提就是这个局部变量不能再被重新赋值,所以这只是个语法糖,本质上被没有改变,所以这类final变量称之为effectively final
            final int startPage = pageNo;
            final int displaySize = pageNo;
//            key由当前类名+方法名+查询条件+参数+分页组成,尽可能保证key的唯一性
            String key = this.getClass().getName() + Thread.currentThread().getStackTrace()[1].getMethodName() + where + StringUtils.join(params) + pageNo + pageSize;
            logger.info("Redis缓存的Key：" + key);
//            这个TypeReference的构造方法是protected,所以无法再外部使用new的方式来构建实例,通过添加大括号的方式以内部类的方式构建实例
            result = cacheServiceTemplate.getObjectFromCache(key, 12, TimeUnit.HOURS, new TypeReference<CpaResult>() {
            }, new CacheLoad<CpaResult>() {
                @Override
                public CpaResult load() {
                    int firstResult = page.countOffset(startPage, displaySize);
                    CpaResult result = cpaRepertoryService.getUnitExam(firstResult, displaySize, where, params);

                    //总记录数
                    page.setTotalRecords(result.getTotalCount().intValue());
                    //总页数(返回的记录中已包含总记录数,无需再次查询)
                    page.setPageSize(displaySize);
                    page.setPageNo(startPage);
                    result.setTotalPage(page.getTotalPages());
                    return result;
                }
            });
//            下面这种请求redis的方式存在两个问题:一是这类代码会在多个地方使用,代码冗余度高,二是:该方法会造成缓存穿透,需要使用双重锁的方式避免
//            try {
//                result = (CpaResult) redisService.getCacheObject(key,CpaResult.class);
//            } catch (Exception e) {
//                logger.error("ERROR： /api/unitExam/getUnitExam Redis {}", e);
//            }
//            if (null == result) {
//                Integer firstResult = page.countOffset(pageNo, pageSize);
//                result = cpaRepertoryService.getUnitExam(firstResult, pageSize, where, params);
//
//                //总记录数
//                page.setTotalRecords(result.getTotalCount().intValue());
//                //总页数(返回的记录中已包含总记录数,无需再次查询)
//                page.setPageSize(pageSize);
//                page.setPageNo(pageNo);
//                result.setTotalPage(page.getTotalPages());
//                try {
//                    redisService.setCacheObject(key, result);
//                } catch (Exception e) {
//                    logger.error("ERROR： /api/unitExam/getUnitExam Redis Error: {}" + e);
//                }
//            }
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
    @ApiOperation(value = "/addUnitExam", notes = "新增试题【保存单元测试】", httpMethod = "POST", response = CpaResult.class)
    @RequestMapping("/addUnitExam")
    @ResponseBody
    public CpaResult addUnitExam(@RequestBody ItemForm itemForm) {
        CpaResult result = new CpaResult();
        try {
            CpaRepertory cpaRepertory = itemForm.getCpaRepertory();
            cpaRepertory.setTestStem(StringUtils.trimToEmpty(cpaRepertory.getTestStem()));
            List<CpaOption> cpaOptions = new ArrayList<>();
            itemForm.getCpaOptions().forEach(cpaOption -> {
                cpaOption.setOptionData(StringUtils.trimToEmpty(cpaOption.getOptionData()));
                cpaOptions.add(cpaOption);
            });
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
     * void方法不定义HttpServletResponse类型的入参，HttpServletResponse对象通过RequestContextHolder上下文获取
     * 注意：这种方式是不可行的，void方法不定义HttpServletResponse类型的入参，Spring MVC会认为@RequestMapping注解中指定的路径就是要返回的视图name，(如果没有该name的页面后台报错,返回404)
     * @author Kanyun
     * @Description: 试题导出到word
     * @date 2017/11/16 11:39
     */
    @ApiOperation(value = "/exportWord/{typeCode}", notes = "试题导出到word", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "typeCode", value = "试题类型编码", dataType = "String", paramType = "path")
    })
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
            map.put("type", ExamClassificationEnum.valueOf(typeCode.toUpperCase()));
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
    @ApiOperation(value = "/getExamDetail/{id}", notes = "获取单个试题详情", httpMethod = "GET", response = CpaResult.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "试题ID", dataType = "Long", paramType = "path")
    })
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
            cpaRepertoryDto.setQuestionType(cpaRepertory.getQuestionType());
            cpaRepertoryDto.setStandardResult(cpaRepertory.getCpaSolution().getResult());
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
    @ApiOperation(value = "/delUnitExam/{reId}", notes = "删除试题【单元测试】", httpMethod = "GET", response = CpaResult.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "reId", value = "试题ID", dataType = "Long", paramType = "path")
    })
    @RequestMapping("/delUnitExam/{reId}")
    @ResponseBody
    public CpaResult delUnitExam(@PathVariable("reId") Long reId) {
        CpaResult result = new CpaResult();
        try {
            cpaRepertoryService.deleteById(reId);
            result.setState(CpaConstants.OPERATION_SUCCESS);
        } catch (Exception e) {
            result.setState(CpaConstants.OPERATION_ERROR);
            logger.error("ERROR：/api/unitExam/delUnitExam: {}", e);
        }
        return result;
    }

    /**
     * @describe: 修改试题
     * @params:
     * @Author: Kanyun
     * @Date: 2018/1/11 0011 11:39
     */
    @ApiOperation(value = "/updUnitExam", notes = "修改试题", httpMethod = "POST", response = CpaResult.class)
    @RequestMapping("/updUnitExam")
    @ResponseBody
    public CpaResult updUnitExam(@RequestBody ItemForm itemForm) {
        CpaResult result = new CpaResult();
        try {
            CpaRepertory cpaRepertory = itemForm.getCpaRepertory();
            cpaRepertory.setTestStem(StringUtils.trimToEmpty(cpaRepertory.getTestStem()));
            List<CpaOption> cpaOptions = new ArrayList<>();
            itemForm.getCpaOptions().forEach(cpaOption -> {
                cpaOption.setOptionData(StringUtils.trimToEmpty(cpaOption.getOptionData()));
                cpaOptions.add(cpaOption);
            });
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
    @ApiOperation(value = "/getListExam", notes = "获取试题列表（仅试题本身,删除修改试题使用）", httpMethod = "GET", response = CpaResult.class)
    @RequestMapping(value = "/getListExam", produces = {"application/json;charset=utf-8"})
    @ResponseBody
    public CpaResult getListExam(@RequestBody ItemForm itemForm) {
        CpaResult result = null;
        CpaRepertoryDto cpaRepertoryDto = itemForm.getCpaRepertoryDto();
        cpaRepertoryDto.setPageNo(itemForm.getPageNo());
        cpaRepertoryDto.setPageSize(itemForm.getPageSize());
        try {
            result = cpaRepertoryService.getListItem(cpaRepertoryDto, null);
            result.setState(CpaConstants.OPERATION_SUCCESS);
        } catch (Exception e) {
            logger.error("ERROR：/api/unitExam/getListExam ：{}", e);
            result.setState(CpaConstants.OPERATION_ERROR);

        }
        return result;
    }

}

