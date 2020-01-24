package com.example.sonota.db;

public class PartialRecord {
    private long id;
    private int amount;
    private int ramount;
    private int times;
    private long fwithdrawel;
    private int cpay;
    private long pmemo;

    public PartialRecord(long id, int amount, int ramount, int times, long fwithdrawel, int cpay, long pmemo) {

        this.id = id;
        this.amount = amount;
        this.ramount = ramount;
        this.times = times;
        this.fwithdrawel = fwithdrawel;
        this.cpay = cpay;
        this.pmemo = pmemo;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getRamount() {
        return ramount;
    }

    public void setRamount(int ramount) {
        this.ramount = ramount;
    }

    public int getTimes() {
        return times;
    }

    public void setTimes(int times) {
        this.times = times;
    }

    public long getFwithdrawel() {
        return fwithdrawel;
    }

    public void setFwithdrawel(long fwithdrawel) {
        this.fwithdrawel = fwithdrawel;
    }

    public int getCpay() {
        return cpay;
    }

    public void setCpay(int cpay) {
        this.cpay = cpay;
    }

    public long getPmemo() {
        return pmemo;
    }

    public void setPmemo(long pmemo) {
        this.pmemo = pmemo;
    }
}
