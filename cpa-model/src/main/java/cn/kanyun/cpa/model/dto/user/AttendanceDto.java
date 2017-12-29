package cn.kanyun.cpa.model.dto.user;

import java.sql.Timestamp;

public class AttendanceDto implements java.io.Serializable{
    private Long id;
    private String userName;
    private Integer userId;
    private Timestamp attendanceDate;
    private Integer reapSigInDay;

    public AttendanceDto() {
    }

    public AttendanceDto(Long id, String userName, Integer userId, Timestamp attendanceDate, Integer reapSigInDay) {
        this.id = id;
        this.userName = userName;
        this.userId = userId;
        this.attendanceDate = attendanceDate;
        this.reapSigInDay = reapSigInDay;
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

    public Integer getReapSigInDay() {
        return reapSigInDay;
    }

    public void setReapSigInDay(Integer reapSigInDay) {
        this.reapSigInDay = reapSigInDay;
    }
}
