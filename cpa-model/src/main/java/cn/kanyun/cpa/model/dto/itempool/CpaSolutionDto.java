package cn.kanyun.cpa.model.dto.itempool;


import lombok.Data;

/**
 * @author Kanyun
 */
@Data
public class CpaSolutionDto implements java.io.Serializable {


    private Long id;

    /**
     * 题干ID
     */
    private Integer examId;

    /**
     * 答案
     */
    private String result;


}