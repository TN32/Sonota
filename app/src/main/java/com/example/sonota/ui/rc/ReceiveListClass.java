package com.example.sonota.ui.rc;

public class ReceiveListClass {

    private long code;
    private int money;
    private String day;
    private String memo;
    public ReceiveListClass(long code, String memo, int money, String day) {
        this.code = code;
        this.memo = memo;
        this.money = money;
        this.day = day;

    }

    public long getCode() {
        return code;
    }

    public void setCode(long code) {
        this.code = code;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }
}
