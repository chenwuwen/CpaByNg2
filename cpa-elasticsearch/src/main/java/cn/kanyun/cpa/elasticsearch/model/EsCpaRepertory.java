package cn.kanyun.cpa.elasticsearch.model;

import cn.kanyun.cpa.model.entity.itempool.CpaOption;
import cn.kanyun.cpa.model.entity.itempool.CpaSolution;
import cn.kanyun.cpa.model.enums.ExamClassificationEnum;
import cn.kanyun.cpa.model.enums.QuestionTypeEnum;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;


/**
 * Elasticsearch 题库实体类
 *
 * @author Kanyun
 * @Document 作用在类，标记实体类为文档对象，一般有两个属性
 * indexName：对应索引库名称
 * type：对应在索引库中的类型
 * shards：分片数量，默认5
 * replicas：副本数量，默认1
 */
@Document(indexName = "repertory", type = "note")
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
     * index：是否索引，布尔类型，默认是true
     * store：是否存储，布尔类型，默认是false
     * analyzer：分词器名称，这里的ik_max_word即使用ik分词器
     */
    @Field(type = FieldType.Text, store = true)
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
     */
//    @Field(type = FieldType.Date, index = false, store = true)
    private LocalDateTime insertDate;
    /**
     * 试题选项
     */
    private Set<CpaOption> cpaOptions = new HashSet(0);

    /**
     * 试题答案
     */
    private CpaSolution cpaSolution;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTestStem() {
        return testStem;
    }

    public void setTestStem(String testStem) {
        this.testStem = testStem;
    }

    public QuestionTypeEnum getQuestionType() {
        return questionType;
    }

    public void setQuestionType(QuestionTypeEnum questionType) {
        this.questionType = questionType;
    }

    public ExamClassificationEnum getTestType() {
        return testType;
    }

    public void setTestType(ExamClassificationEnum testType) {
        this.testType = testType;
    }

    public LocalDateTime getInsertDate() {
        return insertDate;
    }

    public void setInsertDate(LocalDateTime insertDate) {
        this.insertDate = insertDate;
    }

    public Set<CpaOption> getCpaOptions() {
        return cpaOptions;
    }

    public void setCpaOptions(Set<CpaOption> cpaOptions) {
        this.cpaOptions = cpaOptions;
    }

    public CpaSolution getCpaSolution() {
        return cpaSolution;
    }

    public void setCpaSolution(CpaSolution cpaSolution) {
        this.cpaSolution = cpaSolution;
    }
}