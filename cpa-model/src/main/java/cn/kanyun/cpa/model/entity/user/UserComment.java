package cn.kanyun.cpa.model.entity.user;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 *
 * @author KANYUN
 * @date 2017/10/24
 */
@Entity
@Table(name = "USER_COMMENT", schema = "cpa", catalog = "")
public class UserComment implements java.io.Serializable {
    private Long id;
    private Long userId;
    private String userName;
    private String nickName;
    private Long reId;
    private LocalDateTime commentDate;
    private String comment;

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

    public void setUserName(String username) {
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
    @Column(name = "COMMENT_DATE", nullable = true)
    public LocalDateTime getCommentDate() {
        return commentDate;
    }

    public void setCommentDate(LocalDateTime commentDate) {
        this.commentDate = commentDate;
    }

    @Basic
    @Column(name = "COMMENT", nullable = true, length = 255)
    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

}
