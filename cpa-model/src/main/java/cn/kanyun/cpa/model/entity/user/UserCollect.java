package cn.kanyun.cpa.model.entity.user;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Created by KANYUN on 2017/10/24.
 */
@Entity
@Table(name = "USER_COLLECT", schema = "cpa", catalog = "")
public class UserCollect implements java.io.Serializable {
    private Long id;
    private Long userId;
    private String userName;
    private String nickName;
    private Long reId;
    private LocalDateTime collectDate;
    private Integer status;

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
    @Column(name = "RE_ID", nullable = true)
    public Long getReId() {
        return reId;
    }

    public void setReId(Long reId) {
        this.reId = reId;
    }

    @Basic
    @Column(name = "COLLECT_DATE", nullable = true)
    public LocalDateTime getCollectDate() {
        return collectDate;
    }

    public void setCollectDate(LocalDateTime collectDate) {
        this.collectDate = collectDate;
    }

    @Basic
    @Column(name = "STATUS", nullable = true)
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }


}
