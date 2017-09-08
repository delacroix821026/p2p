package com.newland.financial.p2p.common.util;

import java.util.List;
/**
 * 分页模板.
 * @author xiazunhua
 * */
public class PageInfo {
    /**
     * 当前页.
     * */
    private int currentPage;
    /**
     * 每页显示条数.
     * */
    private int pageSize = 3;
    /**
     * 总页数.
     * */
    private int totlePage;
    /**
     * 总记录数.
     * */
    private int totalRecord;
    /**
     * 查询的数据集合.
     * */
    private List list;
    /**
     * 无参构造.
     * */
    public PageInfo() { }
    /**
     *@param crt int
     *@param tlp int
     *@param tr int
     *@param lst List
     * */
    public PageInfo(final int crt, final int tlp,
                    final int tr, final List lst) {
        this.currentPage = crt;
        this.totlePage = tlp;
        this.totalRecord = tr;
        this.list = lst;
    }

    /**
     * 获取当前页.
     * @return currentPage 返回当前页
     * */
    public final int getCurrentPage() {
        return currentPage;
    }

    /**
     * 设置当前页.
     * @param crt int
     * */
    public final void setCurrentPage(final int crt) {
        if (currentPage < 1) {
            this.currentPage = 1;
        } else if (currentPage > totlePage) {
            this.currentPage = totlePage;
        } else {
            this.currentPage = crt;
        }
    }
    /**
     * 获取每页显示条数.
     * @return pageSize 返回每页条数
     * */
    public final int getPageSize() {
        return pageSize;
    }
    /**
     * 设置每页显示条数.
     * @param ps int
     * */
    public final void setPageSize(final int ps) {
        this.pageSize = ps;
    }
    /**
     * 获取总页数.
     * @return totlePage
     * */
    public final int getTotlePage() {
        return totlePage;
    }
    /**
     * 设置总页数.
     * @param tp int
     * */
    public final void setTotlePage(final int tp) {
        this.totlePage = tp;
    }
    /**
     * 获取总记录数.
     * @return totalRecord
     * */
    public final int getTotalRecord() {
        return totalRecord;
    }
    /**
     *set总记录数.
     * @param tr int
     * */
    public final void setTotalRecord(final int tr) {
        this.totalRecord = tr;
    }
    /**
     * 获取查询的数据集合.
     * @return list
     * */
    public final List getList() {
        return list;
    }
    /**
     *set数据集合.
     * @param lst List
     * */
    public final void setList(final List lst) {
        this.list = lst;
    }
}
