package com.example.sonota.db;

public class ShiftRecord {
    private long id;
    private long text;
    private long stime;
    private long etime;
    private int btime;

    public ShiftRecord(long id, long text, long stime, long etime, int btime) {
        this.id = id;
        this.text = text;
        this.stime = stime;
        this.etime = etime;
        this.btime = btime;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getText() {
        return text;
    }

    public void setText(long text) {
        this.text = text;
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

    public int getBtime() {
        return btime;
    }

    public void setBtime(int btime) {
        this.btime = btime;
    }
}
