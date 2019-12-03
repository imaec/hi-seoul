package com.imaec.hiseoul.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "response")
public class AreaData {

    @Element(name = "header")
    private Header header;
    @Element(name = "body")
    private Body body;

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public Body getBody() {
        return body;
    }

    public void setBody(Body body) {
        this.body = body;
    }
}
