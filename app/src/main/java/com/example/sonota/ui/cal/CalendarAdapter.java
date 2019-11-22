package com.example.sonota.ui.cal;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.example.sonota.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CalendarAdapter extends BaseAdapter {
    private List<Date> dateArray = new ArrayList();
    private Context mContext;
    private DateManager mDateManager;
    private LayoutInflater mLayoutInflater;
    private GridView calendarGridView;
    private boolean firstGetView;

    private int firstDayPosition,todayPosition = -1;

    //カスタムセルを拡張したらここでWigetを定義
    private static class ViewHolder {
        public TextView dateText;
    }

    public CalendarAdapter(Context context){
        mContext = context;
        mLayoutInflater = LayoutInflater.from(mContext);
        mDateManager = new DateManager();
        dateArray = mDateManager.getDays();
    }

    public CalendarAdapter(Context context,GridView calendarGridView){
        mContext = context;
        mLayoutInflater = LayoutInflater.from(mContext);
        mDateManager = new DateManager();
        dateArray = mDateManager.getDays();
        this.calendarGridView = calendarGridView;
        firstGetView = true;
    }

    @Override
    public int getCount() {
        return dateArray.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.calendar_cell, null);
            holder = new ViewHolder();
            holder.dateText = convertView.findViewById(R.id.dateText);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder)convertView.getTag();
        }

        //セルのサイズを指定
        float dp = mContext.getResources().getDisplayMetrics().density;
        AbsListView.LayoutParams params = new AbsListView.LayoutParams(parent.getWidth()/7 - (int)dp, (parent.getHeight() - (int)dp * mDateManager.getWeeks() ) / mDateManager.getWeeks());
        convertView.setLayoutParams(params);

        //日付のみ表示させる
        SimpleDateFormat dateFormat = new SimpleDateFormat("d", Locale.US);
        holder.dateText.setText(dateFormat.format(dateArray.get(position)));

        //当月以外のセルをグレーアウト
        if (mDateManager.isCurrentMonth(dateArray.get(position))){
            convertView.setBackgroundResource(R.drawable.selector);
        }else {
            convertView.setBackgroundColor(Color.LTGRAY);
        }

        //その月の１日の位置と、今月なら今日の位置を設定する
        if(mDateManager.isToday(dateArray.get(position))){
            todayPosition = position;
            if(firstGetView == true){
                calendarGridView.setItemChecked(position,true);
                firstGetView = false;
           }
        }
        else if(mDateManager.isFirstDay(dateArray.get(position))){
            firstDayPosition = position;
        }

        //日曜日を赤、土曜日を青に
        int colorId;
        switch (mDateManager.getDayOfWeek(dateArray.get(position))){
            case 1:
                colorId = Color.RED;
                break;
            case 7:
                colorId = Color.BLUE;
                break;
            default:
                colorId = Color.BLACK;
                break;
        }
        holder.dateText.setTextColor(colorId);

        return convertView;
    }



    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }


    //mDateManagerのisCurrentMonthの結果を返す
    public boolean isCurrentMonth(Date date){
        return mDateManager.isCurrentMonth(date);
    }

    //表示月を取得
    public String getTitle(){
        SimpleDateFormat format = new SimpleDateFormat("yyyy.MM", Locale.US);
        return format.format(mDateManager.mCalendar.getTime());
    }

    //日付を文字列にして返す
    public String getDateString(int position){
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd", Locale.US);
        return format.format(dateArray.get(position));
    }

    //カレンダー上の位置を指定してDateを取得
    public Date getDateByPosition(int position){
        return dateArray.get(position);
    }

    //getDefaultPosition(今月なら今日の日付、その他なら1日の位置)を返す
    public int getDefaultPosition() {
        if(mDateManager.isthisMonth(mDateManager.mCalendar.getTime())){
            return todayPosition;
        }
        else{
            return firstDayPosition;
        }
    }

    //１日の位置を返す
    public int getFirstDayPosition(){
        return firstDayPosition;
    }

    //mDateManagerの月移動メソッドを呼び出してグリッドビューに適応する
    public void MoveMonthbyIndex(int index){
        mDateManager.MoveMonthbyIndex(index);
        dateArray = mDateManager.getDays();
        this.notifyDataSetChanged();
    }
}