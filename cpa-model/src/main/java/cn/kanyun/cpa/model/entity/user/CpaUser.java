package cn.kanyun.cpa.model.entity.user;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;


/**
 * CpaUser entity. @author MyEclipse Persistence Tools
 */

public class CpaUser implements java.io.Serializable {


    // Fields    

    private Long id;
    private String userName;
    private String password;
    private Byte gender;  //性别 0：女，1：男
    private String email;
    private String nickName;
    private LocalDateTime regDate;
    private LocalDateTime lastLoginDate;
    private Integer status;  //0:已删除，1:正常，2:锁定
    private String salt;
    private Set userRoles = new HashSet<>();
    private String imgPath;
    private CpaUserExtend cpaUserExtend;

    public CpaUser() {
    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public Byte getGender() {
        return gender;
    }

    public void setGender(Byte gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public LocalDateTime getRegDate() {
        return regDate;
    }

    public void setRegDate(LocalDateTime regDate) {
        this.regDate = regDate;
    }

    public LocalDateTime getLastLoginDate() {
        return lastLoginDate;
    }

    public void setLastLoginDate(LocalDateTime lastLoginDate) {
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

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public CpaUserExtend getCpaUserExtend() {
        return cpaUserExtend;
    }

    public void setCpaUserExtend(CpaUserExtend cpaUserExtend) {
        this.cpaUserExtend = cpaUserExtend;
    }
}