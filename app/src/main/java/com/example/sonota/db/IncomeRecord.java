package com.example.sonota.db;

public class IncomeRecord {
    private long id;
    private int money;
    private long day;
    private long memo;

    public IncomeRecord(long id, int money, long day, long memo) {
        this.id = id;
        this.money = money;
        this.day = day;
        this.memo = memo;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public long getDay() {
        return day;
    }

    public void setDay(long day) {
        this.day = day;
    }

    public long getMemo() {
        return memo;
    }

    public void setMemo(long memo) {
        this.memo = memo;
    }
}
