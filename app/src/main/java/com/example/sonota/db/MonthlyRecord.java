package com.example.sonota.db;

public class MonthlyRecord {
    private long id;
    private int income;

    public MonthlyRecord(long id, int income) {
        this.id = id;
        this.income = income;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getIncome() {
        return income;
    }

    public void setIncome(int income) {
        this.income = income;
    }
}
