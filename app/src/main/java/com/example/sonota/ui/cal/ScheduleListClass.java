package com.example.sonota.ui.cal;

import java.util.ArrayList;

public class ScheduleListClass {
    private  long id;
    private String title;
    private String starttime;
    private String finishtime;
    private int breaktime;
    private String amount = null;

    public ScheduleListClass(long id, int aheadId, String starttime, String finishtime, int breaktime, ArrayList<ParttimejobPlaceClass> pDatat) {
        this.id = id;
        this.title = "[アルバイト]　" + getPNameById(aheadId,pDatat);
        this.starttime = starttime;
        this.finishtime = finishtime;
        this.breaktime = breaktime;
    }

    public ScheduleListClass(long id, String title, String starttime, String finishtime) {
        this.id = id;
        this.title = "[イベント]　" + title;
        this.starttime = starttime;
        this.finishtime = finishtime;
        this.breaktime = -1;
    }

    public ScheduleListClass(long id, String title, String amount) {
        this.id = id;
        this.title = "[支出予定]　" + title;
        this.amount = amount;
        this.breaktime = -1;
    }

    private String getPNameById(int id, ArrayList<ParttimejobPlaceClass> pData){
        for (int index = 0;index < pData.size();index++){
            if(id == pData.get(index).getId()){
                return pData.get(index).getParttimejobPlace();
            }
        }
        return "すでに削除されたアルバイト先です.";
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

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
