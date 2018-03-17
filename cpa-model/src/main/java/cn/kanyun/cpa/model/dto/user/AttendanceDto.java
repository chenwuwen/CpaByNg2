package cn.kanyun.cpa.model.dto.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import java.time.LocalDateTime;

public class AttendanceDto implements java.io.Serializable{
    private Long id;
    private String userName;
    private Integer userId;
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime attendanceDate;
    private Integer reapSigInDay;

    public AttendanceDto() {
    }

    public AttendanceDto(Long id, String userName, Integer userId, LocalDateTime attendanceDate, Integer reapSigInDay) {
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

    public LocalDateTime getAttendanceDate() {
        return attendanceDate;
    }

    public void setAttendanceDate(LocalDateTime attendanceDate) {
        this.attendanceDate = attendanceDate;
    }

    public Integer getReapSigInDay() {
        return reapSigInDay;
    }

    public void setReapSigInDay(Integer reapSigInDay) {
        this.reapSigInDay = reapSigInDay;
    }
}
