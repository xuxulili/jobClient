package com.cqupt.jobclient.utils.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.cqupt.jobclient.model.MessageItemCQUPT;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/4/23.
 */
public class DataBaseTool_CQUPT {
    private CQUPT_DB cqupt_db;
    private SQLiteDatabase dbWriter;
    private SQLiteDatabase dbReader;
    private Context context;


    public DataBaseTool_CQUPT(Context context) {
        this.context = context;
        cqupt_db = new CQUPT_DB(context);
        dbReader = cqupt_db.getReadableDatabase();
        dbWriter = cqupt_db.getWritableDatabase();
    }
    public void addToDB(List<MessageItemCQUPT> messageItemCQUPTs){
        for(MessageItemCQUPT messageItemCQUPT:messageItemCQUPTs){
            ContentValues cv = new ContentValues();
            cv.put(CQUPT_DB.TITLE_CQUPT, messageItemCQUPT.getMessageTitle());
            cv.put(CQUPT_DB.URL_CQUPT, messageItemCQUPT.getMessageUrl());
            cv.put(CQUPT_DB.TIME_CQUPT, messageItemCQUPT.getMessageTime());
            cv.put(CQUPT_DB.PLACE_CQUPT, messageItemCQUPT.getMessagePlace());
            cv.put(CQUPT_DB.NEED_CQUPT, messageItemCQUPT.getMessageNeedNum());
            cv.put(CQUPT_DB.SCAN_CQUPT, messageItemCQUPT.getMessageViewTime());
            cv.put(CQUPT_DB.TYPE_CQUPT, messageItemCQUPT.getMessageNeedType());
            dbWriter.insert(CQUPT_DB.TABLE_TABLE_NAME_CQUPT, null, cv);
//            Log.e("插入重邮数据",messageItemCQUPT.getMessageTitle());
        }

    }

    public ArrayList<MessageItemCQUPT> selectAll() {
        String SQL = "select * from cqupt";
        Cursor cursor_cqupt = dbReader.rawQuery(SQL, null);
        ArrayList<MessageItemCQUPT> messageItemCQUPTs = null;
        if (cursor_cqupt.moveToFirst()) {
            messageItemCQUPTs = new ArrayList<>();
            for (cursor_cqupt.moveToFirst(); !cursor_cqupt.isLast(); cursor_cqupt.moveToNext()) {
                MessageItemCQUPT messageItemCQUPT = new MessageItemCQUPT();
                messageItemCQUPT.setMessageTitle(cursor_cqupt.getString(cursor_cqupt.getColumnIndex("cqupt_title")));
                messageItemCQUPT.setMessageUrl(cursor_cqupt.getString(cursor_cqupt.getColumnIndex("cqupt_url")));
                messageItemCQUPT.setMessageTime(cursor_cqupt.getString(cursor_cqupt.getColumnIndex("cqupt_time")));
                messageItemCQUPT.setMessagePlace(cursor_cqupt.getString(cursor_cqupt.getColumnIndex("cqupt_place")));
                messageItemCQUPT.setMessageNeedNum(cursor_cqupt.getString(cursor_cqupt.getColumnIndex("cqupt_need_num")));
                messageItemCQUPT.setMessageViewTime(cursor_cqupt.getString(cursor_cqupt.getColumnIndex("cqupt_scan")));
                messageItemCQUPT.setMessageNeedType(cursor_cqupt.getString(cursor_cqupt.getColumnIndex("cqupt_type")));
                messageItemCQUPTs.add(messageItemCQUPT);
            }
        }
        return messageItemCQUPTs;
    }
    public void clear() {
        String SQL = "select * from cqupt";
        String SQLDelete = "delete from cqupt";
        Cursor cursor = dbReader.rawQuery(SQL, null);
        if (cursor.moveToFirst()) {
            dbWriter.execSQL(SQLDelete);
//            Log.e("清空表","1");
        }
    }
//    public List<CNews> selectEight() {
//        String SQL = "select * from news order by _id desc limit 10 offset 0";
//        Cursor cursor_new = dbReader.rawQuery(SQL, null);
//        List<CNews> cNewses = null;
//        if (cursor_new.moveToFirst()) {
//            cNewses = new ArrayList<>();
////            for (cursor_new.moveToLast(); !cursor_new.isBeforeFirst(); cursor_new.moveToPrevious()) {
//            for (cursor_new.moveToFirst(); !cursor_new.isAfterLast(); cursor_new.moveToNext()) {
//                CNews cNews = new CNews();
//                cNews.setcUrl(cursor_new.getString(cursor_new.getColumnIndex("cnews_details_url")));
//                cNews.setcTitle(cursor_new.getString(cursor_new.getColumnIndex("cnews_details_title")));
//                cNews.setcTime(cursor_new.getString(cursor_new.getColumnIndex("cnews_details_time")));
//                cNewses.add(cNews);
//            }
//        }
//        return cNewses;
//    }
//
//    public void delete(String title) {
//        String SQL = "select cnews_details_title from news where cnews_details_title= " + title;
//        String SQLDelete = "delete from news where cnews_details_title="+title;
//        Cursor cursor = dbReader.rawQuery(SQL, null);
//        if (cursor.moveToFirst()) {
//            dbWriter.execSQL(SQLDelete);
//        }
//    }

}
