package com.flagship.mgallery.utils;

import com.flagship.mgallery.entity.Painting;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author Flagship
 * @Date 2020/11/30 22:02
 * @Description
 */
public class PageModel {
    /**
     * 页号
     */
    private int page;
    /**
     * 总页数
     */
    private int totalPage;
    /**
     * 每页记录数
     */
    private int rows;
    /**
     * 总记录数
     */
    private int totalRows;
    /**
     * 当前页从第n行开始
     */
    private int pageStartRow;
    /**
     * 当前页到n行结束
     */
    private int pageEndRow;
    /**
     * 是否存在下一页
     */
    private boolean hasNextPage;
    /**
     * 是否存在上一页
     */
    private boolean hasPreviousPage;
    /**
     * 当前页面数据
     */
    private List pageData;

    public static void main(String[] args) {
        List sample = new ArrayList();
        for (int i = 0; i <= 100; i++) {
            sample.add(i);
        }
        PageModel pageModel = new PageModel(sample, 6, 8);
        System.out.println(pageModel.getPageData());
        System.out.println(pageModel.getTotalPage());
        System.out.println(pageModel.getPageStartRow());
        System.out.println(pageModel.getPageEndRow());
    }

    public PageModel(List data, int page, int rows) {
        this.page = page;
        this.rows = rows;
        totalRows = data.size();
        //总页数计算规则：总行数/每页记录数，能整除页数取整，不能整除则向上取整
        //例如18/6=3 | 20/6≈3.33 向上取整=4
        totalPage = new Double(Math.ceil(totalRows / (rows * 1f))).intValue();

        pageStartRow = (page - 1) * rows;
        pageEndRow = Math.min(page * rows, totalRows);
        pageData = data.subList(pageStartRow, pageEndRow);

        hasPreviousPage = page > 1;
        hasNextPage = page < totalPage;
    }

    public PageModel() {
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getTotalRows() {
        return totalRows;
    }

    public void setTotalRows(int totalRows) {
        this.totalRows = totalRows;
    }

    public int getPageStartRow() {
        return pageStartRow;
    }

    public void setPageStartRow(int pageStartRow) {
        this.pageStartRow = pageStartRow;
    }

    public int getPageEndRow() {
        return pageEndRow;
    }

    public void setPageEndRow(int pageEndRow) {
        this.pageEndRow = pageEndRow;
    }

    public boolean isHasNextPage() {
        return hasNextPage;
    }

    public void setHasNextPage(boolean hasNextPage) {
        this.hasNextPage = hasNextPage;
    }

    public boolean isHasPreviousPage() {
        return hasPreviousPage;
    }

    public void setHasPreviousPage(boolean hasPreviousPage) {
        this.hasPreviousPage = hasPreviousPage;
    }

    public List<Painting> getPageData() {
        return pageData;
    }

    public void setPageData(List pageData) {
        this.pageData = pageData;
    }
}
