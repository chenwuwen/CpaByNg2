package cn.kanyun.cpa.dao.itempool.impl;

import cn.kanyun.cpa.dao.CommonDaoImpl;
import cn.kanyun.cpa.dao.itempool.CpaRepertoryDao;
import cn.kanyun.cpa.model.dto.itempool.CpaRepertoryDto;
import cn.kanyun.cpa.model.entity.CpaResult;
import cn.kanyun.cpa.model.entity.Page;
import cn.kanyun.cpa.model.entity.itempool.CpaRepertory;
import org.springframework.stereotype.Repository;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Optional;
import java.util.Queue;

/**
 * @author Administrator
 * @date 2017/6/16
 */
@Repository(CpaRepertoryDao.REPOSITORY_NAME)
public class CpaRepertoryDaoImpl extends CommonDaoImpl<Long, CpaRepertory> implements CpaRepertoryDao {

    public CpaRepertoryDaoImpl() {
        super(CpaRepertory.class);
    }

    /**
     * 保留指定clatt值的接口【通过子类显示调用父类的构造函数来指定】
     * 通过调用父类的构造函数指定clazz值，即实体类的类类型
     *
     * @param clatt
     */
    public CpaRepertoryDaoImpl(Class<CpaRepertory> clatt) {
        super(clatt);
    }


    @Override
    public CpaResult findCpaRepertoryByCondition(CpaRepertoryDto cpaRepertoryDto, LinkedHashMap orderby) {
        CpaResult result;
        Page page = new Page();
        int pageNo = cpaRepertoryDto.getPageNo() == null || cpaRepertoryDto.getPageNo() == 0 ? 1 : cpaRepertoryDto.getPageNo();
        int pageSize = cpaRepertoryDto.getPageSize() == null || cpaRepertoryDto.getPageSize() == 0 ? 20 : cpaRepertoryDto.getPageSize();
        page.setPageNo(pageNo);
        page.setPageSize(pageSize);
        int firstResult = page.countOffset(pageNo, pageSize);
        Object[] params;
        Queue queue = new LinkedList();
        StringBuilder where = new StringBuilder();

//        if (StringUtils.isNotBlank(cpaRepertoryDto.getTestStem())) {
//            where.append(" and o.testStem like ? ");
//            queue.add("%" + cpaRepertoryDto.getTestStem() + "%");
//        }
//        if (cpaRepertoryDto.getTestType() != null) {
//            where.append(" and o.testType=? ");
//            queue.add(cpaRepertoryDto.getTestType());
//        }
//        if (cpaRepertoryDto.getQuestionType() != null) {
//            where.append(" and o.questionType=? ");
//            queue.add(cpaRepertoryDto.getQuestionType());
//        }

        Optional<CpaRepertoryDto> optional = Optional.ofNullable(cpaRepertoryDto);
        optional.map(CpaRepertoryDto::getTestStem).ifPresent(o -> {
            where.append(" and o.testStem like ? ");
            queue.add("%" + o + "%");
        });
        optional.map(CpaRepertoryDto::getTestType).ifPresent(o->{
            where.append(" and o.testType=? ");
            queue.add(o);
        });
        optional.map(CpaRepertoryDto::getQuestionType).ifPresent(o->{
            where.append(" and o.questionType=? ");
            queue.add(o);
        });


        if (queue.size() > 0) {
            params = queue.toArray();
            result = getScrollData(firstResult, pageSize, where.toString(), params, orderby);
        } else {
            result = getScrollData(firstResult, pageSize, null, null, orderby);
        }
        page.setTotalRecords(result.getTotalCount().intValue());
        result.setTotalPage(page.getTotalPages());
        return result;
    }
}
