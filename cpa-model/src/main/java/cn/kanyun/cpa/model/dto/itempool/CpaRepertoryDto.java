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
 * 试题库 Dto类
 *
 * @author Kanyun
 */
@Data
public class CpaRepertoryDto extends BaseEntity {


    private Long id;
    private String testStem;
    /**
     * 试题类型 枚举
     * 单选题/多选题/判断题
     */
    private QuestionTypeEnum questionType;

    /**
     * 试题分类  枚举
     * 会计电算化/会计基础
     */
    private ExamClassificationEnum testType;

    /**
     * @JsonDeserialize(using = LocalDateTimeDeserializer.class)
     * 下面这两个注解保证该字段返回Json是 yyyy-MM-dd HH:mm:ss 格式的字符串
     */
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
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