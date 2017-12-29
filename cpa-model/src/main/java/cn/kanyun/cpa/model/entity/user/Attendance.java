package cn.kanyun.cpa.model.entity.user;

import java.time.LocalDateTime;

/**
 * 签到记录
 */
public class Attendance implements java.io.Serializable{
    private Long id;
    private String userName;
    private Long userId;
    private LocalDateTime attendanceDate;

    public Attendance() {
    }

    public Attendance(Long id, String userName, Long userId, LocalDateTime attendanceDate) {
        this.id = id;
        this.userName = userName;
        this.userId = userId;
        this.attendanceDate = attendanceDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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
}
