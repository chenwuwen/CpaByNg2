package cn.kanyun.cpa.model.entity.user;

import java.sql.Timestamp;

/**
 * 签到记录
 */
public class Attendance implements java.io.Serializable{
    private Integer id;
    private String userName;
    private Integer userId;
    private Timestamp attendanceDate;

    public Attendance() {
    }

    public Attendance(Integer id, String userName, Integer userId, Timestamp attendanceDate) {
        this.id = id;
        this.userName = userName;
        this.userId = userId;
        this.attendanceDate = attendanceDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Timestamp getAttendanceDate() {
        return attendanceDate;
    }

    public void setAttendanceDate(Timestamp attendanceDate) {
        this.attendanceDate = attendanceDate;
    }
}
