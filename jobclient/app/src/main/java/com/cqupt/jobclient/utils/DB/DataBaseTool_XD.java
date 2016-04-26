package com.cqupt.jobclient.utils.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.cqupt.jobclient.model.MessageItemNY;
import com.cqupt.jobclient.model.MessageItemXD;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/4/23.
 */
public class DataBaseTool_XD {
    private XD_DB xd_db;
    private SQLiteDatabase dbWriter;
    private SQLiteDatabase dbReader;
    private Context context;
    public DataBaseTool_XD(Context context) {
        xd_db = new XD_DB(context);
        dbReader = xd_db.getReadableDatabase();
        dbWriter = xd_db.getWritableDatabase();
        this.context = context;
    }
    public void addToDB(List<MessageItemXD> messageItemXDs){
        for(MessageItemXD messageItemXD:messageItemXDs){
            ContentValues cv = new ContentValues();
            cv.put(XD_DB.TITLE_XD, messageItemXD.getMessageTitle());
            cv.put(XD_DB.TIME_XD, messageItemXD.getMessagePostTime());
            cv.put(XD_DB.URL_XD, messageItemXD.getMessageUrl());
            dbWriter.insert(XD_DB.TABLE_TABLE_NAME_XD, null, cv);
//            Log.e("插入xd数据", messageItemXD.getMessageTitle());
        }

    }

    public ArrayList<MessageItemXD> selectAll() {
        String SQL = "select * from xd";
        Cursor cursor_xd = dbReader.rawQuery(SQL, null);
        ArrayList<MessageItemXD> messageItemXDs = null;
        if (cursor_xd.moveToFirst()) {
            messageItemXDs = new ArrayList<>();
            for (cursor_xd.moveToFirst(); !cursor_xd.isLast(); cursor_xd.moveToNext()) {
                MessageItemXD messageItemXD = new MessageItemXD();
                messageItemXD.setMessageTitle(cursor_xd.getString(cursor_xd.getColumnIndex("xd_title")));
                messageItemXD.setMessageUrl(cursor_xd.getString(cursor_xd.getColumnIndex("xd_url")));
                messageItemXD.setMessagePostTime(cursor_xd.getString(cursor_xd.getColumnIndex("xd_time")));
                messageItemXDs.add(messageItemXD);
//                Log.e("查询得一条xd数据",messageItemXD.getMessageTitle());
            }
        }
        return messageItemXDs;
    }
    public void clear() {
        String SQL = "select * from xd";
        String SQLDelete = "delete from xd";
        Cursor cursor = dbReader.rawQuery(SQL, null);
        if (cursor.moveToFirst()) {
            dbWriter.execSQL(SQLDelete);
//            Log.e("清空xd表","1");
        }
    }
}
