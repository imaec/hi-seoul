package com.imaec.hiseoul.model;

import org.simpleframework.xml.Element;

public class IntroBody {

    @Element(name = "items")
    private IntroItems items;
    @Element
    private int numOfRows;
    @Element
    private int pageNo;
    @Element
    private int totalCount;

    public IntroItems getItems() {
        return items;
    }

    public void setItems(IntroItems items) {
        this.items = items;
    }

    public int getNumOfRows() {
        return numOfRows;
    }

    public void setNumOfRows(int numOfRows) {
        this.numOfRows = numOfRows;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }
}
