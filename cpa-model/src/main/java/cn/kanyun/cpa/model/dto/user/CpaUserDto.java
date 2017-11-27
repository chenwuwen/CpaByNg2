package cn.kanyun.cpa.model.dto.user;


import cn.kanyun.cpa.model.entity.BaseEntity;

import java.util.Set;

/**
 * Created by Administrator on 2017/7/11.
 */
public class CpaUserDto extends BaseEntity {

    private String userName;
    private String password;
    private String email;
    private String validateCode; //验证码
    private String isRememberMe;
    private String salt; //盐
    private Set<String> roles; //角色集合
    private Set<String> permissions; //权限集合
    private String imgPath;
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getIsRememberMe() {
        return isRememberMe;
    }

    public void setIsRememberMe(String isRememberMe) {
        this.isRememberMe = isRememberMe;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getValidateCode() {
        return validateCode;
    }

    public void setValidateCode(String validateCode) {
        this.validateCode = validateCode;
    }


    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }

    public Set<String> getPermissions() {
        return permissions;
    }

    public void setPermissions(Set<String> permissions) {
        this.permissions = permissions;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }
}
