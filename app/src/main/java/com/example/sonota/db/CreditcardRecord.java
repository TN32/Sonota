package com.example.sonota.db;

public class CreditcardRecord {
    private long id;
    private long name;
    private long cday;
    private long dpayment;

    public CreditcardRecord(long id, long name, long cday, long dpayment) {
        this.id = id;
        this.name = name;
        this.cday = cday;
        this.dpayment = dpayment;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getName() {
        return name;
    }

    public void setName(long name) {
        this.name = name;
    }

    public long getCday() {
        return cday;
    }

    public void setCday(long cday) {
        this.cday = cday;
    }

    public long getDpayment() {
        return dpayment;
    }

    public void setDpayment(long dpayment) {
        this.dpayment = dpayment;
    }
}
