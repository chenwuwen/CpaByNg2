package cn.kanyun.cpa.elasticsearch.model;

import cn.kanyun.cpa.model.entity.itempool.CpaOption;
import cn.kanyun.cpa.model.entity.itempool.CpaSolution;
import cn.kanyun.cpa.model.enums.ExamClassificationEnum;
import cn.kanyun.cpa.model.enums.QuestionTypeEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Set;


/**
 * Elasticsearch 题库实体类
 *
 * @author Kanyun
 * @Document 作用在类，标记实体类为文档对象，一般有两个属性
 * indexName：对应索引库名称 ->建议以项目名称命名
 * type：对应在索引库中的类型 ->建议以实体类名称命名
 * shards：分片数量，默认5
 * replicas：副本数量，默认1
 * refreshInterval: 刷新间隔
 * indexStoreType: 索引文件存储类型
 * 同时添加@Document注解，项目启动会在ES中自动创建index与type
 */
@Data
@Document(indexName = "cpa", type = "repertory")
public class EsCpaRepertory implements java.io.Serializable {
    /**
     * 试题ID
     *
     * @Id 作用在成员变量，标记一个字段作为id主键
     */
    @Id
    private Long id;

    /**
     * 题干
     *
     * @Field 作用在成员变量，标记为文档的字段，并指定字段映射属性：
     * type：字段类型，是枚举：FieldType，可以是text、long、short、date、integer、object等
     * text：存储数据时候，会自动分词，并生成索引
     * keyword：存储数据时候，不会分词建立索引
     * Numerical：数值类型，分两类
     * 基本数据类型：long、integer、short、byte、double、float、half_float
     * 浮点数的高精度类型：scaled_float
     * 需要指定一个精度因子，比如10或100。elasticsearch会把真实值乘以这个因子后存储，取出时再还原。
     * Date：日期类型
     * elasticsearch可以对日期格式化为字符串存储，但是建议我们存储为毫秒值，存储为long，节省空间。
     * index：是否索引，布尔类型，默认是true 默认情况下分词，一般默认分词就好，除非这个字段你确定查询时不会用到
     * store：是否存储，布尔类型，默认是false
     * analyzer：指定字段建立索引时指定的分词器，这里的ik_max_word即使用ik分词器
     * searchAnalyzer: 指定字段搜索时使用的分词器
     * IK分词器有两种分词模式(前提是ES服务安装了ik分词器)：ik_max_word和ik_smart模式.
     * ik_max_word 会将文本做最细粒度的拆分  ik_smart 会做最粗粒度的拆分
     * 两种分词器使用的最佳实践是：索引时用ik_max_word，在搜索时用ik_smart。即：索引时最大化的将文章内容分词，搜索时更精确的搜索到想要的结果
     * format:时间类型的格式化
     */
    @Field(type = FieldType.Text, store = true, analyzer = "ik_max_word", searchAnalyzer = "ik_smart")
    private String testStem;

    /**
     * 使用枚举
     * 题型：unique:单选题,multi:多选题,judge判断题
     */
    private QuestionTypeEnum questionType;

    /**
     * 试题分类
     * BASIC_ACCOUNT：会计基础, CPU_ACCOUNT：会计电算化, STATUTE_ETHICS：财经法规与职业道德;
     */
    private ExamClassificationEnum testType;

    /**
     * 试题创建时间
     * 时间转换,否则无法插入到ES中,最好的方式是存储long类型的时间戳
     */
    @Field(type = FieldType.Date, format = DateFormat.custom, pattern = "yyyy-MM-dd HH:mm:ss", index = false, store = true)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern ="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date insertDate;
    /**
     * 试题选项
     */
    private Set<CpaOption> cpaOptions;

    /**
     * 试题答案
     */
    private CpaSolution cpaSolution;


}