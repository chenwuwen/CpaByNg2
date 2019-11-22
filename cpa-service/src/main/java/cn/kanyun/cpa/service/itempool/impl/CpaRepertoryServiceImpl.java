package cn.kanyun.cpa.service.itempool.impl;


import cn.kanyun.cpa.dao.itempool.CpaOptionDao;
import cn.kanyun.cpa.dao.itempool.CpaRepertoryDao;
import cn.kanyun.cpa.dao.itempool.CpaSolutionDao;
import cn.kanyun.cpa.model.constants.CpaConstants;
import cn.kanyun.cpa.model.dto.itempool.CpaOptionDto;
import cn.kanyun.cpa.model.dto.itempool.CpaRepertoryDto;
import cn.kanyun.cpa.model.entity.CpaResult;
import cn.kanyun.cpa.model.entity.itempool.CpaOption;
import cn.kanyun.cpa.model.entity.itempool.CpaRepertory;
import cn.kanyun.cpa.model.entity.itempool.CpaSolution;
import cn.kanyun.cpa.service.CommonServiceImpl;
import cn.kanyun.cpa.service.itempool.CpaRepertoryService;
import cn.kanyun.cpa.service.user.UserCommentService;
import cn.kanyun.cpa.util.TypeConver;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 *
 * @author Kanyun
 * @date 2017/6/16
 */
@Service(CpaRepertoryService.SERVICE_NAME)
@Transactional(rollbackFor = {})
public class CpaRepertoryServiceImpl extends CommonServiceImpl<Long, CpaRepertory> implements CpaRepertoryService {
    @Resource(name = CpaRepertoryDao.REPOSITORY_NAME)
    private CpaRepertoryDao cpaRepertoryDao;
    @Resource(name = CpaOptionDao.REPOSITORY_NAME)
    private CpaOptionDao cpaOptionDao;
    @Resource(name = CpaSolutionDao.REPOSITORY_NAME)
    private CpaSolutionDao cpaSolutionDao;
    @Resource(name = UserCommentService.SERVICE_NAME)
    private UserCommentService userCommentService;


