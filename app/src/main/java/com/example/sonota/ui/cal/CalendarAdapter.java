package com.example.sonota.ui.cal;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.sonota.R;
import com.example.sonota.SonotaDBOpenHelper;

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

    private ArrayList<String> positionToDate;
    private int firstDayPosition,todayPosition = -1;
    ArrayList<ParttimejobPlaceClass> pPlace;

    //カスタムセルを拡張したらここでWigetを定義
    private static class ViewHolder {
        public LinearLayout contentArea;
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

    protected SonotaDBOpenHelper helper;
    protected SQLiteDatabase db;

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
            holder.contentArea = convertView.findViewById(R.id.calender_cell_content);
            convertView.setTag(holder);
            Colorling(position,holder);
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
            holder.contentArea.setBackgroundColor(Color.WHITE);
        }else {
            convertView.setBackgroundColor(Color.LTGRAY);
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

        //その月の１日の位置と、今月なら今日の位置を設定する
        if(mDateManager.isToday(dateArray.get(position))){
            int textColor = Color.WHITE;
            if(colorId == Color.BLACK){
                colorId = mContext.getResources().getColor(android.R.color.holo_orange_dark);
                textColor = Color.BLACK;
            }
            holder.dateText.setBackgroundColor(colorId);
            holder.dateText.setTextColor(textColor);
            todayPosition = position;
            if(calendarGridView.getCheckedItemPosition() == -1 && firstGetView == true){
                calendarGridView.setItemChecked(position,true);
                firstGetView = false;
            }
        }
        else if(mDateManager.isFirstDay(dateArray.get(position))){
            firstDayPosition = position;
        }

        return convertView;
    }

    public void setpPlace(){
        Cursor cursor = db.query(
                "t_byteahead",
                new String[]{"byteahead_code","byteahead_name","byteahead_hwage"},
                null,
                null,
                null,
                null,
                null
        );

        cursor.moveToFirst();
        pPlace = new ArrayList<>();
        for (int i = 0; i < cursor.getCount(); i++) {
            ParttimejobPlaceClass data = new ParttimejobPlaceClass(cursor.getInt(0),cursor.getString(1));
            pPlace.add(data);
            cursor.moveToNext();
        }

        cursor.close();
    }

    public void Colorling(int position,ViewHolder holder){
        String cDate = truncDate(dateArray.get(position));
        String[] selectData = new  String[1];
        selectData[0] = cDate;

        if (helper == null){
            helper = new SonotaDBOpenHelper(mContext.getApplicationContext());
        }

        if(db == null){
            db = helper.getWritableDatabase();
        }

        if(pPlace == null){
            setpPlace();
        }

        Cursor cursor;

        if(dateArray.get(position).after(new Date())){
            cursor = db.query(
                    "t_payment",
                    new String[]{"payment_code","payment_date","payment_memo","payment_money","payment_cpay"},
                    "payment_date like ?",
                    selectData,
                    null,
                    null,
                    null
            );

            cursor.moveToFirst();

            for (int i = 0; i < cursor.getCount(); i++) {
                TextView tv = new TextView(mContext);
                tv.setBackgroundColor(Color.YELLOW);
                tv.setTextSize(10);
                tv.setPadding(0,5,0,5);
                String text = cursor.getString(2) + "\n" + cursor.getInt(3) + "円";
                tv.setText(text);
                holder.contentArea.addView(tv);
                cursor.moveToNext();
            }

            cursor.close();
        }

        cursor = db.query(
                "t_shift",
                new String[]{"shift_code","byteahead_code","shift_stime","shift_etime","shift_btime"},
                "shift_date=?",
                selectData,
                null,
                null,
                "shift_stime",
                null
        );

        cursor.moveToFirst();

        for (int i = 0; i < cursor.getCount(); i++) {
            TextView tv = new TextView(mContext);
            tv.setBackgroundColor(Color.RED);
            tv.setTextColor(Color.WHITE);
            tv.setTextSize(10);
            tv.setPadding(0,5,0,5);
            String jName = getPNameById(cursor.getInt(1),pPlace);
            tv.setText(jName);
            holder.contentArea.addView(tv);
            cursor.moveToNext();
        }

        cursor.close();

        cursor = db.query(
                "t_schedule",
                new String[]{"schedule_code","schedule_name","schedule_stime","schedule_etime"},
                "schedule_day=?",
                selectData,
                null,
                null,
                "schedule_stime",
                null
        );

        cursor.moveToFirst();

        for (int i = 0; i < cursor.getCount(); i++) {
            TextView tv = new TextView(mContext);
            tv.setBackgroundColor(Color.BLUE);
            tv.setTextColor(Color.WHITE);
            tv.setTextSize(10);
            tv.setPadding(0,5,0,5);
            tv.setText(cursor.getString(1));
            holder.contentArea.addView(tv);
            cursor.moveToNext();
        }

        cursor.close();
    }

    private String getPNameById(int id, ArrayList<ParttimejobPlaceClass> pData){
        for (int index = 0;index < pData.size();index++){
            if(id == pData.get(index).getId()){
                return pData.get(index).getParttimejobPlace();
            }
        }
        return "すでに削除されたアルバイト先です.";
    }

    public String truncDate(Date date){
        SimpleDateFormat format = new SimpleDateFormat("yyyy_MM_dd", Locale.US);
        return format.format(date);
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

    public int getPositionToDateString(String dateString){
        if(mDateManager.isthisMonth(mDateManager.mCalendar.getTime())){
            return todayPosition;
        }
        for (int position = 0;position < dateArray.size();position++){
            Date date = dateArray.get(position);
            SimpleDateFormat format = new SimpleDateFormat("d", Locale.US);
            if (dateString.equals(format.format(date))) {
                return position;
            }
        }
        return -1;
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