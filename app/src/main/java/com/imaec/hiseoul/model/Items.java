package com.imaec.hiseoul.model;

import org.simpleframework.xml.ElementList;

import java.util.ArrayList;

public class Items {

    @ElementList(inline = true, name = "item")
    private ArrayList<Item> listItem;

    public ArrayList<Item> getListItem() {
        return listItem;
    }

    public void setListItem(ArrayList<Item> listItem) {
        this.listItem = listItem;
    }
}
