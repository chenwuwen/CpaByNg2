package cn.kanyun.cpa.model.dto.itempool;

import cn.kanyun.cpa.model.entity.BaseEntity;
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
    private String testType;
    private String choice; //是否是多选题
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
//    下面这两个注解保证该字段返回Json是 yyyy-MM-dd HH:mm:ss 格式的字符串
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime insertDate;
    private String presult; //用户回答的答案
    private String bresult; //标准答案
    private List<CpaOptionDto> cpaOptionDtos;
    private List<String> pAnswer;
    private Long commentCount;

    public CpaRepertoryDto() {
    }

    public CpaRepertoryDto(Long id, String testStem, String testType, String choice, LocalDateTime insertDate, String presult, String bresult, List<CpaOptionDto> cpaOptionDtos, List<String> pAnswer, Long commentCount) {
        this.id = id;
        this.testStem = testStem;
        this.testType = testType;
        this.choice = choice;
        this.insertDate = insertDate;
        this.presult = presult;
        this.bresult = bresult;
        this.cpaOptionDtos = cpaOptionDtos;
        this.pAnswer = pAnswer;
        this.commentCount = commentCount;
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

    public String getTestType() {
        return testType;
    }

    public String getChoice() {
        return choice;
    }

    public void setChoice(String choice) {
        this.choice = choice;
    }

    public void setTestType(String testType) {
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