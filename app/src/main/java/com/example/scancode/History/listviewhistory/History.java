package com.example.scancode.History.listviewhistory;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "CreateHistory")
public class History {
    @ColumnInfo(name = "qrname")
    private String nameItem;
    @ColumnInfo(name = "qrinfor")
    private String desItem;
    @ColumnInfo(name = "qrtime")
    private String timeItem;
    @ColumnInfo(name = "qricon")
    private int icon;
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "qrformat")
    private String format = "";
//    public History(String nameItem, String desItem, String timeItem) {
//        this.nameItem = nameItem;
//        this.desItem = desItem;
//        this.timeItem = timeItem;
//    }
    public History(String format, String nameItem, String desItem, String timeItem) {
        this.format = format;
        this.nameItem = nameItem;
        this.desItem = desItem;
        this.timeItem = timeItem;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
