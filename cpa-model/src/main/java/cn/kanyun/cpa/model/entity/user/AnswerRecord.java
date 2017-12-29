package cn.kanyun.cpa.model.entity.user;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "answer_record", schema = "cpa", catalog = "")
public class AnswerRecord implements java.io.Serializable {
    private Long id;
    private Long userId;
    private String username;
    private String petname;
    private String itemType;
    private Integer score;
    private Integer totalcount;
    private Integer correctcount;
    private Integer errorcount;
    private Timestamp answerDate;

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
    @Column(name = "username", nullable = true, length = 255)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Basic
    @Column(name = "petname", nullable = true, length = 255)
    public String getPetname() {
        return petname;
    }

    public void setPetname(String petname) {
        this.petname = petname;
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
    @Column(name = "totalcount", nullable = true)
    public Integer getTotalcount() {
        return totalcount;
    }

    public void setTotalcount(Integer totalcount) {
        this.totalcount = totalcount;
    }

    @Basic
    @Column(name = "correctcount", nullable = true)
    public Integer getCorrectcount() {
        return correctcount;
    }

    public void setCorrectcount(Integer correctcount) {
        this.correctcount = correctcount;
    }

    @Basic
    @Column(name = "errorcount", nullable = true)
    public Integer getErrorcount() {
        return errorcount;
    }

    public void setErrorcount(Integer errorcount) {
        this.errorcount = errorcount;
    }

    @Basic
    @Column(name = "answer_date", nullable = true)
    public Timestamp getAnswerDate() {
        return answerDate;
    }

    public void setAnswerDate(Timestamp answerDate) {
        this.answerDate = answerDate;
    }


}
