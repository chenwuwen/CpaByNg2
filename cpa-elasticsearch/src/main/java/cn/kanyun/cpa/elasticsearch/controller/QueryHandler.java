package cn.kanyun.cpa.elasticsearch.controller;

import cn.kanyun.cpa.elasticsearch.model.EsCpaRepertory;
import cn.kanyun.cpa.elasticsearch.repository.EsCpaRepertoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Component;

/**
 * @author Kanyun
 */
@Slf4j
@Component
public class QueryHandler {

    /**
     * 注入EsCpaRepertory crud接口对象
     */
    @Autowired
    private EsCpaRepertoryRepository esCpaRepertoryRepository;


    public void getCpaRepertory(String field, String arg) {
//         构建查询条件
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
//         添加基本分词查询，QueryBuilders提供了大量的静态方法，用于生成各种不同类型的查询
//        match查询会先对搜索词进行分词,分词完毕后再逐个对分词结果进行匹配，因此相比于term的精确搜索，match是分词匹配搜索,match搜索还有两个相似功能的变种，一个是match_phrase，一个是multi_match
        queryBuilder.withQuery(QueryBuilders.matchQuery(field, arg));
//        term是代表完全匹配，也就是精确查询，搜索前不会再对搜索词进行分词
//        queryBuilder.withQuery(QueryBuilders.termQuery(field, arg));
//         搜索，获取结果
        Page<EsCpaRepertory> items = esCpaRepertoryRepository.search(queryBuilder.build());
        // 总条数
        long total = items.getTotalElements();
        log.info("total = {}", total);
        for (EsCpaRepertory item : items) {
            log.info(item.toString());
        }
    }

}
