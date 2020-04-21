package club.banyuan.mgt.common;

import com.github.pagehelper.PageInfo;

import java.util.List;

public class ResponsePages<T> {

    /**
     * pageNum : 1
     * pageSize : 5
     * totalPage : 1
     * total : 3
     * list :
     */

    private int pageNum;
    private int pageSize;
    private int totalPage;
    private long total;
    private List<T> list;

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public static <T> ResponsePages<T> setPages(PageInfo<?> pageInfo, List<T> resultList) {
        ResponsePages<T> responsePages = new ResponsePages<>();
        responsePages.setList(resultList);
        responsePages.setPageNum(pageInfo.getPageNum());
        responsePages.setPageSize(pageInfo.getPageSize());
        responsePages.setTotal(pageInfo.getTotal());
        responsePages.setTotalPage(pageInfo.getPages());
        return responsePages;
    }
}