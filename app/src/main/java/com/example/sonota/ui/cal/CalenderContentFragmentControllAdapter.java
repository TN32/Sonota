package com.example.sonota.ui.cal;

import java.util.Date;

import static java.lang.Integer.parseInt;

public class CalenderContentFragmentControllAdapter {

    CalenderContentFragment flagment;

    public CalenderContentFragmentControllAdapter(CalenderContentFragment flagment){
        this.flagment = flagment;
    }

    public void onCheckedDateChanged(Date afterDate){
        flagment.onCheckedDateChanged(afterDate);
    }

    public void CalenderLod(){
        flagment.CalendarLoad();
    }

    //表示されているカレンダーの外の日付が選択されたとき、カレンダーのページを移動する
    public void onCheckedNotCurrentMonth(boolean isNextMonth,boolean isOverCount){
        flagment.onCheckedNotCurrentMonth(isNextMonth,isOverCount);
    }

    //スケジュールページャーをスワイプしたと気カレンダーに選択を変更する
    public void onScheduleLIstPageChanged(Date newDate){
        flagment.onScheduleLIstPageChanged(newDate);
    }

}
