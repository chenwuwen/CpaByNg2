package cn.kanyun.cpa.elasticsearch.repository;

import cn.kanyun.cpa.elasticsearch.model.EsCpaRepertory;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * 因为文档的id是Long类型的，所以泛型的第二个参数是Long。
 * Spring Data 的强大之处，就在于你不用写任何DAO处理，自动根据方法名或类的信息进行CRUD操作。
 * 只要你定义一个接口，然后继承Repository提供的一些子接口，就能具备各种基本的CRUD功能。
 * ElasticsearchRepository 继承自 ElasticsearchCrudRepository 因此本接口就可以进行CRUD操作
 * @author Kanyun
 */
public interface EsCpaRepertoryRepository extends ElasticsearchRepository<EsCpaRepertory,Long> {
}
