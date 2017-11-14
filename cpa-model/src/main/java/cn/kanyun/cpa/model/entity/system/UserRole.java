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
public class UserRole implements java.io.Serializable {
    private int id;
    private CpaUser cpaUser; //user_id
    private CpaRole cpaRole; //role_id

    public UserRole() {
    }

    public UserRole(int id, CpaUser cpaUser, CpaRole cpaRole) {
        this.id = id;
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


}
