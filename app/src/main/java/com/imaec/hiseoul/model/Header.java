package com.imaec.hiseoul.model;

import org.simpleframework.xml.Element;

public class Header {
    @Element
    int resultCode;
    @Element
    String resultMsg;

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
}