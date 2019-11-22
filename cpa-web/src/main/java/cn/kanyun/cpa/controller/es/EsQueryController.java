package cn.kanyun.cpa.controller.es;

import cn.kanyun.cpa.elasticsearch.controller.CreateHandler;
import cn.kanyun.cpa.elasticsearch.controller.QueryHandler;
import cn.kanyun.cpa.elasticsearch.model.EsCpaRepertory;
import cn.kanyun.cpa.model.dto.itempool.CpaRepertoryDto;
import cn.kanyun.cpa.model.entity.CpaResult;
import cn.kanyun.cpa.service.itempool.CpaRepertoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Kanyun
 */
@Api(value = "/api/es", tags = "ElasticSearch搜索模块")
@RestController
@RequestMapping("/es")
public class EsQueryController {

    @Resource
    private QueryHandler queryHandler;

    @Resource
    private CreateHandler createHandler;

    @Resource
    private CpaRepertoryService cpaRepertoryService;

    @ApiOperation(value = "/query", notes = "关键字查询", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "field", value = "字段名", dataType = "String", paramType = "query", required = true,defaultValue = "testStem"),
            @ApiImplicitParam(name = "queryStr", value = "查询字符串", dataType = "String", paramType = "query", required = true),
    })
    @GetMapping("/query")
    public void query(@RequestParam("field") String field, @RequestParam("queryStr") String queryStr) {
        queryHandler.getCpaRepertory(field, queryStr);
    }


    @ApiOperation(value = "/init", notes = "初始化索引", httpMethod = "GET")
    @GetMapping("/init")
    public void init() {
        CpaRepertoryDto cpaRepertoryDto = new CpaRepertoryDto();
        cpaRepertoryDto.setPageSize(Integer.MAX_VALUE);
        CpaResult<List<CpaRepertoryDto>> result = cpaRepertoryService.getListItem(cpaRepertoryDto, null);
        List<CpaRepertoryDto> list = result.getData();
        List<EsCpaRepertory> cpaRepertories = new ArrayList<>();
        ZoneId zone = ZoneId.systemDefault();
        list.forEach(repertoryDto -> {
            EsCpaRepertory esCpaRepertory = new EsCpaRepertory();
            esCpaRepertory.setTestStem(repertoryDto.getTestStem());
            esCpaRepertory.setInsertDate(repertoryDto.getInsertDate().atZone(zone).toEpochSecond());
            cpaRepertories.add(esCpaRepertory);
        });
        createHandler.insertItem(cpaRepertories);
    }
}