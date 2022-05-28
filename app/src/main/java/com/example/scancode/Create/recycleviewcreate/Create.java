package com.example.scancode.Create.recycleviewcreate;

public class Create {
    private String nameItem;
    private int icon;

    public Create(String nameItem, int icon) {
        this.nameItem = nameItem;
        this.icon = icon;
    }

    public String getNameItem() {
        return nameItem;
    }

    public void setNameItem(String nameItem) {
        this.nameItem = nameItem;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }
}
