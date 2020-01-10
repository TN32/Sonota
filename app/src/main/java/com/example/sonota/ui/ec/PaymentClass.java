package com.example.sonota.ui.ec;

public class PaymentClass {


    private long id;

    private int Amount;
    private String date;
    private boolean isCregitPayment;
    private String memo;
    private long creditcode;



    public PaymentClass( int code,String date,String memo, int amount){
        this.id = code;
        this.date = date;

        isCregitPayment = false;
        this.memo = memo;

        this.Amount = amount;
    }

    public PaymentClass( int code,String date,String memo, int amount, long creditcode ){
        this.id = code;
        this.date = date;
        isCregitPayment = true;
        this.memo = memo;
        this.Amount = amount;
        this.creditcode = creditcode;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getAmount() {
        return Amount;
    }

    public void setAmount(int amount) {
        Amount = amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean isCregitPayment() {
        return isCregitPayment;
    }

    public void setCregitPayment(boolean cregitPayment) {
        isCregitPayment = cregitPayment;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public long getCreditcode() {
        return creditcode;
    }

    public void setCreditcode(long creditcode) {
        this.creditcode = creditcode;
    }

}
