package com.example.sonota.ui.clc;

import java.util.Random;

public class LoanListDataClass {

    // リストの一項目自体を識別するためのid
    private long id = 0;

    private String title = "";

    private String amout = "";

    private String count = "";

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

    public String getAmout() {
        return amout;
    }

    public void setAmout(String amout) {
        this.amout = amout;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public LoanListDataClass(long id, String title, String amout, String count) {
        this.id = id;
        this.title = title;
        this.amout = amout;
        this.count = count;
    }
}
