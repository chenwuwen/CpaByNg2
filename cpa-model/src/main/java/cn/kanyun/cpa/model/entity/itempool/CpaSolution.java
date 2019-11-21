package cn.kanyun.cpa.model.entity.itempool;



/**
 * 试题答案 实体类
 * @author Kanyun
 */

public class CpaSolution  implements java.io.Serializable {


    // Fields    

     private Long id;
     private CpaRepertory cpaRepertory;
     private String result;


    // Constructors

    /** default constructor */
    public CpaSolution() {
    }

    
    /** full constructor */
    public CpaSolution(CpaRepertory cpaRepertory, String result) {
        this.cpaRepertory = cpaRepertory;
        this.result = result;
    }

   
    // Property accessors

    public Long getId() {
        return this.id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }

    public CpaRepertory getCpaRepertory() {
        return this.cpaRepertory;
    }
    
    public void setCpaRepertory(CpaRepertory cpaRepertory) {
        this.cpaRepertory = cpaRepertory;
    }

    public String getResult() {
        return this.result;
    }
    
    public void setResult(String result) {
        this.result = result;
    }
   








}