package com.example.sonota.ui.clc;

import java.util.Random;

public class CreditListDataClass {


    // リストの一項目自体を識別するためのid
    private long id;

    private String memo;
    private int rAmount;
    private int amout;
    private int times;

    public CreditListDataClass(long id, String memo, int rAmount, int amout, int times) {
        this.id = id;
        this.memo = memo;
        this.rAmount = rAmount;
        this.amout = amout;
        this.times = times;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public int getrAmount() {
        return rAmount;
    }

    public void setrAmount(int rAmount) {
        this.rAmount = rAmount;
    }

    public int getAmout() {
        return amout;
    }

    public void setAmout(int amout) {
        this.amout = amout;
    }

    public int getTimes() {
        return times;
    }

    public void setTimes(int times) {
        this.times = times;
    }
}
