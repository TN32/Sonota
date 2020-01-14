package com.example.sonota.ui.set;

public class CreditListDataClass {

   private long id;

   private String name, closingday, paymentday;

    public CreditListDataClass(long id, String name, String closingday, String paymentday) {
        this.id = id;
        this.name = name;
        this.closingday = closingday;
        this.paymentday = paymentday;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClosingday() {
        return closingday;
    }

    public void setClosingday(String closingday) {
        this.closingday = closingday;
    }

    public String getPaymentday() {
        return paymentday;
    }

    public void setPaymentday(String paymentday) {
        this.paymentday = paymentday;
    }
}
