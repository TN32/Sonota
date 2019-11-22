package com.example.sonota.ui.cal;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static java.lang.Integer.parseInt;

public class CalFragmentControllAdapter {

    CalFragment flagment;

    public CalFragmentControllAdapter(CalFragment flagment){
        this.flagment = flagment;
    }

    public void onCheckedDateChanged(Date afterDate){
        flagment.onCheckedDateChanged(afterDate);
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
