package com.example.sonota.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.sonota.SonotaDBOpenHelper;

import java.util.ArrayList;

public class DBDatafholder {
    private ArrayList<PartialRecord> partialRecordList;
    private ArrayList<ScheduleRecord> scheduleRecordList;
    private ArrayList<ScheduletemplateRecord> scheduletemplateRecordList;
    private ArrayList<ParttimejobtemplateRecord> parttimejobtemplateRecordList;
    private ArrayList<ShiftRecord> shiftRecordList;
    private ArrayList<ByteaheadRecord> byteaheadRecordList;
    private ArrayList<CreditcardRecord> creditcardRecordList;
    private ArrayList<PaymentRecord> paymentRecordList;
    private ArrayList<IncomeRecord> incomeRecordList;
    private ArrayList<MonthlyRecord> monthlyRecordList;

    protected SonotaDBOpenHelper helper;
    protected SQLiteDatabase db;
    Context mContext;

    public DBDatafholder(Context context) {
        this.mContext = context;
        DataLoad();
    }



    public void DataLoad(){
        if (helper == null){
            helper = new SonotaDBOpenHelper(mContext);
        }

        if (db == null){
            db = helper.getReadableDatabase();
        }

        Cursor cursor = db.query("t_partial",null,null,null,null,null,null);
        cursor.moveToFirst();

        for (int i = 0; i < cursor.getCount(); i++) {
            PartialRecord partialRecord = new PartialRecord(cursor.getLong(0),cursor.getInt(1),cursor.getInt(2),cursor.getInt(3),cursor.getLong(4),cursor.getInt(5),cursor.getLong(6));
            partialRecordList.add(partialRecord);
            cursor.moveToNext();
        }
        cursor.close();

        cursor = db.query("t_schedule",null,null,null,null,null,null);
        cursor.moveToFirst();

        for (int i = 0; i < cursor.getCount(); i++) {
            ScheduleRecord scheduleRecord = new ScheduleRecord(cursor.getLong(0),cursor.getLong(1),cursor.getLong(2),cursor.getLong(3),cursor.getLong(4));
            scheduleRecordList.add(scheduleRecord);
            cursor.moveToNext();
        }
        cursor.close();

        cursor = db.query("t_scheduletemplate",null,null,null,null,null,null);
        cursor.moveToFirst();

        for (int i = 0; i < cursor.getCount(); i++) {
            ScheduletemplateRecord scheduletemplateRecord = new ScheduletemplateRecord(cursor.getLong(0),cursor.getLong(1),cursor.getLong(2),cursor.getLong(3));
            scheduletemplateRecordList.add(scheduletemplateRecord);
            cursor.moveToNext();
        }
        cursor.close();

        cursor = db.query("t_Parttimejobtemplate",null,null,null,null,null,null);
        cursor.moveToFirst();

        for (int i = 0; i < cursor.getCount(); i++) {
            ParttimejobtemplateRecord ParttimejobtemplateRecord = new ParttimejobtemplateRecord(cursor.getLong(0),cursor.getInt(1),cursor.getLong(2),cursor.getInt(3));
            parttimejobtemplateRecordList.add(ParttimejobtemplateRecord);
            cursor.moveToNext();
        }
        cursor.close();

        cursor = db.query("t_shift",null,null,null,null,null,null);
        cursor.moveToFirst();

        for (int i = 0; i < cursor.getCount(); i++) {
            ShiftRecord shiftRecord = new ShiftRecord(cursor.getLong(0),cursor.getInt(1),cursor.getLong(2),cursor.getLong(3),cursor.getInt(4));
            shiftRecordList.add(shiftRecord);
            cursor.moveToNext();
        }
        cursor.close();

        cursor = db.query("t_byteahead",null,null,null,null,null,null);
        cursor.moveToFirst();

        for (int i = 0; i < cursor.getCount(); i++) {
            ByteaheadRecord byteaheadRecord = new ByteaheadRecord(cursor.getLong(0),cursor.getLong(1),cursor.getInt(2),cursor.getLong(3),cursor.getLong(4));
            byteaheadRecordList.add(byteaheadRecord);
            cursor.moveToNext();
        }
        cursor.close();

        cursor = db.query("t_creditcard",null,null,null,null,null,null);
        cursor.moveToFirst();

        for (int i = 0; i < cursor.getCount(); i++) {
            CreditcardRecord creditcardRecord = new CreditcardRecord(cursor.getLong(0),cursor.getLong(1),cursor.getLong(2),cursor.getLong(3));
            creditcardRecordList.add(creditcardRecord);
            cursor.moveToNext();
        }
        cursor.close();

        cursor = db.query("t_payment",null,null,null,null,null,null);
        cursor.moveToFirst();

        for (int i = 0; i < cursor.getCount(); i++) {
            PaymentRecord paymentRecord = new PaymentRecord(cursor.getLong(0),cursor.getInt(1),cursor.getLong(2),cursor.getInt(3),cursor.getInt(4),cursor.getInt(5),cursor.getLong(6));
            paymentRecordList.add(paymentRecord);
            cursor.moveToNext();
        }
        cursor.close();

        cursor = db.query("t_income",null,null,null,null,null,null);
        cursor.moveToFirst();

        for (int i = 0; i < cursor.getCount(); i++) {
            IncomeRecord incomeRecord = new IncomeRecord(cursor.getLong(0),cursor.getInt(1),cursor.getLong(2),cursor.getLong(3));
            incomeRecordList.add(incomeRecord);
            cursor.moveToNext();
        }
        cursor.close();

        cursor = db.query("t_monthly",null,null,null,null,null,null);
        cursor.moveToFirst();

        for (int i = 0; i < cursor.getCount(); i++) {
            MonthlyRecord monthlyRecord = new MonthlyRecord(cursor.getLong(0),cursor.getInt(1));
            monthlyRecordList.add(monthlyRecord);
            cursor.moveToNext();
        }
        cursor.close();
    }
}
