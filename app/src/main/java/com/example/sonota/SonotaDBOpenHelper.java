
package com.example.sonota;

        import android.content.Context;
        import android.database.sqlite.SQLiteDatabase;
        import android.database.sqlite.SQLiteOpenHelper;
        import android.util.Log;

public class SonotaDBOpenHelper extends SQLiteOpenHelper {
    // データーベースのバージョン
    private static final int DATABASE_VERSION = 3;
    // データーベース名
    private static final String DATABASE_NAME = "SONOTA_DB";

    //分割支払い表
    private static final String TABLE_NAME1 = "t_partial";
    private static final String partial_code = "partial_code";
    private static final String partial_money ="partial_money";
    private static final String partial_fwithdrawel = "partial_fwithdrawel";
    private static final String partial_cpay = "partial_cpay";
    private static final String partial_pmemo = "partial_pmemo";

    //予定表
    private static final String TABLE_NAME2 = "schedule_code";
    private static final String schedule_code ="schedule_code";
    private static final String schedule_name = "schedule_name";
    private static final String schedule_day = "schedule_day";
    private static final String schedule_stime = "schedule_stime";
    private static final String schedule_etime = "schedule_etime";

    //予定テンプレート表
    private static final String TABLE_NAME3 = "scheduletemplate_code";
    private static final String scheduletemplate_code = "scheduletemplate_code";
    private static final String scheduletemplate_name = "scheduletemplate_name";
    private static final String scheduletemplate_stime = "scheduletemplate_stime";
    private static final String scheduletemplate_etime = "scheduletemplate_etime";

    //バイトテンプレート表
    private static final String TABLE_NAME4 = "Parttimejobtemplate_code";
    private static final String Parttimejobtemplate_code = "Parttimejobtemplate_code";
    private static final String Parttimejobtemplate_stime = " Parttimejobtemplate_stime";
    private static final String Parttimejobtemplate_etime = " Parttimejobtemplate_etime";
    private static final String Parttimejobtemplate_btime = "Parttimejobtemplate_btime";

    //シフト表
    private static final String TABLE_NAME5 = "t_shift";
    private static final String t_shift = "t_shift";
    private static final String shift_code = "shift_code";
    private static final String shift_date = "shift_date";
    private static final String shift_stime = "shift_stime";
    private static final String shift_etime = "shift_etime";
    private static final String shift_btime = "shift_btime";

    //バイト先表
    private static final String TABLE_NAME6 = "t_byteahead";
    private static final String t_byteahead = "t_byteahead";
    private static final String byteahead_code = "byteahead_code";
    private static final String byteahead_name = "byteahead_name";
    private static final String byteahead_hwage = "byteahead_hwage";
    private static final String byteahead_pday = "byteahead_pday";
    private static final String byteahead_cday = "byteahead_cday";

    //クレジット表
    private static final String TABLE_NAME7 = "t_creditcard";
    private static final String t_creditcard = "t_creditcard";
    private static final String creditcard_code = "creditcard_code";
    private static final String creditcard_name = "creditcard_name";
    private static final String creditcard_cday = "creditcard_cday";
    private static final String creditcard_dpayment = "creditcard_dpayment";

    //支払い表
    private static final String TABLE_NAME8 = "t_payment";
    private static final String t_payment = "t_payment";
    private static final String payment_code = "payment_code";
    private static final String payment_money = "payment_money";
    private static final String payment_date = "payment_date";
    private static final String payment_cpay = "payment_cpay";
    private static final String payment_memo = "payment_memo";
    private static final String payment_category = "payment_category";

    //収入表
    private static final String TABLE_NAME9 = "t_income";
    private static final String t_income = "t_income";
    private static final String income_code = "income_code";
    private static final String income_money = "income_money";
    private static final String income_day = "income_day";
    private static final String income_memo = "income_memo";

    //月別収入表
    private static final String TABLE_NAME10 = "t_monthly";
    private static final String t_monthly = "t_monthly";
    private static final String monthly_month = "monthly_month";
    private static final String monthly_income = "monthly_income";

    //表作成　

//          テーブルを作成する
//         execSQLメソッドにCREATET TABLE命令を文字列として渡すことで実行される
//         引数で指定されているものの意味は以下の通り
//         引数1 ・・・ id：列名 , INTEGER：数値型 , PRIMATY KEY：テーブル内の行で重複無し , AUTOINCREMENT：1から順番に振っていく
//         引数2 ・・・ name：列名 , TEXT：文字列型
//         引数3 ・・・ price：列名 , INTEGER：数値型


    private static final String SQL_CREATE_ENTRIES =
            //分割支払い表
            "CREATE TABLE " + TABLE_NAME1 + " (" +
                    partial_code + "  INTEGER PRIMARY KEY," +
                    partial_money + " INTEGER," +
                    partial_fwithdrawel + " TEXT," +
                    partial_cpay + " BOOLEAN," +
                    partial_pmemo + " TEXT)";


    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " +TABLE_NAME2;

    //分割支払い表
    private static final String SQL_CREATE_ENTRIES2 =

            "CREATE TABLE " +  TABLE_NAME2 + " (" +
                    schedule_code + "  INTEGER PRIMARY KEY," +
                    schedule_name + " TEXT," +
                    schedule_day + " TEXT," +
                    schedule_stime + " TEXT," +
                    schedule_etime + " TEXT)";

    private static final String SQL_DELETE_ENTRIES2 =
            "DROP TABLE IF EXISTS " +TABLE_NAME2;

    //予定テンプレート表
    private static final String SQL_CREATE_ENTRIES3 =

