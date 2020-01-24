package com.example.sonota.db;

public class ScheduleRecord {
    private long id;
    private long name;
    private long day;
    private long stlme;
    private long etime;

    public ScheduleRecord(long id, long name, long day, long stlme, long etime) {
        this.id = id;
        this.name = name;
        this.day = day;
        this.stlme = stlme;
        this.etime = etime;
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

    public long getDay() {
        return day;
    }

    public void setDay(long day) {
        this.day = day;
    }

    public long getStlme() {
        return stlme;
    }

    public void setStlme(long stlme) {
        this.stlme = stlme;
    }

    public long getEtime() {
        return etime;
    }

    public void setEtime(long etime) {
        this.etime = etime;
    }
}
