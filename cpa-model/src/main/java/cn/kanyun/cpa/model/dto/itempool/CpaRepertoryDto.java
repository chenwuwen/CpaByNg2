package cn.kanyun.cpa.model.dto.itempool;

import cn.kanyun.cpa.model.entity.BaseEntity;
import cn.kanyun.cpa.model.enums.ExamClassificationEnum;
import cn.kanyun.cpa.model.enums.QuestionTypeEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;


/**
 * CpaRepertory entity. @author MyEclipse Persistence Tools
 */

public class CpaRepertoryDto extends BaseEntity {


    // Fields    

    private Long id;
    private String testStem;
    private QuestionTypeEnum questionType;
    private ExamClassificationEnum testType;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
//    下面这两个注解保证该字段返回Json是 yyyy-MM-dd HH:mm:ss 格式的字符串
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime insertDate;
    /**
     * 用户回答的答案
     */
    private String presult;
    /**
     * 标准答案
     */
    private String bresult;
    private List<CpaOptionDto> cpaOptionDtos;
    private List<String> pAnswer;
    private Long commentCount;

    public CpaRepertoryDto() {
    }

    public CpaRepertoryDto(Long id, String testStem, QuestionTypeEnum questionType, ExamClassificationEnum testType, LocalDateTime insertDate, String presult, String bresult, List<CpaOptionDto> cpaOptionDtos, List<String> pAnswer, Long commentCount) {
        this.id = id;
        this.testStem = testStem;
        this.questionType = questionType;
        this.testType = testType;
        this.insertDate = insertDate;
        this.presult = presult;
        this.bresult = bresult;
        this.cpaOptionDtos = cpaOptionDtos;
        this.pAnswer = pAnswer;
        this.commentCount = commentCount;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String getTestStem() {
        return testStem;
    }

    public void setTestStem(String testStem) {
        this.testStem = testStem;
    }

    public String getQuestionType() {
        return questionType.name();
    }

    public void setQuestionType(QuestionTypeEnum questionType) {
        this.questionType = questionType;
    }

    public String getTestType() {
        return testType.name();
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

    public String getPresult() {
        return presult;
    }

    public void setPresult(String presult) {
        this.presult = presult;
    }

    public String getBresult() {
        return bresult;
    }

    public void setBresult(String bresult) {
        this.bresult = bresult;
    }

    public List<CpaOptionDto> getCpaOptionDtos() {
        return cpaOptionDtos;
    }

    public void setCpaOptionDtos(List<CpaOptionDto> cpaOptionDtos) {
        this.cpaOptionDtos = cpaOptionDtos;
    }

    public List<String> getpAnswer() {
        return pAnswer;
    }

    public void setpAnswer(List<String> pAnswer) {
        this.pAnswer = pAnswer;
    }

    public Long getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(Long commentCount) {
        this.commentCount = commentCount;
    }
}