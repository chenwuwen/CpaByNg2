package cn.kanyun.cpa.model.entity.system;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Administrator on 2017/6/15.
 */
@Entity
@Table(name = "cpa_role", schema = "cpa", catalog = "")
public class CpaRole implements java.io.Serializable {
    private int id;
    private String roleName;
    private String description;
    private Set rolePermissions = new HashSet();
    private Set userRoles = new HashSet();

    public CpaRole() {}

    public CpaRole(int id, String roleName, String description, Set rolePermissions, Set userRoles) {
        this.id = id;
        this.roleName = roleName;
        this.description = description;
        this.rolePermissions = rolePermissions;
        this.userRoles = userRoles;
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
    @Column(name = "role_name", nullable = true, length = 10)
    public String getRoleName() {
        return roleName;
    }
    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    @Basic
    @Column(name = "description", nullable = true,length = 255)
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description=description;
    }

    public Set getRolePermissions() {
        return this.rolePermissions;
    }
    public void setRolePermissions(Set rolePermissions) {
        this.rolePermissions = rolePermissions;
    }

    public Set getUserRoles() {
        return this.userRoles;
    }
    public void setUserRoles(Set userRoles) {
        this.userRoles = userRoles;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CpaRole cpaRole = (CpaRole) o;

        if (id != cpaRole.id) return false;
        if (roleName != null ? !roleName.equals(cpaRole.roleName) : cpaRole.roleName != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (roleName != null ? roleName.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "CpaRole{" +
                "id=" + id +
                ", roleName='" + roleName + '\'' +
                ", description='" + description + '\'' +
                ", RolePermissions=" + rolePermissions +
                ", UserRoles=" + userRoles +
                '}';
    }
}
