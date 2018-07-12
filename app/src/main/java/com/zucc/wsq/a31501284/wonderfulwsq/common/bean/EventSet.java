package com.zucc.wsq.a31501284.wonderfulwsq.common.bean;

import java.io.Serializable;

/**
 * Created by dell on 2018/7/11.
 */
public class EventSet implements Serializable {

    private int id;
    private String name;
    private int color;
    private int icon;
    private boolean isChecked;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
