package com.cqupt.jobclient.utils.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Administrator on 2016/4/23.
 */
public class SC_DB extends SQLiteOpenHelper {
    public  static final String  TABLE_TABLE_NAME_SC="sc";
    public  static final String  TITLE_SC="sc_title";
    public  static final String  URL_SC="sc_url";
    public  static final String  ID_SC="sc_id";
    public SC_DB(Context context) {
        super(context, "sc", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE sc(sc_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "sc_title text not null," +
                "sc_url text not null)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
