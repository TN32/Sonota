package com.example.sonota.db;

public class PaymentRecord {
    private long id;
    private int meney;
    private long date;
    private int cpay;
    private int memo;
    private int cade;
    private long category;

    public PaymentRecord(long id, int meney, long date, int cpay, int memo, int cade, long category) {
        this.id = id;
        this.meney = meney;
        this.date = date;
        this.cpay = cpay;
        this.memo = memo;
        this.cade = cade;
        this.category = category;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getMeney() {
        return meney;
    }

    public void setMeney(int meney) {
        this.meney = meney;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public int getCpay() {
        return cpay;
    }

    public void setCpay(int cpay) {
        this.cpay = cpay;
    }

    public int getMemo() {
        return memo;
    }

    public void setMemo(int memo) {
        this.memo = memo;
    }

    public int getCade() {
        return cade;
    }

    public void setCade(int cade) {
        this.cade = cade;
    }

    public long getCategory() {
        return category;
    }

    public void setCategory(long category) {
        this.category = category;
    }
}
