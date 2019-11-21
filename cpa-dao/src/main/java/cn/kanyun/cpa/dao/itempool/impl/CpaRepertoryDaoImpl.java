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
 * @author Kanyun
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
    public CpaResult findCpaRepertoryByCondition(CpaRepertoryDto cpaRepertoryDto, LinkedHashMap orderBy) {
        CpaResult result;
        Page page = new Page(cpaRepertoryDto);
        int firstResult = page.countOffset();
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
            result = getScrollData(firstResult, page.getPageSize(), where.toString(), params, orderBy);
        } else {
            result = getScrollData(firstResult, page.getPageSize(), null, null, orderBy);
        }
        page.setTotalRecords(result.getTotalCount().intValue());
        result.setTotalPage(page.getTotalPages());
        return result;
    }
}
