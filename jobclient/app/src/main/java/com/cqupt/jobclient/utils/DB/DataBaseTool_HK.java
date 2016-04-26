package com.cqupt.jobclient.utils.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.cqupt.jobclient.acticity.app;
import com.cqupt.jobclient.model.MessageItemHK;
import com.cqupt.jobclient.model.MessageItemXD;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/4/23.
 */
public class DataBaseTool_HK {
    private HK_DB hk_db;
    private SQLiteDatabase dbWriter;
    private SQLiteDatabase dbReader;
    private Context context;
    public DataBaseTool_HK(Context context) {
        hk_db = new HK_DB(context);
        dbReader = hk_db.getReadableDatabase();
        dbWriter = hk_db.getWritableDatabase();
        this.context = context;
    }
    public void addToDB(List<MessageItemHK> messageItemHKs){
        for(MessageItemHK messageItemHK:messageItemHKs){
            ContentValues cv = new ContentValues();
            cv.put(HK_DB.TITLE_HK, messageItemHK.getMessageTitle());
            cv.put(HK_DB.TIME_HK, messageItemHK.getMessagePostTime());
            cv.put(HK_DB.URL_HK, messageItemHK.getMessageUrl());
            cv.put(HK_DB.SCAN_HK, messageItemHK.getMessageViewNum());
            dbWriter.insert(HK_DB.TABLE_TABLE_NAME_HK, null, cv);
        }

    }

    public ArrayList<MessageItemHK> selectAll() {
        String SQL = "select * from hk";
        Cursor cursor_hk = dbReader.rawQuery(SQL, null);
        ArrayList<MessageItemHK> messageItemHKs = null;
        if (cursor_hk.moveToFirst()) {
            messageItemHKs = new ArrayList<>();
            for (cursor_hk.moveToFirst(); !cursor_hk.isLast(); cursor_hk.moveToNext()) {
                MessageItemHK messageItemHK = new MessageItemHK();
                messageItemHK.setMessageTitle(cursor_hk.getString(cursor_hk.getColumnIndex("hk_title")));
                messageItemHK.setMessageUrl(cursor_hk.getString(cursor_hk.getColumnIndex("hk_url")));
                messageItemHK.setMessagePostTime(cursor_hk.getString(cursor_hk.getColumnIndex("hk_time")));
                messageItemHK.setMessageViewNum(cursor_hk.getString(cursor_hk.getColumnIndex("hk_scan")));
                messageItemHKs.add(messageItemHK);
            }
        }
        return messageItemHKs;
    }
    public void clear() {
        String SQL = "select * from hk";
        String SQLDelete = "delete from hk";
        Cursor cursor = dbReader.rawQuery(SQL, null);
        if (cursor.moveToFirst()) {
            dbWriter.execSQL(SQLDelete);
//            Log.e("清空xd表","1");
        }
    }
}
