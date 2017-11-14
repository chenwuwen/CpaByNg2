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
public class RolePermission implements java.io.Serializable {
    private int id;
    private CpaRole cpaRole;
    private CpaPermission cpaPermission;

    public RolePermission() {
    }

    public RolePermission(int id, CpaRole cpaRole, CpaPermission cpaPermission) {
        this.id = id;
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


}
