package com.example.scancode.History.listviewhistory;

public class History {
    private String nameItem, desItem, timeItem;
    private int icon;
    private boolean selected;

    public History(String nameItem, String desItem, String timeItem, int icon) {
        this.nameItem = nameItem;
        this.desItem = desItem;
        this.timeItem = timeItem;
        this.icon = icon;
    }

    public String getNameItem() {
        return nameItem;
    }

    public void setNameItem(String nameItem) {
        this.nameItem = nameItem;
    }

    public String getDesItem() {
        return desItem;
    }

    public void setDesItem(String desItem) {
        this.desItem = desItem;
    }

    public String getTimeItem() {
        return timeItem;
    }

    public void setTimeItem(String timeItem) {
        this.timeItem = timeItem;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }
}
