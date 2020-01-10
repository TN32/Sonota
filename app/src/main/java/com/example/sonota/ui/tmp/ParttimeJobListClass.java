package com.example.sonota.ui.tmp;

public class ParttimeJobListClass {
    private  long id;
    private String title;
    private String starttime;
    private String finishtime;

    public ParttimeJobListClass(long id, String title, String starttime, String finishtime, int breaktime) {
        this.id = id;
        this.title = title;
        this.starttime = starttime;
        this.finishtime = finishtime;
        this.breaktime = breaktime;
    }

    private int breaktime;

    public ParttimeJobListClass(int i, String s, String s1, String s2) {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStarttime() {
        return starttime;
    }

    public void setStarttime(String starttime) {
        this.starttime = starttime;
    }

    public String getFinishtime() {
        return finishtime;
    }

    public void setFinishtime(String finishtime) {
        this.finishtime = finishtime;
    }

    public int getBreaktime() {
        return breaktime;
    }

    public void setBreaktime(int breaktime) {
        this.breaktime = breaktime;
    }
}
