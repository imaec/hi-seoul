package com.imaec.hiseoul.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "response")
public class IntroData {

    @Element(name = "header")
    private Header header;
    @Element(name = "body")
    private IntroBody body;

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public IntroBody getBody() {
        return body;
    }

    public void setBody(IntroBody body) {
        this.body = body;
    }
}