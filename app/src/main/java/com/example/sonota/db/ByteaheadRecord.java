package com.example.sonota.db;

public class ByteaheadRecord {
    private long id;
    private long name;
    private int hwage;
    private long pday;
    private long cday;

    public ByteaheadRecord(long id, long name, int hwage, long pday, long cday) {
        this.id = id;
        this.name = name;
        this.hwage = hwage;
        this.pday = pday;
        this.cday = cday;
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

    public int getHwage() {
        return hwage;
    }

    public void setHwage(int hwage) {
        this.hwage = hwage;
    }

    public long getPday() {
        return pday;
    }

    public void setPday(long pday) {
        this.pday = pday;
    }

    public long getCday() {
        return cday;
    }

    public void setCday(long cday) {
        this.cday = cday;
    }
}
