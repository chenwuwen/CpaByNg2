package cn.kanyun.cpa.model.dto.user;


import cn.kanyun.cpa.model.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Set;

/**
 * Created by Administrator on 2017/7/11.
 */
public class CpaUserDto extends BaseEntity {
    /**
     * Java序列化是将一个对象编码成一个字节流，反序列化将字节流编码转换成一个对象。 序列化是Java中实现持久化存储的一种方法；为数据传输提供了线路级对象表示法。
     * Java的序列化机制是通过在运行时判断类的serialVersionUID来验证版本一致性的。在进行反序列化时，JVM会把传来的字节流中的serialVersionUID与本地相应实体（类）的serialVersionUID进行比较，如果相同就认为是一致的，可以进行反序列化，否则就会出现序列化版本不一致的异常。
     * serialVersionUID 用来表明类的不同版本间的兼容性。有两种生成方式： 一个是默认的1L；另一种是根据类名、接口名、成员方法及属性等来生成一个64位的哈希字段
     */
    /**
     * java类中为什么需要重载 serialVersionUID 属性(或者说固定序列化的版本)。
     * 1）在某些场合，希望类的不同版本对序列化兼容，因此需要确保类的不同版本具有相同的serialVersionUID；在某些场合，不希望类的不同版本对序列化兼容，因此需要确保类的不同版本具有不同的serialVersionUID。
     * 2）当你序列化了一个类实例后，希望更改一个字段或添加一个字段，不设置serialVersionUID，所做的任何更改都将导致无法反序化旧有实例，并在反序列化时抛出一个异常（如在处理activiti工作流设置获取流程变量不设置固定序列化版本会抛出异常）。如果你添加了serialVersionUID，在反序列旧有实例时，新添加或更改的字段值将设为初始化值（对象为null，基本类型为相应的初始默认值），字段被删除将不设置。
     */
    private static final long serialVersionUID = 7991499430235800383L;
    private String userName;
    private String password;
    private String email;
    private String validateCode; //验证码
    private String isRememberMe;
    private String salt; //盐
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime startRegisterDate; //注册开始时间
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime endRegisterDate;  //注册结束时间
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime regDate;  //注册时间
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime startLastLoginDate; //上次登录开始时间
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime endLastLoginDate;  //上次登录结束时间
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime lastLoginDate;  //上次登录时间
    private Set<String> roles; //角色集合
    private Set<String> permissions; //权限集合
    private String imgPath;
    private String token;
    private Integer status;
    private Byte gender;  //性别 0：女，1：男


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

    public LocalDateTime getStartRegisterDate() {
        return startRegisterDate;
    }

    public void setStartRegisterDate(LocalDateTime startRegisterDate) {
        this.startRegisterDate = startRegisterDate;
    }

    public LocalDateTime getEndRegisterDate() {
        return endRegisterDate;
    }

    public void setEndRegisterDate(LocalDateTime endRegisterDate) {
        this.endRegisterDate = endRegisterDate;
    }

    public LocalDateTime getStartLastLoginDate() {
        return startLastLoginDate;
    }

    public void setStartLastLoginDate(LocalDateTime startLastLoginDate) {
        this.startLastLoginDate = startLastLoginDate;
    }

    public LocalDateTime getEndLastLoginDate() {
        return endLastLoginDate;
    }

    public void setEndLastLoginDate(LocalDateTime endLastLoginDate) {
        this.endLastLoginDate = endLastLoginDate;
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

    public Byte getGender() {
        return gender;
    }

    public void setGender(Byte gender) {
        this.gender = gender;
    }
}
