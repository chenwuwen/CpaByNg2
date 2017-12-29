package cn.kanyun.cpa.model.entity.user;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Created by KANYUN on 2017/10/24.
 */
@Entity
@Table(name = "user_collect", schema = "cpa", catalog = "")
public class UserCollect implements java.io.Serializable {
    private Long id;
    private Long userId;
    private String username;
    private String petname;
    private Long reId;
    private LocalDateTime collectDate;
    private Integer status;

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
    @Column(name = "re_id", nullable = true)
    public Long getReId() {
        return reId;
    }

    public void setReId(Long reId) {
        this.reId = reId;
    }

    @Basic
    @Column(name = "collect_date", nullable = true)
    public LocalDateTime getCollectDate() {
        return collectDate;
    }

    public void setCollectDate(LocalDateTime collectDate) {
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


}
