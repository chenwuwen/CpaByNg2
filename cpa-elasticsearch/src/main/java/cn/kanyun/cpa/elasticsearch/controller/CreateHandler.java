package cn.kanyun.cpa.elasticsearch.controller;

import cn.kanyun.cpa.elasticsearch.model.EsCpaRepertory;
import cn.kanyun.cpa.elasticsearch.model.EsUserComment;
import cn.kanyun.cpa.elasticsearch.repository.EsCpaRepertoryRepository;
import cn.kanyun.cpa.elasticsearch.repository.EsUserCommentRepository;
import cn.kanyun.cpa.model.entity.itempool.CpaRepertory;
import cn.kanyun.cpa.model.entity.user.UserComment;
import cn.kanyun.cpa.model.event.RepertoryEvent;
import cn.kanyun.cpa.model.event.UserCommentEvent;
import com.google.common.eventbus.Subscribe;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.stereotype.Component;

import java.time.ZoneId;
import java.util.Date;
import java.util.List;

/**
 * @author Kanyun
 */
@Slf4j
@Component
public class CreateHandler {

    @Autowired
    private EsCpaRepertoryRepository esCpaRepertoryRepository;


    @Autowired
    private EsUserCommentRepository esUserCommentRepository;

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
     *
     * @param items
     */
    public void insertItem(List<?> items) {
        for (Object o : items) {
            if (o instanceof EsCpaRepertory) {
                esCpaRepertoryRepository.save((EsCpaRepertory) o);
            }
        }
    }

    /**
     * 接收试题变化事件
     *
     * @param event
     */
    @Subscribe
    public void insertItem(RepertoryEvent event) {
        CpaRepertory cpaRepertory = event.getGetSource();
        EsCpaRepertory esCpaRepertory = new EsCpaRepertory();
        esCpaRepertory.setId(cpaRepertory.getId());
        esCpaRepertory.setTestStem(cpaRepertory.getTestStem());
        esCpaRepertory.setInsertDate(Date.from(cpaRepertory.getInsertDate().atZone(ZoneId.systemDefault()).toInstant()));
        esCpaRepertoryRepository.save(esCpaRepertory);

    }


    /**
     * 接收用户评论变化事件
     *
     * @param event
     */
    @Subscribe
    public void insertItem(UserCommentEvent event) {
        UserComment userComment = event.getGetSource();
        EsUserComment esUserComment = new EsUserComment();
        esUserComment.setId(userComment.getId());
        esUserComment.setComment(userComment.getComment());
        esUserComment.setCommentDate(Date.from(userComment.getCommentDate().atZone(ZoneId.systemDefault()).toInstant()));
        esUserCommentRepository.save(esUserComment);

    }

}
