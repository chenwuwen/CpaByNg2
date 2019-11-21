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

    @Autowired
    private EsCpaRepertoryRepository esCpaRepertoryRepository;


    public void getCpaRepertory(String arg) {
        // 构建查询条件
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        // 添加基本分词查询，QueryBuilders提供了大量的静态方法，用于生成各种不同类型的查询
        queryBuilder.withQuery(QueryBuilders.matchQuery("title", "小米手机"));
        // 搜索，获取结果
        Page<EsCpaRepertory> items = esCpaRepertoryRepository.search(queryBuilder.build());
        // 总条数
        long total = items.getTotalElements();
        log.info("total = {}", total);
        for (EsCpaRepertory item : items) {
            log.info(item.toString());
        }
    }

}
