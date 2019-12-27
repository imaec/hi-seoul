package com.imaec.hiseoul.model;

import org.simpleframework.xml.Element;

@Element(name = "items")
public class IntroItems {

    @Element(name = "item")
    private IntroItem item;

    public IntroItem getItem() {
        return item;
    }

    public void setItem(IntroItem item) {
        this.item = item;
    }
}
