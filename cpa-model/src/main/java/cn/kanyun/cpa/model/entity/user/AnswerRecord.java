package cn.kanyun.cpa.model.entity.user;

import cn.kanyun.cpa.model.enums.ExamClassificationEnum;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "ANSWER_RECORD", schema = "cpa", catalog = "")
public class AnswerRecord implements java.io.Serializable {
    private Long id;
    private Long userId;
    private String userName;
    private String nickName;
    private ExamClassificationEnum testType;
    private Integer score;
    private Integer totalCount;
    private Integer correctCount;
    private Integer errorCount;
    private LocalDateTime answerDate;

    public AnswerRecord() {
    }

    @Id
    @Column(name = "ID", nullable = false)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "USER_ID", nullable = true)
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "USER_NAME", nullable = true, length = 255)
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Basic
    @Column(name = "NICK_NAME", nullable = true, length = 255)
    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    @Basic
    @Column(name = "TEST_TYPE", nullable = true, length = 255)
    public ExamClassificationEnum getTestType() {
        return testType;
    }

    public void setTestType(ExamClassificationEnum testType) {
        this.testType = testType;
    }

    @Basic
    @Column(name = "SCORE", nullable = true)
    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    @Basic
    @Column(name = "TOTAL_COUNT", nullable = true)
    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    @Basic
    @Column(name = "CORRECT_COUNT", nullable = true)
    public Integer getCorrectCount() {
        return correctCount;
    }

    public void setCorrectCount(Integer correctCount) {
        this.correctCount = correctCount;
    }

    @Basic
    @Column(name = "ERROR_COUNT", nullable = true)
    public Integer getErrorCount() {
        return errorCount;
    }

    public void setErrorCount(Integer errorCount) {
        this.errorCount = errorCount;
    }

    @Basic
    @Column(name = "ANSWER_DATE", nullable = true)
    public LocalDateTime getAnswerDate() {
        return answerDate;
    }

    public void setAnswerDate(LocalDateTime answerDate) {
        this.answerDate = answerDate;
    }


}
