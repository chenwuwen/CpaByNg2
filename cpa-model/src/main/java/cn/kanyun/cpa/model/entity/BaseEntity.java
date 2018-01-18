package cn.kanyun.cpa.model.entity;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/7/11.
 */
public class BaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;

	private Integer pageNo;

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
