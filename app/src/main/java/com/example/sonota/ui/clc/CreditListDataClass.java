package com.example.sonota.ui.clc;

import java.util.Random;

public class CreditListDataClass {

    // リストの一項目自体を識別するためのid
    private long id = 0;

    private String title = "";

    private int amout;

    private int count;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getAmout() {
        return amout;
    }

    public void setAmout(int amout) {
        this.amout = amout;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public CreditListDataClass(long id, String title, int amout, int count) {
        this.id = id;
        this.title = title;
        this.amout = amout;
        this.count = count;
    }
}
