package cn.kanyun.cpa.model.entity.user;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "answer_record", schema = "cpa", catalog = "")
public class AnswerRecord implements java.io.Serializable {
    private Long id;
    private Long userId;
    private String userName;
    private String nickName;
    private String itemType;
    private Integer score;
    private Integer totalCount;
    private Integer correctCount;
    private Integer errorCount;
    private LocalDateTime answerDate;

    @Id
    @Column(name = "id", nullable = false)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "user_id", nullable = true)
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "user_name", nullable = true, length = 255)
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Basic
    @Column(name = "nick_name", nullable = true, length = 255)
    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    @Basic
    @Column(name = "item_type", nullable = true, length = 255)
    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    @Basic
    @Column(name = "score", nullable = true)
    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    @Basic
    @Column(name = "total_count", nullable = true)
    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    @Basic
    @Column(name = "correct_count", nullable = true)
    public Integer getCorrectCount() {
        return correctCount;
    }

    public void setCorrectCount(Integer correctCount) {
        this.correctCount = correctCount;
    }

    @Basic
    @Column(name = "error_count", nullable = true)
    public Integer getErrorCount() {
        return errorCount;
    }

    public void setErrorcount(Integer errorCount) {
        this.errorCount = errorCount;
    }

    @Basic
    @Column(name = "answer_date", nullable = true)
    public LocalDateTime getAnswerDate() {
        return answerDate;
    }

    public void setAnswerDate(LocalDateTime answerDate) {
        this.answerDate = answerDate;
    }


}
