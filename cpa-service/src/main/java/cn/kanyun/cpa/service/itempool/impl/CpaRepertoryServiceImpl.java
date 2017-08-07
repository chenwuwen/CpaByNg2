package cn.kanyun.cpa.service.itempool.impl;


import cn.kanyun.cpa.dao.itempool.ICpaRepertoryDao;
import cn.kanyun.cpa.model.dto.itempool.CpaOptionDto;
import cn.kanyun.cpa.model.dto.itempool.CpaRepertoryDto;
import cn.kanyun.cpa.model.entity.CpaResult;
import cn.kanyun.cpa.model.entity.itempool.CpaOption;
import cn.kanyun.cpa.model.entity.itempool.CpaRepertory;
import cn.kanyun.cpa.service.CommonServiceImpl;
import cn.kanyun.cpa.service.itempool.ICpaRepertoryService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by Administrator on 2017/6/16.
 */
@Service(ICpaRepertoryService.SERVICE_NAME)
public class CpaRepertoryServiceImpl extends CommonServiceImpl<Integer, CpaRepertory> implements ICpaRepertoryService {
    @Resource(name = ICpaRepertoryDao.REPOSITORY_NAME)
    private ICpaRepertoryDao iCpaRepertoryDao;
    //获取单元测试数据
    public CpaResult getUnitExam(String where, Object[] params) {
        CpaResult result = iCpaRepertoryDao.getScrollData(-1, -1, where, params);
        if (result.getTotalCount() > 0) {
            List<CpaRepertoryDto> cpaRepertoryDtos = new ArrayList<>();
            List<CpaRepertory> listcr = (List<CpaRepertory>) result.getData();
            for (CpaRepertory cr : listcr) {
                CpaRepertoryDto cpaRepertoryDto = new CpaRepertoryDto();
                cpaRepertoryDto.setTestStem(cr.getTestStem());
                cpaRepertoryDto.setId(cr.getId());
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
            result.setStatus(1);
            result.setData(cpaRepertoryDtos);
        } else {
            result.setStatus(0);
            result.setMsg("未获取到记录！");
        }
        return result;
    }

}
