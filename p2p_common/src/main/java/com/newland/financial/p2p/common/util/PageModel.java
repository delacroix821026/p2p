package com.newland.financial.p2p.common.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 翻页模板工具类.
 */
public final class PageModel implements Serializable {
    /**
     * 默认的序列化版本 id.
     */
    private static final long serialVersionUID = 1L;
    /**
     * 当前页.
     */
    private int currentPage = 1;
    /**
     * 每页显示条数.
     */
    private int pageSize = 10;
    /**
     * 总页数.
     */
    private int totalPage;
    /**
     * 总记录数.
     */
    private int totalRecord;
    /**
     * 分页数据.
     */
    private List list = new ArrayList();
    /***/
    private String pageStr;
    /**
     * true:需要分页的地方，传入的参数就是Page实体；false:需要分页的地方，传入的参数所代表的实体拥有Page属性.
     */
    private boolean entityOrField = true;

    /**
     * 构造器.
     */
    public PageModel() {
    }

    /**
     * 带currentPage的构造器.
     *
     * @param crt Integer
     */
    public PageModel(final Integer crt) {
        this.currentPage = crt;
    }

    /**
     * 初始化PageModel实例.
     *
     * @param ps   int
     * @param page String
     */
    private PageModel(final int ps, final String page) {
        // 初始化每页显示条数
        this.pageSize = ps;
        // 初始化总页数
        setTotalPage();
        // 初始化当前页
        setCurrentPage();
    }

    /**
     * 初始化PageModel实例.
     * @param ps int
     * @param tr int
     * @param page String
     * */
    private PageModel(final int ps, final String page,
                      final int tr) {
        // 初始化每页显示条数
        this.pageSize = ps;
        // 设置总记录数
        this.totalRecord = tr;
        // 初始化总页数
        setTotalPage();
        // 初始化当前页
        setCurrentPage();

    }

    /**
     * 外界获得PageModel实例.
     * @param pageSize int
     * @param page String
     * @param totalRecord int
     * @return PageModel
     * */
    public static PageModel newPageModel(final int pageSize, final String page,
                                         final int totalRecord) {

        return new PageModel(pageSize, page, totalRecord);
    }

    /**
     * 设置当前请求页.
     * */
    private void setCurrentPage() {

        // 如果当前页小于第一页时，当前页指定到首页
        if (currentPage < 1) {

            currentPage = 1;
        }

        if (currentPage > totalPage) {

            currentPage = totalPage;

        }

    }
    /**
     * 设置总页数.
     * */
    private void setTotalPage() {
        if (totalRecord % pageSize == 0) {

            totalPage = totalRecord / pageSize;
        } else {
            totalPage = totalRecord / pageSize + 1;
        }
    }

    /**
     * 获得当前页.
     * @return int
     * */
    public int getCurrentPage() {
        return currentPage;
    }

    /**
     * 获得总页数.
     * @return int
     * */
    public int getTotalPage() {
        return totalPage;

    }

    /**
     * 获得开始行数.
     * @return int
     * */
    public int getStartRow() {
        return (currentPage - 1) * pageSize;
    }

    /**
     * 获得结束行.
     * @return int
     * */
    public int getEndRow() {
        return currentPage * pageSize;
    }

    /**
     * 首页.
     * @return int
     * */
    public int getFirst() {

        return 1;
    }
    /**
     * 上一页.
     * @return int
     * */
    public int getPrevious() {

        return currentPage - 1;
    }
    /**
     * 下一页.
     * @return int
     * */
    public int getNext() {

        return currentPage + 1;
    }
    /**
     * 尾页.
     * @return int
     * */
    public int getLast() {

        return totalPage;
    }
    /**
     * 总记录数.
     * @return int
     * */
    public int getTotalRecord() {
        return totalRecord;
    }
    /**
     * 设置总记录数.
     * @param tr int
     * */
    public void setTotalRecord(final int tr) {
        this.totalRecord = tr;
        // 初始化总页数
        setTotalPage();
        // 初始化当前页
        setCurrentPage();
    }
    /**
     * @return String
     * */
    public String getPageStr() {
        return pageStr;
    }
    /**
     * @param ps String
     * */
    public void setPageStr(final String ps) {
        this.pageStr = pageStr;
    }
    /**
     * @return int
     * */
    public int getPageSize() {
        return pageSize;
    }
    /**
     * @param ps int
     * */
    public void setPageSize(final int ps) {
        this.pageSize = pageSize;
    }
    /**
     * @return List
     * */
    public List getList() {
        return list;
    }
    /**
     * @param lst List
     * */
    public void setList(final List lst) {
        this.list = lst;
    }

}
