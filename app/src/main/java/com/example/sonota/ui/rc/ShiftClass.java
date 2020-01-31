package com.example.sonota.ui.rc;

import java.util.ArrayList;

public class ShiftClass {

    private long code;
    private int amount;

    public ShiftClass(long code, int bcode, String stime, String etime, int btime,ArrayList<ByteaheadClass> byteaheadClass) {
        this.code = code;

        String[] stimeSplit = stime.split("_");
        String[] etimeSplit = etime.split("_");
        String[] btimeSplit = stime.split("_");

        if (Integer.valueOf(stimeSplit[0]) > Integer.valueOf(etimeSplit[0])) {

            etimeSplit[0] = String.valueOf(Integer.valueOf(etimeSplit[0]) + 24);
        }

        if (Integer.valueOf(stimeSplit[1]) > Integer.valueOf(etimeSplit[1])) {
            etimeSplit[0] = String.valueOf(Integer.valueOf(etimeSplit[0]) - 1);
            etimeSplit[1] = String.valueOf(Integer.valueOf(etimeSplit[1]) + 60);
        }

        int hour = Integer.valueOf(etimeSplit[0]) - Integer.valueOf(stimeSplit[0]);
        int minite = Integer.valueOf(etimeSplit[1]) - Integer.valueOf(stimeSplit[1]);

        int hwage = getHwageById((int) bcode, byteaheadClass);

        int time = (hour * 60 + minite);
        if(btime != 0){
            time = time - btime;
         }

        amount = (hwage * time) / 60;
        amount = amount;
    }

    private int getHwageById(int id, ArrayList<ByteaheadClass> bData){
        for (int index = 0;index < bData.size();index++){
            if(id == bData.get(index).getCode()){
                return bData.get(index).getHwage();
            }
        }
        return 0;
    }

    public int setAmount() {
        return amount;
    }

    public long getCode() {
        return code;
    }

    public void setCode(long code) {
        this.code = code;
    }

    public int getAmount() {
        return amount;
    }
}
