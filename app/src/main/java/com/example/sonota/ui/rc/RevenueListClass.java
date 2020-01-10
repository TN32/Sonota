package com.example.sonota.ui.rc;

public class RevenueListClass {

    private long id;
    private String Month;
    private int Amount;

    public RevenueListClass(int id, String Month, int Amount){
        this.id = id;
        this.Month = Month;
        this.Amount = Amount;
    }


    public long getiId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMonth() {
        return Month;
    }

    public void setMonth(String month) {
        Month = month;
    }

    public int getAmount() {
        return Amount;
    }

    public void setAmount(int amount) {
        Amount = amount;
    }

}
