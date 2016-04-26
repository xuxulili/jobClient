package com.cqupt.jobclient.utils.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Administrator on 2016/4/23.
 */
public class HK_DB extends SQLiteOpenHelper {
    public  static final String  TABLE_TABLE_NAME_HK="hk";
    public  static final String  TITLE_HK="hk_title";
    public  static final String  URL_HK="hk_url";
    public  static final String  SCAN_HK="hk_scan";
    public  static final String  TIME_HK="hk_time";
    public  static final String  ID_HK="hk_id";
    public HK_DB(Context context) {
        super(context, "hk", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE hk(hk_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "hk_title text not null," +
                "hk_time text not null," +
                "hk_url text not null," +
                "hk_scan text not null)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
