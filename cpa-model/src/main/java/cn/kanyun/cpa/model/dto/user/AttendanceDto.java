package cn.kanyun.cpa.model.dto.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * @author Kanyun
 */
@Getter
@Setter
public class AttendanceDto implements java.io.Serializable{
    private Long id;
    private String userName;
    private Integer userId;
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime attendanceDate;

    /**
     * 连续打卡天数
     */
    private Integer seriesSigInDay;

    public AttendanceDto() {
    }


}
