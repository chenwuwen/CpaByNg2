package cn.kanyun.cpa.model.entity.system;

import cn.kanyun.cpa.model.entity.user.CpaUser;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by Administrator on 2017/6/15.
 */
@Entity
@Table(name = "user_role", schema = "cpa", catalog = "")
public class UserRole implements java.io.Serializable{
    private int id;
    private Integer userId;
    private Integer roleId;
    private CpaUser cpaUser;
    private CpaRole cpaRole;

    public UserRole(){}

    public UserRole(int id, Integer userId, Integer roleId, CpaUser cpaUser, CpaRole cpaRole) {
        this.id = id;
        this.userId = userId;
        this.roleId = roleId;
        this.cpaUser = cpaUser;
        this.cpaRole = cpaRole;
    }
    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "user_id", nullable = true)
    public Integer getUserId() {
        return userId;
    }
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "role_id", nullable = true)
    public Integer getRoleId() {
        return roleId;
    }
    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }


    public CpaUser getCpaUser() {
        return cpaUser;
    }
    public void setCpaUser(CpaUser cpaUser) {
        this.cpaUser = cpaUser;
    }

    public CpaRole getCpaRole() {
        return cpaRole;
    }
    public void setCpaRole(CpaRole cpaRole) {
        this.cpaRole = cpaRole;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserRole userRole = (UserRole) o;

        if (id != userRole.id) return false;
        if (userId != null ? !userId.equals(userRole.userId) : userRole.userId != null) return false;
        if (roleId != null ? !roleId.equals(userRole.roleId) : userRole.roleId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (userId != null ? userId.hashCode() : 0);
        result = 31 * result + (roleId != null ? roleId.hashCode() : 0);
        return result;
    }
}
