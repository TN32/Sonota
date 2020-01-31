package com.example.sonota.ui.rc;

public class ByteaheadClass {

    private long code;
    private int hwage;

    public ByteaheadClass(long code, int hwage) {
        this.code = code;
        this.hwage = hwage;
    }

    public long getCode() {
        return code;
    }

    public void setCode(long code) {
        this.code = code;
    }

    public int getHwage() {
        return hwage;
    }

    public void setHwage(int hwage) {
        this.hwage = hwage;
    }
}
