package com.miku.utils;

import java.util.ArrayList;

/**
 * @author Utopiamiku
 * @date 2020/8/13 15:47
 * @File SplitePage.py
 */
public class SplitePage<T> {
    private int currentPage = 1;
    private int pageRows = 6;
    private int totalPage;
    private int totalRows;
    private T t;

    public int getCurrentPage() {
        return currentPage;
    }

    public T getT() {
        return t;
    }

    public void setT(T t) {
        this.t = t;
    }

    public void setCurrentPage(int currentPage) {
        if(currentPage<1){
            currentPage = 1;
        }
        if(currentPage>getTotalPage()){
            currentPage = getTotalPage();
        }
        this.currentPage = currentPage;
    }

    public int getTotalPage() {

       return getTotalRows()%getPageRows() == 0?getTotalRows()/getPageRows():getTotalRows()/getPageRows()+1;
    }

    public int getTotalRows() {
        if(totalRows<1){
            setTotalRows(1);
        }
        return totalRows;
    }

    public void setTotalRows(int totalRows) {
        this.totalRows = totalRows;
    }



    public int getPageRows() {
        if(pageRows<1){
            pageRows=1;
        }
        return pageRows;
    }

    public void setPageRows(int pageRows) {
        this.pageRows = pageRows;
    }

    public SplitePage() {
    }
}
