package cn.kanyun.cpa.model.entity.user;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

@Entity
@Table(name = "answer_record", schema = "cpa", catalog = "")
public class AnswerRecord {
    private int id;
    private Integer userId;
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
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "user_id", nullable = true)
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AnswerRecord that = (AnswerRecord) o;

        if (id != that.id) return false;
        if (userId != null ? !userId.equals(that.userId) : that.userId != null) return false;
        if (username != null ? !username.equals(that.username) : that.username != null) return false;
        if (petname != null ? !petname.equals(that.petname) : that.petname != null) return false;
        if (itemType != null ? !itemType.equals(that.itemType) : that.itemType != null) return false;
        if (score != null ? !score.equals(that.score) : that.score != null) return false;
        if (totalcount != null ? !totalcount.equals(that.totalcount) : that.totalcount != null) return false;
        if (correctcount != null ? !correctcount.equals(that.correctcount) : that.correctcount != null) return false;
        if (errorcount != null ? !errorcount.equals(that.errorcount) : that.errorcount != null) return false;
        if (answerDate != null ? !answerDate.equals(that.answerDate) : that.answerDate != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (userId != null ? userId.hashCode() : 0);
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + (petname != null ? petname.hashCode() : 0);
        result = 31 * result + (itemType != null ? itemType.hashCode() : 0);
        result = 31 * result + (score != null ? score.hashCode() : 0);
        result = 31 * result + (totalcount != null ? totalcount.hashCode() : 0);
        result = 31 * result + (correctcount != null ? correctcount.hashCode() : 0);
        result = 31 * result + (errorcount != null ? errorcount.hashCode() : 0);
        result = 31 * result + (answerDate != null ? answerDate.hashCode() : 0);
        return result;
    }
}