            "CREATE TABLE " +  TABLE_NAME3 + " (" +
                    scheduletemplate_code + "  INTEGER PRIMARY KEY," +
                    scheduletemplate_name + " TEXT," +
                    scheduletemplate_stime + " TEXT," +
                    scheduletemplate_etime + " TEXT)";

    private static final String SQL_DELETE_ENTRIES3 =
            "DROP TABLE IF EXISTS " +TABLE_NAME3;

    //バイトテンプレート表
    private static final String SQL_CREATE_ENTRIES4 =

            "CREATE TABLE " +  TABLE_NAME4 + " (" +
                    Parttimejobtemplate_code + "  INTEGER PRIMARY KEY," +
                    byteahead_code + "INTEGER,"+
                    Parttimejobtemplate_stime + " TEXT," +
                    Parttimejobtemplate_etime + " TEXT," +
                    Parttimejobtemplate_btime + " INTEGER)";

    private static final String SQL_DELETE_ENTRIES4 =
            "DROP TABLE IF EXISTS " +TABLE_NAME4;

    //シフト表
    private static final String SQL_CREATE_ENTRIES5 =

            "CREATE TABLE " +  TABLE_NAME5 + " (" +
                    shift_code + "  INTEGER PRIMARY KEY," +
                    byteahead_code + "INTEGER,"+
                    shift_date + " TEXT," +
                    shift_stime + " TEXT," +
                    shift_etime + " TEXT," +
                    shift_btime + " INTEGER)";

    private static final String SQL_DELETE_ENTRIES5 =
            "DROP TABLE IF EXISTS " +TABLE_NAME5;

    //バイト先表
    private static final String SQL_CREATE_ENTRIES6 =

            "CREATE TABLE " +  TABLE_NAME6 + " (" +
                    byteahead_code + "  INTEGER PRIMARY KEY," +
                    byteahead_name + " TEXT," +
                    byteahead_hwage + " INTEGER," +
                    byteahead_pday + " TEXT," +
                    byteahead_cday + " TEXT)" ;

    private static final String SQL_DELETE_ENTRIES6 =
            "DROP TABLE IF EXISTS " +TABLE_NAME6;

    //クレジット表
    private static final String SQL_CREATE_ENTRIES7 =

            "CREATE TABLE " +  TABLE_NAME7 + " (" +
                    creditcard_code + "  INTEGER PRIMARY KEY," +
                    creditcard_name + " TEXT," +
                    creditcard_cday + " TEXT," +
                    creditcard_dpayment + " TEXT)" ;

    private static final String SQL_DELETE_ENTRIES7 =
            "DROP TABLE IF EXISTS " +TABLE_NAME7;

    //支払い表
    private static final String SQL_CREATE_ENTRIES8 =

            "CREATE TABLE " +  TABLE_NAME8 + " (" +
                    payment_code + "  INTEGER PRIMARY KEY," +
                    payment_money + "  INTEGER,"+
                    payment_date + " TEXT," +
                    payment_cpay + " BOOLEAN," +
                    payment_memo + "  INTEGER,"+
                    creditcard_code + "  INTEGER,"+
                    payment_category + " TEXT)" ;

    private static final String SQL_DELETE_ENTRIES8 =
            "DROP TABLE IF EXISTS " +TABLE_NAME8;


    //収入表
    private static final String SQL_CREATE_ENTRIES9 =

            "CREATE TABLE " + TABLE_NAME9 + " (" +
                    income_code + " INTEGER PRIMARY KEY," +
                    income_money + " INTEGER,"+
                    income_day + " TEXT," +
                    income_memo + " TEXT)" ;

    private static final String SQL_DELETE_ENTRIES9 =
            "DROP TABLE IF EXISTS " +TABLE_NAME9;

    private static final String SQL_CREATE_ENTRIES10 =

            "CREATE TABLE " +  TABLE_NAME10 + " (" +
                    monthly_month + "  TEXT PRIMARY KEY," +
                    monthly_income + " INTENGER)" ;

    private static final String SQL_DELETE_ENTRIES10 =
            "DROP TABLE IF EXISTS " +TABLE_NAME10;




    public SonotaDBOpenHelper(Context context) {
        super((Context) context,DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // テーブル作成
        // SQLiteファイルがなければSQLiteファイルが作成される
        db.execSQL(SQL_CREATE_ENTRIES);
        db.execSQL(SQL_CREATE_ENTRIES2);
        db.execSQL(SQL_CREATE_ENTRIES3);
        db.execSQL(SQL_CREATE_ENTRIES4);
        db.execSQL(SQL_CREATE_ENTRIES5);
        db.execSQL(SQL_CREATE_ENTRIES6);
        db.execSQL(SQL_CREATE_ENTRIES7);
        db.execSQL(SQL_CREATE_ENTRIES8);
        db.execSQL(SQL_CREATE_ENTRIES9);
        db.execSQL(SQL_CREATE_ENTRIES10);
        Log.d("debug", "onCreate(SQLiteDatabase db)");

    }

    // データベースをバージョンアップした時に実行される処理
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_CREATE_ENTRIES);
        db.execSQL(SQL_DELETE_ENTRIES2);
        db.execSQL(SQL_DELETE_ENTRIES3);
        db.execSQL(SQL_DELETE_ENTRIES4);
        db.execSQL(SQL_DELETE_ENTRIES5);
        db.execSQL(SQL_DELETE_ENTRIES6);
        db.execSQL(SQL_DELETE_ENTRIES7);
        db.execSQL(SQL_DELETE_ENTRIES8);
        db.execSQL(SQL_CREATE_ENTRIES9);
        db.execSQL(SQL_CREATE_ENTRIES10);
        onCreate(db);
    }


    // データベースが開かれた時に実行される処理
    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
    }
}
