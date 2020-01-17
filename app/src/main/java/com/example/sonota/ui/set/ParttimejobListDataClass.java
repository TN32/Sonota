package com.example.sonota.ui.set;

public class ParttimejobListDataClass {
    long id;
    String name;
    int hwage;
    String pday;
    String cday;

    public ParttimejobListDataClass(long id, String name, int hwage, String pday, String cday) {
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHwage() {
        return hwage;
    }

    public void setHwage(int hwage) {
        this.hwage = hwage;
    }

    public String getPday() {
        return pday;
    }

    public void setPday(String pday) {
        this.pday = pday;
    }

    public String getCday() {
        return cday;
    }

    public void setCday(String cday) {
        this.cday = cday;
    }


}
