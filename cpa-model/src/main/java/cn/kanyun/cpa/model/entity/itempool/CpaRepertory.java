package cn.kanyun.cpa.model.entity.itempool;

import cn.kanyun.cpa.model.enums.ExamClassificationEnum;
import cn.kanyun.cpa.model.enums.QuestionTypeEnum;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;


/**
 * 试题库 实体类
 *
 * @author Kanyun
 */

public class CpaRepertory implements java.io.Serializable {


    /**
     * 试题ID
     */
    private Long id;

    /**
     * 题干
     */
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
    @Enumerated(value = EnumType.STRING)
    private ExamClassificationEnum testType;

    /**
     * 试题创建时间
     */
    private LocalDateTime insertDate;
    /**
     * 试题选项
     */
    private Set<CpaOption> cpaOptions = new HashSet(0);

    /**
     * 试题答案
     */
    private CpaSolution cpaSolution;


    /**
     * default constructor
     */
    public CpaRepertory() {
    }

    public CpaRepertory(Long id, String testStem, QuestionTypeEnum questionType, ExamClassificationEnum testType, LocalDateTime insertDate, Set cpaOptions, CpaSolution cpaSolution) {
        this.id = id;
        this.testStem = testStem;
        this.questionType = questionType;
        this.testType = testType;
        this.insertDate = insertDate;
        this.cpaOptions = cpaOptions;
        this.cpaSolution = cpaSolution;
    }

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