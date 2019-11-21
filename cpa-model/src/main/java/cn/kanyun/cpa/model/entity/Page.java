package cn.kanyun.cpa.model.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Kanyun
 */
public class Page<E> {
    /**
     * 结果集
     */
    private List<E> list;

    /**
     * 查询记录总数
     */
    private int totalRecords;

    /**
     * 每页多少条记录
     */
    private int pageSize = 20;

    /**
     * 第几页
     */
    private int pageNo = 1;

    public Page() {
    }

    public Page(int pageSize, int pageNo) {
        this.pageSize = pageSize;
        this.pageNo = pageNo;
    }

    /**
     * 构造方法,初始化pageNo、pageSize
     *
     * @param baseEntity 通过传入BaseEntity的子类,来获取页码和每页显示数量
     */
    public Page(BaseEntity baseEntity) {
        if (null != baseEntity) {
            this.pageNo = baseEntity.getPageNo() == null || baseEntity.getPageNo() == 0 ? 1 : baseEntity.getPageNo();
            this.pageSize = baseEntity.getPageSize() == null || baseEntity.getPageSize() == 0 ? 20 : baseEntity.getPageSize();
        } else {
            this.pageNo = 1;
            this.pageSize = 10;
        }
    }

    /**
     * @return 总页数
     */
    public int getTotalPages() {
        return (totalRecords + pageSize - 1) / pageSize;
    }

    /**
     * 计算当前页开始记录
     *
     * @param pageSize    每页记录数
     * @param currentPage 当前第几页
     * @return 当前页开始记录号
     */
    public int countOffset(int currentPage, int pageSize) {
        int offset = pageSize * (currentPage - 1);
        return offset;
    }

    /**
     * 计算当前页开始记录
     *
     * @return 当前页开始记录号
     */
    public int countOffset() {
        int offset = this.pageSize * (this.pageNo - 1);
        return offset;
    }

    /**
     * @return 首页
     */
    public int getTopPageNo() {
        return 1;
    }

    /**
     * @return 上一页
     */
    public int getPreviousPageNo() {
        if (pageNo <= 1) {
            return 1;
        }
        return pageNo - 1;
    }

    /**
     * @return 下一页
     */
    public int getNextPageNo() {
        if (pageNo >= getBottomPageNo()) {
            return getBottomPageNo();
        }
        return pageNo + 1;
    }

    /**
     * @return 尾页
     */
    public int getBottomPageNo() {
        return getTotalPages();
    }


    public List<E> getList() {
        return list;
    }

    public void setList(List<E> list) {
        this.list = list;
    }

    public int getTotalRecords() {
        return totalRecords;
    }

    public void setTotalRecords(int totalRecords) {
        this.totalRecords = totalRecords;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    /**
     * 计算以当前页为中心的页面列表,如"首页,23,24,25,26,27,末页"，即页数过多,不能全部在页面显示,只能显示一部分
     *
     * @param count 需要计算的列表大小,即页面展示(count+1)个页码
     * @return pageNo列表
     * 在jsp页面可以使用
     * <%
     * Page p = (Page) request.getAttribute("page");
     * List showPageNos = p.getSlider(6);
     * %>
     * page为response 返回的键值对的key,应根据实际代码来判断其取值
     * 在需要循环的页码出使用 <c:forEach items="<%=showPageNos%>" var="page">即可
     */
    public List<Integer> getSlider(int count) {
        int halfSize = count / 2;

        int startPageNo = Math.max(getPageNo() - halfSize, 1);
        int endPageNo = Math.min(startPageNo + count - 1, getTotalPages());

        if (endPageNo - startPageNo < count) {
            startPageNo = Math.max(endPageNo - count, 1);
        }

        List<Integer> showPageNums = new ArrayList();
        for (int i = startPageNo; i <= endPageNo; i++) {
            showPageNums.add(i);
        }
        return showPageNums;
    }
}
