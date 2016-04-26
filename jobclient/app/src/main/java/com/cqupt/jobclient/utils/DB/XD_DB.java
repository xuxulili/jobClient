package com.cqupt.jobclient.utils.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Administrator on 2016/4/23.
 */
public class XD_DB extends SQLiteOpenHelper {
    public  static final String  TABLE_TABLE_NAME_XD="xd";
    public  static final String  TITLE_XD="xd_title";
    public  static final String  URL_XD="xd_url";
    public  static final String  TIME_XD="xd_time";
    public  static final String  ID_XD="xd_id";
    public XD_DB(Context context) {
        super(context, "xd", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE xd(xd_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "xd_title text not null," +
                "xd_url text not null," +
                "xd_time text not null)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
