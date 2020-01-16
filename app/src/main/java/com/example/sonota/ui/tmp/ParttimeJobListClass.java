package com.example.sonota.ui.tmp;

public class ParttimeJobListClass {
    private  long id;
    private int parttimejobId;
    private String starttime;
    private String finishtime;
    private int breaktime;

    public ParttimeJobListClass(long id, int parttimejobId, String starttime, String finishtime, int breaktime) {
        this.id = id;
        this.parttimejobId = parttimejobId;
        this.starttime = starttime;
        this.finishtime = finishtime;
        this.breaktime = breaktime;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getParttimejobId() {
        return parttimejobId;
    }

    public void setParttimejobId(int parttimejobId) {
        this.parttimejobId = parttimejobId;
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
