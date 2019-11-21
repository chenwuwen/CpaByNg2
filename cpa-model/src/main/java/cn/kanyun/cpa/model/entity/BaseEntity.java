package cn.kanyun.cpa.model.entity;

import java.io.Serializable;

/**
 * @author Kanyun
 * @date 2017/7/11
 */
public class BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 页码
     */
    private Integer pageNo;

    /**
     * 每页显示数量
     */
    private Integer pageSize;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
