package cn.kanyun.cpa.model.entity.itempool;

import cn.kanyun.cpa.model.enums.ExamClassificationEnum;
import cn.kanyun.cpa.model.enums.QuestionTypeEnum;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;


/**
 * CpaRepertory entity. @author MyEclipse Persistence Tools
 */

public class CpaRepertory implements java.io.Serializable {


    // Fields    

    private Long id;
    private String testStem;

    /**
     * 使用枚举
     * 题型：unique:单选题,multi:多选题,judge判断题
     */
    private QuestionTypeEnum questionType ;

    /**
     * 试题分类
     * BASIC_ACCOUNT：会计基础, CPU_ACCOUNT：会计电算化, STATUTE_ETHICS：财经法规与职业道德;
     */
    private ExamClassificationEnum testType ;

    private LocalDateTime insertDate;
    private Set cpaOptions = new HashSet(0);
    private CpaSolution cpaSolution;


    // Constructors

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

    public Set getCpaOptions() {
        return cpaOptions;
    }

    public void setCpaOptions(Set cpaOptions) {
        this.cpaOptions = cpaOptions;
    }

    public CpaSolution getCpaSolution() {
        return cpaSolution;
    }

    public void setCpaSolution(CpaSolution cpaSolution) {
        this.cpaSolution = cpaSolution;
    }
}