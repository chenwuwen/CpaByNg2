package cn.kanyun.cpa.model.dto.itempool;


/**
 * CpaSolution entity. @author MyEclipse Persistence Tools
 */

public class CpaSolutionDto  implements java.io.Serializable {


    // Fields    

     private Long id;
     private Integer stemId; //题干ID
     private String result; //答案

    public CpaSolutionDto(){}

    public CpaSolutionDto(Long id, Integer stemId, String result) {
        this.id = id;
        this.stemId = stemId;
        this.result = result;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getStemId() {
        return stemId;
    }

    public void setStemId(Integer stemId) {
        this.stemId = stemId;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}