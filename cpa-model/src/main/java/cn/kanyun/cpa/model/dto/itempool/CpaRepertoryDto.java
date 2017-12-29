package cn.kanyun.cpa.model.dto.itempool;

import java.sql.Timestamp;
import java.util.List;


/**
 * CpaRepertory entity. @author MyEclipse Persistence Tools
 */

public class CpaRepertoryDto implements java.io.Serializable {


    // Fields    

    private Long id;
    private String testStem;
    private String testType;
    private String choice; //是否是多选题
    private Timestamp insertDate;
    private String presult; //用户回答的答案
    private String bresult; //标准答案
    private List<CpaOptionDto> cpaOptionDtos;
    private List<String> pAnswer;
    private String typeCode;
    private Long commentCount;

    public CpaRepertoryDto() {
    }

    public CpaRepertoryDto(Long id, String testStem, String testType, String choice, Timestamp insertDate, String presult, String bresult, List<CpaOptionDto> cpaOptionDtos, List<String> pAnswer, String typeCode,Long commentCount) {
        this.id = id;
        this.testStem = testStem;
        this.testType = testType;
        this.choice = choice;
        this.insertDate = insertDate;
        this.presult = presult;
        this.bresult = bresult;
        this.cpaOptionDtos = cpaOptionDtos;
        this.pAnswer = pAnswer;
        this.typeCode = typeCode;
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

    public Timestamp getInsertDate() {
        return insertDate;
    }

    public void setInsertDate(Timestamp insertDate) {
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

    public String getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }

    public Long getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(Long commentCount) {
        this.commentCount = commentCount;
    }
}