package cn.kanyun.cpa.model.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 模型驱动，用于返回JSON字符串数据
 *
 * @author Kanyun
 * @param <T>
 */
@ApiModel(value = "返回数据包装类",description = "包含返回状态,返回数据,错误信息等")
public class CpaResult<T> implements java.io.Serializable {

    /**
     * 用户登陆状态 0：代表未登录，1：代表已登录(添加该值，用以返回状态让js进行一些处理如重定向到登陆页面或者注册页面等等，类似的也可以写两个controller，一个负责跳转页面（判断权限，可跳转到登陆页，也可以跳转到目的页）另外一个controller负责处理业务逻辑，但是对于单页面（SPA）来说这种模式并不适用）
     */
    @ApiModelProperty(value="用户登陆状态: 0：代表未登录，1：代表已登录")
    private Integer status;
    /**
     * 状态  0:代表未找到，1:代表成功，2:代表失败
     */
    @ApiModelProperty(value="当次请求服务返回状态：0:代表未找到，1:代表成功，2:代表失败")
    private Integer state;
    /**
     * 消息
     */
    @ApiModelProperty(value="错误信息")
    private String msg;
    /**
     * 数据
     */
    @ApiModelProperty(value="请求返回数据")
    private T data;
    /**
     * 总记录数
     */
    @ApiModelProperty(value="总记录数")
    private Long totalCount;
    /**
     * 总页数
     */
    @ApiModelProperty(value="总页数")
    private Integer totalPage;


    public CpaResult() {
    }

    public CpaResult(Integer status, Integer state, String msg, T data, Long totalCount, Integer totalPage) {
        this.status = status;
        this.state = state;
        this.msg = msg;
        this.data = data;
        this.totalCount = totalCount;
        this.totalPage = totalPage;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Long totalCount) {
        this.totalCount = totalCount;
    }

    public Integer getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }
}


