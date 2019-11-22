package cn.kanyun.cpa.elasticsearch.repository;

import cn.kanyun.cpa.elasticsearch.model.EsUserComment;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * 因为文档的id是Long类型的，所以泛型的第二个参数是Long。
 * Spring Data 的强大之处，就在于你不用写任何DAO处理，自动根据方法名或类的信息进行CRUD操作。
 * 只要你定义一个接口，然后继承Repository提供的一些子接口，就能具备各种基本的CRUD功能。
 * ElasticsearchRepository 继承自 ElasticsearchCrudRepository 因此本接口就可以进行CRUD操作(需要在xml中配置扫描包)
 * 简单的查询和条件查询可以直接使用ElasticsearchRepository提供的接口，如果需要复杂的条件组合（模糊查询，完全匹配查 询，分页，排序）使用ElasticSearchTemplate实例
 * @author Kanyun
 */
public interface EsUserCommentRepository extends ElasticsearchRepository<EsUserComment,Long> {
}
