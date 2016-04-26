package com.cqupt.jobclient.utils.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Administrator on 2016/4/23.
 */
public class NY_DB extends SQLiteOpenHelper {
    public  static final String  TABLE_TABLE_NAME_NY="ny";
    public  static final String  TITLE_NY="ny_title";
    public  static final String  URL_NY="ny_url";
    public  static final String  TIME_NY="ny_time";
    public  static final String  PLACE_NY="ny_place";
    public  static final String  ID_NY="ny_id";

    public NY_DB(Context context) {
        super(context, "ny", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE ny(ny_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "ny_title text not null," +
                "ny_url text not null," +
                "ny_time text not null," +
                "ny_place text not null)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
