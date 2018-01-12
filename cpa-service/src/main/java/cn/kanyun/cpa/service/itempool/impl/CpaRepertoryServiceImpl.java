package cn.kanyun.cpa.service.itempool.impl;


import cn.kanyun.cpa.dao.itempool.CpaOptionDao;
import cn.kanyun.cpa.dao.itempool.CpaRepertoryDao;
import cn.kanyun.cpa.dao.itempool.CpaSolutionDao;
import cn.kanyun.cpa.model.dto.itempool.CpaOptionDto;
import cn.kanyun.cpa.model.dto.itempool.CpaRepertoryDto;
import cn.kanyun.cpa.model.entity.CpaConstants;
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
import java.util.*;


/**
 * Created by Administrator on 2017/6/16.
 */
@Service(CpaRepertoryService.SERVICE_NAME)
@Transactional
public class CpaRepertoryServiceImpl extends CommonServiceImpl<Integer, CpaRepertory> implements CpaRepertoryService {
    @Resource(name = CpaRepertoryDao.REPOSITORY_NAME)
    private CpaRepertoryDao cpaRepertoryDao;
    @Resource(name = CpaOptionDao.REPOSITORY_NAME)
    private CpaOptionDao cpaOptionDao;
    @Resource(name = CpaSolutionDao.REPOSITORY_NAME)
    private CpaSolutionDao cpaSolutionDao;
    @Resource(name = UserCommentService.SERVICE_NAME)
    private UserCommentService userCommentService;

