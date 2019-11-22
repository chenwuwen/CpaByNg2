package cn.kanyun.cpa.service.itempool;

import cn.kanyun.cpa.dao.common.DataSourceContextHolder;
import cn.kanyun.cpa.dao.common.annotation.DataSource;
import cn.kanyun.cpa.model.dto.itempool.CpaRepertoryDto;
import cn.kanyun.cpa.model.entity.CpaResult;
import cn.kanyun.cpa.model.entity.itempool.CpaOption;
import cn.kanyun.cpa.model.entity.itempool.CpaRepertory;
import cn.kanyun.cpa.model.entity.itempool.CpaSolution;
import cn.kanyun.cpa.service.CommonService;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

/**
 *
 * @author Administrator
 * @date 2017/6/16
 */
public interface CpaRepertoryService extends CommonService<Long, CpaRepertory> {

     String SERVICE_NAME = "cn.kanyun.cpa.Service.itempool.impl.CpaRepertoryServiceImpl";

    /**
     * @Author: kanyun
     * @Description: 获取试题列表（单元测试）
     * @Date: 2017/8/16 15:05
     * @params:
     */
    CpaResult getUnitExam(Integer firstResult, Integer pageSize, String where, Object[] params);

    /**
     * @param cpaRepertory
     * @param cpaOptions
     * @param cpaSolution
     * @return
     * @Description: 保存试题，单独保存，(单元测试)，选择主数据库库进行插入
     * @Date: 2017/9/16 15:05
     */
    Integer saveUnitExam(CpaRepertory cpaRepertory, Set<CpaOption> cpaOptions, CpaSolution cpaSolution);

    /**
     * @describe: 获取试题列表（不包括选项,答案等内容，供后台试题管理使用）
     * @params: orderBy 排序
     * @author: Kanyun
     * @Date: 2018/1/11  15:48
     */
    @DataSource(targetDataSource = DataSourceContextHolder.DATA_SOURCE_SLAVE)
    CpaResult getListItem(CpaRepertoryDto cpaRepertoryDto, LinkedHashMap orderBy);

    /**
     * 修改试题
     * @param cpaRepertory
     * @author Kanyun
     * @date 2018/1/12  14:15
     */
    Integer updateUnitExam(CpaRepertory cpaRepertory, Set<CpaOption> cpaOptions, CpaSolution cpaSolution);
}
