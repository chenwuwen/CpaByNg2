package cn.kanyun.cpa.model.entity.user;

import java.time.LocalDateTime;

/**
 * 签到记录
 * @author Kanyun
 */
public class Attendance implements java.io.Serializable{
    private Long id;

    /**
     * 用户昵称
     */
    private String nickName;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 签到时间
     */
    private LocalDateTime attendanceDate;

    /**
     * 签到类型：1：正常签到  0：补签
     * 数据库对应类型为 tinyint 范围：从 0 到 255 的整型数据。存储大小为 1 字节
     */
    private Integer type;

    public Attendance() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public LocalDateTime getAttendanceDate() {
        return attendanceDate;
    }

    public void setAttendanceDate(LocalDateTime attendanceDate) {
        this.attendanceDate = attendanceDate;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
