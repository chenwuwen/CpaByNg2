package cn.kanyun.cpa.model.dto.itempool;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * CpaRepertory entity. @author MyEclipse Persistence Tools
 */

public class CpaRepertoryDto  implements java.io.Serializable {


    // Fields    

     private Integer id;
     private String testStem;
     private String testType;
     private Timestamp insertDate;
     private String presult; //用户回答的答案
     private String bresult; //标准答案
     private List<CpaOptionDto> cpaOptionDtos;

     public CpaRepertoryDto(){}

     public CpaRepertoryDto(Integer id, String testStem,String presult,String bresult, String testType, Timestamp insertDate, List<CpaOptionDto> cpaOptionDtos) {
          this.id = id;
          this.testStem = testStem;
          this.testType = testType;
          this.insertDate = insertDate;
          this.presult = presult;
          this.bresult = bresult;
          this.cpaOptionDtos = cpaOptionDtos;
     }

     public Integer getId() {
          return id;
     }

     public void setId(Integer id) {
          this.id = id;
     }

     public String getTestStem() {
          return testStem;
     }

     public void setTestStem(String testStem) {
          this.testStem = testStem;
     }

     public String getTestType() {
          return testType;
     }

     public void setTestType(String testType) {
          this.testType = testType;
     }

     public Timestamp getInsertDate() {
          return insertDate;
     }

     public void setInsertDate(Timestamp insertDate) {
          this.insertDate = insertDate;
     }

     public List<CpaOptionDto> getCpaOptionDtos() {
          return cpaOptionDtos;
     }

     public void setCpaOptionDtos(List<CpaOptionDto> cpaOptionDtos) {
          this.cpaOptionDtos = cpaOptionDtos;
     }

     public String getPresult() {
          return presult;
     }

     public void setPresult(String presult) {
          this.presult = presult;
     }

     public String getBresult() {
          return bresult;
     }

     public void setBresult(String bresult) {
          this.bresult = bresult;
     }
}