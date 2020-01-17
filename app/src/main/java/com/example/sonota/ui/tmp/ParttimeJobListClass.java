package com.example.sonota.ui.tmp;

import com.example.sonota.ui.cal.ParttimejobPlaceClass;

import java.util.ArrayList;

public class ParttimeJobListClass {
    private  long id;
    private int parttimejobId;
    String Title;
    private String starttime;
    private String finishtime;
    private int breaktime;

    public ParttimeJobListClass(long id, int parttimejobId, String starttime, String finishtime, int breaktime,ArrayList<ParttimejobPlaceClass> pData) {
        this.id = id;
        this.parttimejobId = parttimejobId;
        this.Title = getPNameById(parttimejobId,pData);
        this.starttime = starttime;
        this.finishtime = finishtime;
        this.breaktime = breaktime;
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

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
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
