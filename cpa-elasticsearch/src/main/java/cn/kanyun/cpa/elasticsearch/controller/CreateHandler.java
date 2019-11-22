package cn.kanyun.cpa.elasticsearch.controller;

import cn.kanyun.cpa.elasticsearch.model.EsCpaRepertory;
import cn.kanyun.cpa.elasticsearch.repository.EsCpaRepertoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * @author Kanyun
 */
@Slf4j
@Component
public class CreateHandler {

    @Autowired
    private EsCpaRepertoryRepository esCpaRepertoryRepository;

    @Autowired(required = false)
    private ElasticsearchTemplate elasticsearchTemplate;


    /**
     * 创建索引
     *
     * @param className
     */
    public void createIndex(String className) {
        try {
            elasticsearchTemplate.createIndex(Class.forName(className));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    /**
     * 添加对象 添加对象的时候如果索引不存在会自动创建索引
     * @param items
     */
    public void insertItem(List<?> items) {
        for (Object o : items) {
            if (o instanceof EsCpaRepertory) {
                esCpaRepertoryRepository.save((EsCpaRepertory) o);
            }
        }
    }

}
