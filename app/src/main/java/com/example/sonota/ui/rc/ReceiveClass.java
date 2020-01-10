package com.example.sonota.ui.rc;

public class ReceiveClass {


    private long id;

    private String Title;
    private int amount;
    private int worktime;
    private int endurance;

    public ReceiveClass(long id, String title,  int worktime, int endurance) {
        this.id = id;
        Title = title;
        this.worktime = worktime;
        this.endurance = endurance;
        this.amount = worktime * endurance / 60;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getWorktime() {
        return worktime;
    }

    public void setWorktime(int worktime) {
        this.worktime = worktime;
    }

    public int getEndurance() {
        return endurance;
    }

    public void setEndurance(int endurance) {
        this.endurance = endurance;
    }
}
