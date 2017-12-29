package cn.kanyun.cpa.model.dto.itempool;


/**
 * CpaOption entity. @author MyEclipse Persistence Tools
 */

public class CpaOptionDto  implements java.io.Serializable {


    // Fields    

     private Long id;
     private String selectData;
     private String optionData;


    // Constructors

    /** default constructor */
    public CpaOptionDto() {
    }


    /** full constructor */
    public CpaOptionDto( String selectData, String optionData) {
        this.selectData = selectData;
        this.optionData = optionData;
    }


    // Property accessors

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSelectData() {
        return this.selectData;
    }
    
    public void setSelectData(String selectData) {
        this.selectData = selectData;
    }

    public String getOptionData() {
        return this.optionData;
    }
    
    public void setOptionData(String optionData) {
        this.optionData = optionData;
    }
   








}