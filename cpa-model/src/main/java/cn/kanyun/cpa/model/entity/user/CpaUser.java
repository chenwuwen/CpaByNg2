package cn.kanyun.cpa.model.entity.user;

import cn.kanyun.cpa.model.entity.system.UserRole;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by HLWK-06 on 2019/5/27.
 */
public class CpaUser implements Serializable{
    /**
     * 用户ID
     */
    private Long id;
    /**
     * 用户名
     */
    private String userName;
    /**
     * 密码
     */
    private String password;
    /**
     * 性别 0：女，1：男
     */
    private Byte gender;
    /**
     * 邮箱地址
     */
    private String email;
    /**
     * 昵称
     */
    private String nickName;
    /**
     * 注册时间
     */
    private LocalDateTime regDate;
    /**
     * 上次登录时间
     */
    private LocalDateTime lastLoginDate;
    /**
     * 用户状态
     * 0:已删除，1:正常，2:锁定
     */
    private Integer status;
    /**
     * 盐(加密密码)
     */
    private String salt;
    /**
     * 角色集合
     */
    private Set<UserRole> userRoles = new HashSet();
    /**
     * 用户头像路径
     */
    private String imgPath;
    /**
     * 用户扩展表
     */
    private CpaUserExtend cpaUserExtend;


    public CpaUser() {
    }

    public CpaUser(Long id, String userName, String password, Byte gender, String email, String nickName, LocalDateTime regDate, LocalDateTime lastLoginDate, Integer status, String salt, Set<UserRole> userRoles, String imgPath, CpaUserExtend cpaUserExtend) {
        this.id = id;
        this.userName = userName;
        this.password = password;
        this.gender = gender;
        this.email = email;
        this.nickName = nickName;
        this.regDate = regDate;
        this.lastLoginDate = lastLoginDate;
        this.status = status;
        this.salt = salt;
        this.userRoles = userRoles;
        this.imgPath = imgPath;
        this.cpaUserExtend = cpaUserExtend;
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

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public Set<UserRole> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(Set<UserRole> userRoles) {
        this.userRoles = userRoles;
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
