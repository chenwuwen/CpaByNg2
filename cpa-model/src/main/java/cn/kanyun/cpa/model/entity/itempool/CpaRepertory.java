package cn.kanyun.cpa.model.entity.itempool;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;


/**
 * CpaRepertory entity. @author MyEclipse Persistence Tools
 */

public class CpaRepertory implements java.io.Serializable {


    // Fields    

    private Long id;
    private String testStem;
    private String testType;
    private String choice ; //是否是多选题(默认为单选)
    private LocalDateTime insertDate;
    private Set cpaOptions = new HashSet(0);
    private CpaSolution cpaSolution;


    // Constructors

    /**
     * default constructor
     */
    public CpaRepertory() {
    }


    /**
     * full constructor
     */
    public CpaRepertory(String testStem, String testType, String choice, LocalDateTime insertDate, Set cpaOptions, CpaSolution cpaSolution) {
        this.testStem = testStem;
        this.testType = testType;
        this.choice = choice;
        this.insertDate = insertDate;
        this.cpaOptions = cpaOptions;
        this.cpaSolution = cpaSolution;
    }


    // Property accessors

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTestStem() {
        return this.testStem;
    }

    public void setTestStem(String testStem) {
        this.testStem = testStem;
    }

    public String getTestType() {
        return this.testType;
    }

    public void setTestType(String testType) {
        this.testType = testType;
    }

    public String getChoice() {
        return choice;
    }

    public void setChoice(String choice) {
        this.choice = choice;
    }

    public LocalDateTime getInsertDate() {
        return this.insertDate;
    }

    public void setInsertDate(LocalDateTime insertDate) {
        this.insertDate = insertDate;
    }

    public Set getCpaOptions() {
        return this.cpaOptions;
    }

    public void setCpaOptions(Set cpaOptions) {
        this.cpaOptions = cpaOptions;
    }

    public CpaSolution getCpaSolution() {
        return cpaSolution;
    }

    public void setCpaSolution(CpaSolution cpaSolution) {
        this.cpaSolution = cpaSolution;
    }
}