    @Override
//不需要事务管理的(只查询的)方法:@Transactional(propagation=Propagation.NOT_SUPPORTED),加上readOnly=true这样就做成一个只读事务，可以提高效率。
    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true, isolation = Isolation.REPEATABLE_READ)
    public CpaResult getUnitExam(Integer firstResult, Integer pageSize, String where, Object[] params) {
        CpaResult result = getScrollData(firstResult, pageSize, where, params);
        if (result.getTotalCount() > 0) {
            List<CpaRepertoryDto> cpaRepertoryDtos = new ArrayList<>();
            List<CpaRepertory> listcr = (List<CpaRepertory>) result.getData();
            List reids = new ArrayList();
            for (CpaRepertory cr : listcr) {
                CpaRepertoryDto cpaRepertoryDto = new CpaRepertoryDto();
                cpaRepertoryDto.setTestStem(cr.getTestStem());
                cpaRepertoryDto.setChoice(cr.getChoice());
                cpaRepertoryDto.setId(cr.getId());
                cpaRepertoryDto.setBresult(cr.getCpaSolution().getResult());
                reids.add(cr.getId());
                Set<CpaOption> setco = cr.getCpaOptions();
//                将Set集合转换为List集合
                List<CpaOption> listco = new ArrayList<CpaOption>();
                for (CpaOption co : setco) {
                    listco.add(co);
                }
//                将转换后的List集合，自定义排序，根据属性内的ABCD进行升序排列
                Collections.sort(listco, new Comparator<CpaOption>() {
                    @Override
                    public int compare(CpaOption o1, CpaOption o2) {
                        int sortResult = o1.getSelectData().compareTo(o2.getSelectData());
                        return sortResult;
                    }
                });
//                从排序后的List集合里取出选项内容，可以保证，他们的顺序不变
                List<CpaOptionDto> listoptions = new ArrayList<>();
//                List<CpaOption> listoptions = new ArrayList<CpaOption>(); //原写法,只取出选项的内容,ABCD在前台用Angular的过滤器来得到,由于选项内容已经排序故不必担心顺序不对的问题
                for (CpaOption co : listco) {
//                    listoptions.add(co.getOptionData());
                    CpaOptionDto cpaOptionDto = new CpaOptionDto();
                    cpaOptionDto.setSelectData(co.getSelectData());//原写法,只取出数据即可.但后来发现在提交的时候有些问题,故将ABCD也加上
                    cpaOptionDto.setOptionData(co.getOptionData());
                    listoptions.add(cpaOptionDto);
                }
                cpaRepertoryDto.setCpaOptionDtos(listoptions);
                cpaRepertoryDtos.add(cpaRepertoryDto);
            }
//            获取每个试题的评论数,主要用作页面是否显示查看评论按钮
            Object[] fields = {"reId"};
            String commentWhere = "o.reId in (:reids)";
            Map paramsMap = new HashMap();
            paramsMap.put("reids", reids);
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
    public Integer saveUnitExam(CpaRepertory cpaRepertory, List<CpaOption> cpaOptions, CpaSolution cpaSolution) {
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

/*        Hibernate在.hbm.xml文件中配置好级联关系后;如“cascade="save-update"”;那么保存的时候仅仅保存主表
        ,就可以把相关联的表也保存了，就不用一个个保存了*/
        cpaRepertory.setInsertDate(LocalDateTime.now());
        Set cpaOptions1 = new HashSet();
        for (CpaOption cpaOption : cpaOptions) {
            cpaOption.setCpaRepertory(cpaRepertory);
            cpaOptions1.add(cpaOption);
        }
        cpaSolution.setCpaRepertory(cpaRepertory);
        cpaRepertory.setCpaOptions(cpaOptions1);
        cpaRepertory.setCpaSolution(cpaSolution);
        cpaRepertoryDao.save(cpaRepertory);
        return cpaRepertory.getId().intValue();
    }

    @Override
    public CpaResult getListItem(CpaRepertory cpaRepertory, Integer firstResult, Integer pageSize, LinkedHashMap orderby) {
        CpaResult result;
        if (null == cpaRepertory.getTestStem() && null == cpaRepertory.getTestType()) {
            result = cpaRepertoryDao.getScrollData(firstResult, pageSize, null, null, orderby);
        } else {
            String typeCode = cpaRepertory.getTestType() == null ? null : cpaRepertory.getTestType();
            String testStem = cpaRepertory.getTestStem() == null ? null : cpaRepertory.getTestStem();
            StringBuilder where = new StringBuilder();
            Object[] params;
            LinkedList list = new LinkedList();
            if (typeCode != null) {
                where.append("o.typeCode=?");
                list.add(typeCode);
            }
            if (testStem != null) {
                where.append("o.testStem=?");
                list.add(testStem);
            }
            params = list.toArray();
            result = cpaRepertoryDao.getScrollData(firstResult, pageSize, where.toString(), params, orderby);
        }
        List<CpaRepertoryDto> cpaRepertoryDtos = new ArrayList<>();
        if (result.getTotalCount() > 0) {
            List<CpaRepertory> cpaRepertorys = (List<CpaRepertory>) result.getData();
            cpaRepertorys.forEach(cpaRepertory1 -> {
                CpaRepertoryDto cpaRepertoryDto = new CpaRepertoryDto();
                cpaRepertoryDto.setTestStem(cpaRepertory1.getTestStem());
                cpaRepertoryDto.setId(cpaRepertory1.getId());
//                cpaRepertoryDto.setTestType(QuestionTypeEnum.cpaRepertory1.getTestType().toUpperCase());
//                cpaRepertoryDto.setTypeCode(QuestionTypeEnum.valueOf(cpaRepertory1.getChoice().toUpperCase()));
                cpaRepertoryDtos.add(cpaRepertoryDto);
            });
        }
        result.setData(cpaRepertoryDtos);
        return result;
    }

    @Override
    public Integer updUnitExam(CpaRepertory cpaRepertory, List<CpaOption> cpaOptions, CpaSolution cpaSolution) {
        Set cpaOptions1 = new HashSet();
        for (CpaOption cpaOption : cpaOptions) {
            cpaOption.setCpaRepertory(cpaRepertory);
            cpaOptions1.add(cpaOption);
        }
        cpaSolution.setCpaRepertory(cpaRepertory);
        cpaRepertory.setCpaOptions(cpaOptions1);
        cpaRepertory.setCpaSolution(cpaSolution);
        cpaRepertoryDao.saveOrUpdate(cpaRepertory);
        return cpaRepertory.getId().intValue();
    }


}
