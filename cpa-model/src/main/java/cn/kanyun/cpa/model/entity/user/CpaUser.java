package cn.kanyun.cpa.model.entity.user;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;


/**
 * CpaUser entity. @author MyEclipse Persistence Tools
 */

public class CpaUser implements java.io.Serializable {


    // Fields    

    private Integer id;
    private String userName;
    private String password;
    private String gender;
    private String email;
    private String petName;
    private Timestamp regDate;
    private Timestamp lastLoginDate;
    private Integer status;
    private String salt;
    private Set userRoles = new HashSet<>();

    public CpaUser(){}

    public CpaUser(Integer id, String userName, String password, String gender, String email, String petName, Timestamp regDate, Timestamp lastLoginDate, Integer status, Set userRoles) {
        this.id = id;
        this.userName = userName;
        this.password = password;
        this.gender = gender;
        this.email = email;
        this.petName = petName;
        this.regDate = regDate;
        this.lastLoginDate = lastLoginDate;
        this.status = status;
        this.userRoles = userRoles;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public String getPetName() {
        return petName;
    }

    public void setPetName(String petName) {
        this.petName = petName;
    }

    public Timestamp getRegDate() {
        return regDate;
    }

    public void setRegDate(Timestamp regDate) {
        this.regDate = regDate;
    }

    public Timestamp getLastLoginDate() {
        return lastLoginDate;
    }

    public void setLastLoginDate(Timestamp lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Set getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(Set userRoles) {
        this.userRoles = userRoles;
    }


    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }


    @Override
    public String toString() {
        return "CpaUser{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", gender='" + gender + '\'' +
                ", email='" + email + '\'' +
                ", petName='" + petName + '\'' +
                ", regDate=" + regDate +
                ", lastLoginDate=" + lastLoginDate +
                ", status=" + status +
                ", userRoles=" + userRoles +
                ", salt=" + salt +
                '}';
    }
}