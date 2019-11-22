package com.example.sonota.ui.cal;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class DateManager {
    Calendar mCalendar;

    public DateManager(){
        mCalendar = Calendar.getInstance();
    }

    //当月の要素を取得
    public List<Date> getDays(){
        //現在の状態を保持
        Date startDate = mCalendar.getTime();

        //GridViewに表示するマスの合計を計算
        int count = getWeeks() * 7 ;

        //当月のカレンダーに表示される前月分の日数を計算
        mCalendar.set(Calendar.DATE, 1);
        int dayOfWeek = mCalendar.get(Calendar.DAY_OF_WEEK) - 1;
        mCalendar.add(Calendar.DATE, -dayOfWeek);

        List<Date> days = new ArrayList<>();

        //カレンダーに日付のデータをセット
        for (int i = 0; i < count; i ++){
            days.add(mCalendar.getTime());
            mCalendar.add(Calendar.DATE, 1);
        }

        //状態を復元
        mCalendar.setTime(startDate);

        return days;
    }

    //dateが今月か確認
    public boolean isthisMonth(Date date){
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM", Locale.US);
        String thisMonth = format.format(new Date());
        if (thisMonth.equals(format.format(date))){
            return true;
        }else {
            return false;
        }
    }

    //dateがカレンダーに表示されている月か確認
    public boolean isCurrentMonth(Date date){
        SimpleDateFormat format = new SimpleDateFormat("yyyy.MM", Locale.US);
        String currentMonth = format.format(mCalendar.getTime());
        if (currentMonth.equals(format.format(date))){
            return true;
        }else {
            return false;
        }
    }

    //dateが今日か確認
    public boolean isToday(Date date){
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd", Locale.US);
        String currentDate = format.format(new Date());
        if (currentDate.equals(format.format(date))){
            return true;
        }else {
            return false;
        }
    }

    //dateが月の初めの日か確認
    public boolean isFirstDay(Date date) {
        if (!isCurrentMonth(date)) {
            return false;
        }
        SimpleDateFormat format = new SimpleDateFormat("d", Locale.US);
        if ("1".equals(format.format(date))) {
            return true;
        } else {
            return false;
        }
    }

    //週数を取得
    public int getWeeks(){
        return mCalendar.getActualMaximum(Calendar.WEEK_OF_MONTH);
    }

    //曜日を取得
    public int getDayOfWeek(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_WEEK);
    }

    //indexの数だけ月を移動する
    public void MoveMonthbyIndex(int index){
        mCalendar.add(Calendar.MONTH, index);
    }
}