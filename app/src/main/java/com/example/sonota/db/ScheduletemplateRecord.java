package com.example.sonota.db;

public class ScheduletemplateRecord {
    private long id;
    private long name;
    private long stime;
    private long etime;

    public ScheduletemplateRecord(long id, long name, long stime, long etime) {
        this.id = id;
        this.name = name;
        this.stime = stime;
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

    public long getStime() {
        return stime;
    }

    public void setStime(long stime) {
        this.stime = stime;
    }

    public long getEtime() {
        return etime;
    }

    public void setEtime(long etime) {
        this.etime = etime;
    }
}
