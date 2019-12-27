package com.imaec.hiseoul.model;

import org.simpleframework.xml.Element;

public class Header {
    @Element
    private int resultCode;
    @Element
    private String resultMsg;
    @Element(required = false)
    private String responseTime;

    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public String getResultMsg() {
        return resultMsg;
    }

    public void setResultMsg(String resultMsg) {
        this.resultMsg = resultMsg;
    }

    public String getResponseTime() {
        return responseTime;
    }

    public void setResponseTime(String responseTime) {
        this.responseTime = responseTime;
    }
}