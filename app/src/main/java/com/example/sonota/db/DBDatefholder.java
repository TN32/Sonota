package com.example.sonota.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.sonota.SonotaDBOpenHelper;

import java.util.ArrayList;

public class DBDatefholder {
    private ArrayList<PartialRecord> partialRecordList;
    private ArrayList<ScheduleRecord> scheduleRecordList;
    private ArrayList<ScheduletemplateRecord> scheduletemplateRecordList;
    private ArrayList<ParttimejobtemplateRecord> parttimejobtemplateRecordList;
    private ArrayList<ShiftRecord> shiftRecordList;
    private ArrayList<ByteaheadRecord> byteaheadRecordList;
    private ArrayList<CreditcardRecord> creditcardRecordList;
    private ArrayList<PaymentRecord> paymentRecordList;
    private ArrayList<IncomeRecord> incomeRecordList;

    protected SonotaDBOpenHelper helper;
    protected SQLiteDatabase db;
    Context mContext;

    public DBDatefholder(Context context) {
        this.mContext = context;
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
    }
}
