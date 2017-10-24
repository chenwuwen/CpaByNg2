package cn.kanyun.cpa.model.entity.user;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by KANYUN on 2017/10/24.
 */
@Entity
@Table(name = "user_collect", schema = "cpa", catalog = "")
public class UserCollect implements Serializable {
    private int id;
    private Integer userId;
    private Integer reId;
    private Timestamp collectDate;
    private Integer status;

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
    @Column(name = "re_id", nullable = true)
    public Integer getReId() {
        return reId;
    }

    public void setReId(Integer reId) {
        this.reId = reId;
    }

    @Basic
    @Column(name = "collect_date", nullable = true)
    public Timestamp getCollectDate() {
        return collectDate;
    }

    public void setCollectDate(Timestamp collectDate) {
        this.collectDate = collectDate;
    }

    @Basic
    @Column(name = "status", nullable = true)
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserCollect that = (UserCollect) o;

        if (id != that.id) return false;
        if (userId != null ? !userId.equals(that.userId) : that.userId != null) return false;
        if (reId != null ? !reId.equals(that.reId) : that.reId != null) return false;
        if (collectDate != null ? !collectDate.equals(that.collectDate) : that.collectDate != null) return false;
        if (status != null ? !status.equals(that.status) : that.status != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (userId != null ? userId.hashCode() : 0);
        result = 31 * result + (reId != null ? reId.hashCode() : 0);
        result = 31 * result + (collectDate != null ? collectDate.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        return result;
    }
}
