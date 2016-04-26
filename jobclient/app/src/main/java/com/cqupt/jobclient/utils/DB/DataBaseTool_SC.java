package com.cqupt.jobclient.utils.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.cqupt.jobclient.model.MessageItemCQUPT;
import com.cqupt.jobclient.model.MessageItemSC;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/4/23.
 */
public class DataBaseTool_SC {
    private SC_DB sc_db;
    private SQLiteDatabase dbWriter;
    private SQLiteDatabase dbReader;
    private Context context;

    public DataBaseTool_SC(Context context) {
        this.context = context;
        sc_db = new SC_DB(context);
        dbReader = sc_db.getReadableDatabase();
        dbWriter = sc_db.getWritableDatabase();
    }
    public void addToDB(List<MessageItemSC> messageItemSCs){
        for(MessageItemSC messageItemSC:messageItemSCs){
            ContentValues cv = new ContentValues();
            cv.put(SC_DB.TITLE_SC, messageItemSC.getMessageTitle());
            cv.put(SC_DB.URL_SC, messageItemSC.getMessageUrl());
            dbWriter.insert(SC_DB.TABLE_TABLE_NAME_SC, null, cv);
//            Log.e("插入川大数据", messageItemSC.getMessageTitle());
        }

    }

    public ArrayList<MessageItemSC> selectAll() {
        String SQL = "select * from sc";
        Cursor cursor_sc = dbReader.rawQuery(SQL, null);
        ArrayList<MessageItemSC> messageItemSCs = null;
        if (cursor_sc.moveToFirst()) {
            messageItemSCs = new ArrayList<>();
            for (cursor_sc.moveToFirst(); !cursor_sc.isLast(); cursor_sc.moveToNext()) {
                MessageItemSC messageItemSC = new MessageItemSC();
                messageItemSC.setMessageTitle(cursor_sc.getString(cursor_sc.getColumnIndex("sc_title")));
                messageItemSC.setMessageUrl(cursor_sc.getString(cursor_sc.getColumnIndex("sc_url")));
                messageItemSCs.add(messageItemSC);
//                Log.e("查询得一条数据",messageItemSC.getMessageTitle());
            }
        }
        return messageItemSCs;
    }
    public void clear() {
        String SQL = "select * from sc";
        String SQLDelete = "delete from sc";
        Cursor cursor = dbReader.rawQuery(SQL, null);
        if (cursor.moveToFirst()) {
            dbWriter.execSQL(SQLDelete);
//            Log.e("清空sc表","1");
        }
    }
}
