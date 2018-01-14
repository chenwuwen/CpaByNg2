package cn.kanyun.cpa.service.itempool;

import cn.kanyun.cpa.dao.common.DataSourceContextHolder;
import cn.kanyun.cpa.dao.common.annotation.DataSource;
import cn.kanyun.cpa.model.entity.CpaResult;
import cn.kanyun.cpa.model.entity.itempool.CpaOption;
import cn.kanyun.cpa.model.entity.itempool.CpaRepertory;
import cn.kanyun.cpa.model.entity.itempool.CpaSolution;
import cn.kanyun.cpa.service.CommonService;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by Administrator on 2017/6/16.
 */
public interface CpaRepertoryService extends CommonService<Long, CpaRepertory> {
    public static final String SERVICE_NAME = "cn.kanyun.cpa.Service.itempool.impl.CpaRepertoryServiceImpl";

    /**
     * @Author: kanyun
     * @Description: 获取试题列表（单元测试）,选择从数据库库进行查询
     * @Date: 2017/8/16 15:05
     * @params:
     */
    @DataSource(targetDataSource = DataSourceContextHolder.DATA_SOURCE_SLAVE)
    CpaResult getUnitExam(Integer firstResult, Integer pageSize, String where, Object[] params);

    /**
     * @param cpaRepertory
     * @param cpaOptions
     * @param cpaSolution
     * @return
     * @Description: 保存试题，单独保存，(单元测试)，选择主数据库库进行插入
     * @Date: 2017/9/16 15:05
     */
    Integer saveUnitExam(CpaRepertory cpaRepertory, List<CpaOption> cpaOptions, CpaSolution cpaSolution);

    /**
     * @describe: 获取试题列表（不包括选项,答案等内容，供后台试题管理使用）
     * @params:
     * @Author: Kanyun
     * @Date: 2018/1/11  15:48
     */
    CpaResult getListItem(CpaRepertory cpaRepertory, Integer firstResult, Integer pageSize, LinkedHashMap orderby);

    /**
     * @describe: 修改试题
     * @params:
     * @Author: Kanyun
     * @Date: 2018/1/12  14:15
     */
    Integer updUnitExam(CpaRepertory cpaRepertory, List<CpaOption> cpaOptions, CpaSolution cpaSolution);
}
