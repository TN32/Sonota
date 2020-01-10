package com.example.sonota.ui.tmp;

public class EventListClass {
    private  long id;
    private String title;
    private String starttime;
    private String finishtime;

    public EventListClass(long id, String title, String starttime, String finishtime) {
        this.id = id;
        this.title = title;
        this.starttime = starttime;
        this.finishtime = finishtime;
    }


    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
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
}