    /**
     * 不需要事务管理的(只查询的)方法:@Transactional(propagation=Propagation.NOT_SUPPORTED),
     * 加上readOnly=true这样就做成一个只读事务，可以提高效率。
     * @param firstResult
     * @param pageSize
     * @param where
     * @param params
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true, isolation = Isolation.REPEATABLE_READ)
    public CpaResult getUnitExam(Integer firstResult, Integer pageSize, String where, Object[] params) {
        CpaResult result = getScrollData(firstResult, pageSize, where, params);
        if (result.getTotalCount() > 0) {
            List<CpaRepertoryDto> cpaRepertoryDtos = new ArrayList<>();
            List<CpaRepertory> cpaRepertories = (List<CpaRepertory>) result.getData();
            List reIds = new ArrayList();
            for (CpaRepertory cr : cpaRepertories) {
                CpaRepertoryDto cpaRepertoryDto = new CpaRepertoryDto();
                cpaRepertoryDto.setTestStem(cr.getTestStem());
                cpaRepertoryDto.setId(cr.getId());
                cpaRepertoryDto.setStandardResult(cr.getCpaSolution().getResult());
                reIds.add(cr.getId());
                Set<CpaOption> cpaOptionSet = cr.getCpaOptions();
//                将Set集合转换为List集合
                List<CpaOption> cpaOptionList = new ArrayList<CpaOption>();
                for (CpaOption co : cpaOptionSet) {
                    cpaOptionList.add(co);
                }
//                将转换后的List集合，自定义排序，根据属性内的ABCD进行升序排列
                Collections.sort(cpaOptionList, new Comparator<CpaOption>() {
                    @Override
                    public int compare(CpaOption o1, CpaOption o2) {
                        int sortResult = o1.getSelectData().compareTo(o2.getSelectData());
                        return sortResult;
                    }
                });
//                从排序后的List集合里取出选项内容，可以保证，他们的顺序不变
                List<CpaOptionDto> listOptions = new ArrayList<>();
//                原写法,只取出选项的内容,ABCD在前台用Angular的过滤器来得到,由于选项内容已经排序故不必担心顺序不对的问题
//                List<CpaOption> listOptions = new ArrayList<CpaOption>();
                for (CpaOption co : cpaOptionList) {
//                    listoptions.add(co.getOptionData());
                    CpaOptionDto cpaOptionDto = new CpaOptionDto();
//                    原写法,只取出数据即可.但后来发现在提交的时候有些问题,故将ABCD也加上
                    cpaOptionDto.setSelectData(co.getSelectData());
                    cpaOptionDto.setOptionData(co.getOptionData());
                    listOptions.add(cpaOptionDto);
                }
                cpaRepertoryDto.setCpaOptionDtos(listOptions);
                cpaRepertoryDtos.add(cpaRepertoryDto);
            }
//            获取每个试题的评论数,主要用作页面是否显示查看评论按钮
            Object[] fields = {"reId"};
            String commentWhere = "o.reId in (:reIds)";
            Map paramsMap = new HashMap(1);
            paramsMap.put("reIds", reIds);
            List<Map<Object, Object>> list = userCommentService.getCommentCountByCondition(fields, commentWhere, paramsMap);
            if (!list.isEmpty()) {
                Map<Object, Object> commentMap = TypeConver.List2Map(list, "reId", "count");
                Iterator iterator = commentMap.entrySet().iterator();
                while (iterator.hasNext()) {
                    Map.Entry<Object, Object> entry = (Map.Entry<Object, Object>) iterator.next();
                    for (CpaRepertoryDto cpaRepertoryDto : cpaRepertoryDtos) {
                        if (cpaRepertoryDto.getId() == entry.getKey()) {
                            cpaRepertoryDto.setCommentCount((Long) entry.getValue());
                            break;
                        }
                    }
                }
            }

            result.setState(CpaConstants.OPERATION_SUCCESS);
            result.setData(cpaRepertoryDtos);
        } else {
            result.setState(CpaConstants.NOT_FOUND_DATA);
            result.setMsg("未获取到记录！");
        }
        return result;
    }

    @Override
    @Transactional(rollbackFor = {RuntimeException.class, Exception.class})
    public Integer saveUnitExam(CpaRepertory cpaRepertory, Set<CpaOption> cpaOptions, CpaSolution cpaSolution) {
//        for (CpaOption cpaOption:cpaOptions){
//            cpaOption.setCpaRepertory(cpaRepertory);
//        }
//        cpaSolution.setCpaRepertory(cpaRepertory);
//        Session session = HibernateSessionFactory.getSession();
//        Transaction tx = session.beginTransaction();
//        iCpaRepertoryDao.save(cpaRepertory);
//        iCpaSolutionDao.save(cpaSolution);
//        iCpaOptionDao.saveAll(cpaOptions);
//        tx.commit();
//        session.close();

//        Hibernate在.hbm.xml文件中配置好级联关系后;如“cascade="save-update"”;那么保存的时候仅仅保存主表 ,就可以把相关联的表也保存了，就不用一个个保存了

        cpaSolution.setCpaRepertory(cpaRepertory);
        cpaRepertory.setCpaOptions(cpaOptions);
        cpaRepertory.setCpaSolution(cpaSolution);
        cpaRepertoryDao.save(cpaRepertory);
        return cpaRepertory.getId().intValue();
    }

    @Override
    public CpaResult getListItem(CpaRepertoryDto cpaRepertoryDto, LinkedHashMap orderBy) {
        CpaResult result = cpaRepertoryDao.findCpaRepertoryByCondition(cpaRepertoryDto, orderBy);
        List<CpaRepertoryDto> cpaRepertoryDtoList = new ArrayList<>();
        if (result.getTotalCount() > 0) {
            List<CpaRepertory> cpaRepertories = (List<CpaRepertory>) result.getData();
            cpaRepertories.forEach(cpaRepertory1 -> {
                CpaRepertoryDto cpaRepertoryDto1 = new CpaRepertoryDto();
                cpaRepertoryDto1.setTestStem(cpaRepertory1.getTestStem());
                cpaRepertoryDto1.setId(cpaRepertory1.getId());
                cpaRepertoryDto1.setInsertDate(cpaRepertory1.getInsertDate());
                cpaRepertoryDto1.setTestType(cpaRepertory1.getTestType());
                cpaRepertoryDto1.setQuestionType(cpaRepertory1.getQuestionType());
                cpaRepertoryDtoList.add(cpaRepertoryDto1);
            });
        }
        result.setData(cpaRepertoryDtoList);
        return result;
    }

    @Override
    public Integer updateUnitExam(CpaRepertory cpaRepertory, Set<CpaOption> cpaOptions, CpaSolution cpaSolution) {
        cpaRepertory.setCpaOptions(cpaOptions);
        cpaRepertory.setCpaSolution(cpaSolution);
        cpaRepertoryDao.saveOrUpdate(cpaRepertory);
        return cpaRepertory.getId().intValue();
    }


}
