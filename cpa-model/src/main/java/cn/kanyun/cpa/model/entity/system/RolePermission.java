package cn.kanyun.cpa.model.entity.system;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by Administrator on 2017/6/15.
 */
@Entity
@Table(name = "role_permission", schema = "cpa", catalog = "")
public class RolePermission implements java.io.Serializable{
    private int id;
    private Integer roleId;
    private Integer permissionId;
    private CpaRole cpaRole;
    private CpaPermission cpaPermission;

    public RolePermission(){}

    public RolePermission(int id, Integer roleId, Integer permissionId, CpaRole cpaRole, CpaPermission cpaPermission) {
        this.id = id;
        this.roleId = roleId;
        this.permissionId = permissionId;
        this.cpaRole = cpaRole;
        this.cpaPermission = cpaPermission;
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
    @Column(name = "role_id", nullable = true)
    public Integer getRoleId() {
        return roleId;
    }
    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    @Basic
    @Column(name = "permission_id", nullable = true)
    public Integer getPermissionId() {
        return permissionId;
    }
    public void setPermissionId(Integer permissionId) {
        this.permissionId = permissionId;
    }


    public CpaRole getCpaRole() {
        return cpaRole;
    }
    public void setCpaRole(CpaRole cpaRole) {
        this.cpaRole = cpaRole;
    }

    public CpaPermission getCpaPermission() {
        return cpaPermission;
    }
    public void setCpaPermission(CpaPermission cpaPermission) {
        this.cpaPermission = cpaPermission;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RolePermission that = (RolePermission) o;

        if (id != that.id) return false;
        if (roleId != null ? !roleId.equals(that.roleId) : that.roleId != null) return false;
        if (permissionId != null ? !permissionId.equals(that.permissionId) : that.permissionId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (roleId != null ? roleId.hashCode() : 0);
        result = 31 * result + (permissionId != null ? permissionId.hashCode() : 0);
        return result;
    }
}
