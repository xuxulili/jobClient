package com.cqupt.jobclient.utils.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.cqupt.jobclient.acticity.app;
import com.cqupt.jobclient.model.MessageItemNY;
import com.cqupt.jobclient.model.MessageItemSC;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/4/23.
 */
public class DataBaseTool_NY {
    private NY_DB ny_db;
    private SQLiteDatabase dbWriter;
    private SQLiteDatabase dbReader;
    private Context context;
    public DataBaseTool_NY(Context context) {
        ny_db = new NY_DB(app.getContext());
        dbReader = ny_db.getReadableDatabase();
        dbWriter = ny_db.getWritableDatabase();
        this.context = context;
    }
    public void addToDB(List<MessageItemNY> messageItemNYs){
        for(MessageItemNY messageItemNY:messageItemNYs){
            ContentValues cv = new ContentValues();
            cv.put(NY_DB.TITLE_NY, messageItemNY.getMessageTitle());
            cv.put(NY_DB.TIME_NY, messageItemNY.getMessageTime());
            cv.put(NY_DB.PLACE_NY, messageItemNY.getMessagePlace());
            cv.put(NY_DB.URL_NY, messageItemNY.getMessageUrl());
            dbWriter.insert(NY_DB.TABLE_TABLE_NAME_NY, null, cv);
//            Log.e("插入南邮数据", messageItemNY.getMessageTitle());
        }

    }

    public ArrayList<MessageItemNY> selectAll() {
        String SQL = "select * from ny";
        Cursor cursor_ny = dbReader.rawQuery(SQL, null);
        ArrayList<MessageItemNY> messageItemNYs = null;
        if (cursor_ny.moveToFirst()) {
            messageItemNYs = new ArrayList<>();
            for (cursor_ny.moveToFirst(); !cursor_ny.isLast(); cursor_ny.moveToNext()) {
                MessageItemNY messageItemNY = new MessageItemNY();
                messageItemNY.setMessageTitle(cursor_ny.getString(cursor_ny.getColumnIndex("ny_title")));
                messageItemNY.setMessageUrl(cursor_ny.getString(cursor_ny.getColumnIndex("ny_url")));
                messageItemNY.setMessageTime(cursor_ny.getString(cursor_ny.getColumnIndex("ny_time")));
                messageItemNY.setMessagePlace(cursor_ny.getString(cursor_ny.getColumnIndex("ny_place")));
                messageItemNYs.add(messageItemNY);
                Log.e("查询得一条南邮数据",messageItemNY.getMessageTitle());
            }
        }
        return messageItemNYs;
    }
    public void clear() {
        String SQL = "select * from ny";
        String SQLDelete = "delete from ny";
        Cursor cursor = dbReader.rawQuery(SQL, null);
        if (cursor.moveToFirst()) {
            dbWriter.execSQL(SQLDelete);
//            Log.e("清空ny表","1");
        }
    }
}
