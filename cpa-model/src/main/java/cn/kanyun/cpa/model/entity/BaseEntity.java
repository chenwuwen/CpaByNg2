package cn.kanyun.cpa.model.entity;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/7/11.
 */
public class BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
