package com.cqupt.jobclient.utils.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Administrator on 2016/4/23.
 */
public class CQUPT_DB extends SQLiteOpenHelper {
    public  static final String  TABLE_TABLE_NAME_CQUPT="cqupt";
    public  static final String  TITLE_CQUPT="cqupt_title";
    public  static final String  URL_CQUPT="cqupt_url";
    public  static final String  TIME_CQUPT="cqupt_time";
    public  static final String  PLACE_CQUPT="cqupt_place";
    public  static final String  NEED_CQUPT="cqupt_need_num";
    public  static final String  SCAN_CQUPT="cqupt_scan";
    public  static final String  TYPE_CQUPT="cqupt_type";
    public  static final String  ID_CQUPT="cqupt_id";

    public CQUPT_DB(Context context) {
        super(context, "cqupt", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE cqupt(cqupt_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "cqupt_title text not null," +
                "cqupt_url text not null," +
                "cqupt_time text not null," +
                "cqupt_place text not null," +
                "cqupt_need_num text not null," +
                "cqupt_scan text not null," +
                "cqupt_type text not null)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
