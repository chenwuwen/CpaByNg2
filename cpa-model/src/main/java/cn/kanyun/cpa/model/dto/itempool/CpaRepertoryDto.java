package cn.kanyun.cpa.model.dto.itempool;

import cn.kanyun.cpa.model.entity.BaseEntity;
import cn.kanyun.cpa.model.enums.ExamClassificationEnum;
import cn.kanyun.cpa.model.enums.QuestionTypeEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;


/**
 * CpaRepertory entity. @author MyEclipse Persistence Tools
 */
@Data
public class CpaRepertoryDto extends BaseEntity {


    // Fields    

    private Long id;
    private String testStem;
    private QuestionTypeEnum questionType;
    private ExamClassificationEnum testType;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
//    下面这两个注解保证该字段返回Json是 yyyy-MM-dd HH:mm:ss 格式的字符串
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime insertDate;

    /**
     * 标准答案
     */
    private String standardResult;
    private List<CpaOptionDto> cpaOptionDtos;
    /**
     * 用户回答的答案
     */
    private List<String> userResult;
    private Long commentCount;



}