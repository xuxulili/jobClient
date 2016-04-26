package com.cqupt.jobclient.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;


import com.cqupt.jobclient.R;
import com.cqupt.jobclient.acticity.app;
import com.cqupt.jobclient.adapter.RecyclerViewCQUPTAdapter;
import com.cqupt.jobclient.adapter.RecyclerViewDetailsCQUPTAdapter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by zhaokaiqiang on 15/4/17.
 */
public class ShareUtil {


    public static void shareText(Activity activity, String shareText) {

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT,
                shareText);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(Intent.createChooser(intent, activity.getResources().getString(R
                .string.app_name)));
    }

    public static void sharePicture(Activity activity, String imgPath, String shareText) {

        Intent intent = new Intent(Intent.ACTION_SEND);
        File f = new File(imgPath);
        if (f != null && f.exists() && f.isFile()) {
            intent.setType("image/*");
            intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(f));
        } else {
//            Toast.makeText(app.getContext(), "�����ͼƬ������", Toast.LENGTH_SHORT).show();
            return;
        }

        //GIFͼƬָ������url������ͼƬָ����Ŀ��ַ
        if (imgPath.endsWith(".gif")) {
            intent.putExtra(Intent.EXTRA_TEXT, shareText);
        } else {
            intent.putExtra(Intent.EXTRA_TEXT, shareText);
        }

        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(Intent.createChooser(intent, activity.getResources().getString(R
                .string.app_name)));
    }


    public static Bitmap createBitmap(ListView listView) {
        int titleHeight, width, height, rootHeight = 0;
        Bitmap bitmap;
        Canvas canvas;
        int yPos = 0;
        int listItemNum;
        List<View> childViews = null;
        WindowManager wm = (WindowManager) app.getContext()
                .getSystemService(Context.WINDOW_SERVICE);


        width = wm.getDefaultDisplay().getWidth();
        ;//��ȵ�����Ļ��

        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter != null) {
            listItemNum = listAdapter.getCount();
        } else {
            return null;
        }
        childViews = new ArrayList<View>(listItemNum);
        View itemView;
        //��������߶�:
        for (int pos = 0; pos < listItemNum; ++pos) {
            itemView = listAdapter.getView(pos, null, null);
            //measure����
            itemView.measure(View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.EXACTLY), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
            childViews.add(itemView);
            rootHeight += itemView.getMeasuredHeight();
        }

        height = rootHeight;
        bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        canvas = new Canvas(bitmap);

        Bitmap itemBitmap;
        View item;
        int childHeight;
        //��ÿ��ItemView����ͼƬ������������������
        for (int pos = 0; pos < childViews.size(); ++pos) {
            item = childViews.get(pos);
            item.setBackgroundColor(Color.parseColor("#ffffff"));
            childHeight = item.getMeasuredHeight();
            itemBitmap = viewToBitmap(item, width, childHeight);
            if (itemBitmap != null) {
                canvas.drawBitmap(itemBitmap, 0, yPos, null);
            }
            yPos = childHeight + yPos;
        }

        canvas.save(Canvas.ALL_SAVE_FLAG);
        canvas.restore();
        return bitmap;
    }

    private static Bitmap viewToBitmap(View view, int viewWidth, int viewHeight) {
        view.layout(0, 0, viewWidth, viewHeight);
        view.buildDrawingCache();
        Bitmap bitmap = view.getDrawingCache();
        return bitmap;
    }


    public static Bitmap myShot(Activity activity) {
        // ��ȡwindows������view
        View view = activity.getWindow().getDecorView();
        view.buildDrawingCache();

        // ��ȡ״̬���߶�
        Rect rect = new Rect();
        view.getWindowVisibleDisplayFrame(rect);
        int statusBarHeights = rect.top;
        Display display = activity.getWindowManager().getDefaultDisplay();

        // ��ȡ��Ļ��͸�
        int widths = display.getWidth();
        int heights = display.getHeight();

        // ����ǰ���ڱ��滺����Ϣ
        view.setDrawingCacheEnabled(true);

        // ȥ��״̬��
        Bitmap bmp = Bitmap.createBitmap(view.getDrawingCache(), 0,
                statusBarHeights, widths, heights - statusBarHeights);

        // ���ٻ�����Ϣ
        view.destroyDrawingCache();

        return bmp;
    }

    public static Bitmap myShot_recyclerView(RecyclerView recyclerView) {
        int h = 0;
        Bitmap bitmap = null;
        // ��ȡlistViewʵ�ʸ߶�
        recyclerView.setDrawingCacheEnabled(true);
        recyclerView.buildDrawingCache();
        Bitmap b1 = recyclerView.getDrawingCache();
        RecyclerViewDetailsCQUPTAdapter recyclerViewDetailsCQUPTAdapter = (RecyclerViewDetailsCQUPTAdapter) recyclerView.getAdapter();
        for (int i = 0; i < recyclerView.getChildCount(); i++) {
                h += recyclerView.getChildAt(i).getHeight();
        }
        recyclerView.setBackgroundColor(
                Color.parseColor("#ffffff"));
        Log.d("����", "ʵ�ʸ߶�:" + h);
        Log.d("����", "list �߶�:" + recyclerView.getHeight());
        // ������Ӧ��С��bitmap
        bitmap = Bitmap.createBitmap( recyclerView.getWidth(), h, Bitmap.Config.ARGB_8888);
//        bitmap = Bitmap.createBitmap(recyclerView.getWidth(), h,
//                Bitmap.Config.ARGB_8888);
        final Canvas canvas = new Canvas(bitmap);
        recyclerView.draw(canvas);

//        // ����ǰ���ڱ��滺����Ϣ
//        view.setDrawingCacheEnabled(true);
//
//        // ȥ��״̬��
//        Bitmap bmp = Bitmap.createBitmap(view.getDrawingCache(), 0,
//                statusBarHeights, widths, heights - statusBarHeights);
//
//        // ���ٻ�����Ϣ
//        view.destroyDrawingCache();

        return bitmap;
    }

    //��ָ��ͼƬ���뱾��
    public static String saveToSD_recyclerView(Bitmap bitmap ) throws IOException {
        String dirName = "";
        String fileName = "";
        File file;
        // �ж�sd���Ƿ����
//        Bitmap bitmap=getBitmapByView(activity);
        Bitmap bmp = bitmap;
//        Log.e("������ȡ��ͼƬ", bmp + "");
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            dirName = Environment.getExternalStorageDirectory() + "/myShare/";
        } else {
//            dirName=activity.getCacheDir()+"/myShare/";
            dirName = app.getContext().getFilesDir().getPath() + "/myShare/";
        }


        SimpleDateFormat formatter = new SimpleDateFormat("MM��dd��HHmmss");
        Date curDate = new Date(System.currentTimeMillis());//��ȡ��ǰʱ��
        String str = formatter.format(curDate);
        if (!str.equals("")) {
            fileName = str + ".png";
//            Log.e("fileName",fileName);
        }

//        �ж��ļ����Ƿ���ڣ��������򴴽�
        File dir = new File(dirName);
        if (!dir.exists()) {
            dir.mkdir();
//                Log.e("�½������ڵ�·��", dir+"");
        }
        //�г�ָ��Ŀ¼�������ļ�
        if (dir.exists() && dir.isDirectory()) {
            if (dir.listFiles().length != 0) {
                File[] files = dir.listFiles();

                for (File f : files) {
//                    Log.e("��ͼ�ļ��б�", "file is " + f);
                }
            }
        }

        file = new File(dirName, fileName);
        // �ж��ļ��Ƿ���ڣ��������򴴽�
        if (!file.exists()) {
            file.createNewFile();
        }
//        Log.e("����ͼƬ��ַ", file + "");
        FileOutputStream fos = null;
        try {
            if (Environment.getExternalStorageState().equals(
                    Environment.MEDIA_MOUNTED)) {
                fos = new FileOutputStream(file);
            } else {
                fos = app.getContext().openFileOutput(fileName, Context.MODE_WORLD_WRITEABLE | Context.MODE_WORLD_READABLE);
            }
            if (fos != null) {
                // ��һ������ͼƬ��ʽ���ڶ�����ͼƬ�������������������
//                bmp.compress(Bitmap.CompressFormat.PNG, 100, fos);
                bmp.compress(Bitmap.CompressFormat.PNG, 100, fos);
//                Log.e("�洢ͼƬ����", "");
                // ����ر�
                fos.flush();
                fos.close();
                Toast.makeText(app.getContext(), "��ͼ���", Toast.LENGTH_SHORT).show();
                return (dirName + fileName);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    //��ָ��ͼƬ���뱾��
    public static String saveToSD(Activity activity) throws IOException {
        String dirName = "";
        String fileName = "";
        File file;
        // �ж�sd���Ƿ����
//        Bitmap bitmap=getBitmapByView(activity);
        Bitmap bmp = myShot(activity);
//        Log.e("������ȡ��ͼƬ", bmp + "");
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            dirName = Environment.getExternalStorageDirectory() + "/myShare/";
        } else {
//            dirName=activity.getCacheDir()+"/myShare/";
            dirName = app.getContext().getFilesDir().getPath() + "/myShare/";
        }


        SimpleDateFormat formatter = new SimpleDateFormat("MM��dd��HHmmss");
        Date curDate = new Date(System.currentTimeMillis());//��ȡ��ǰʱ��
        String str = formatter.format(curDate);
        if (!str.equals("")) {
            fileName = str + ".png";
//            Log.e("fileName",fileName);
        }

//        �ж��ļ����Ƿ���ڣ��������򴴽�
        File dir = new File(dirName);
        if (!dir.exists()) {
            dir.mkdir();
//                Log.e("�½������ڵ�·��", dir+"");
        }
        //�г�ָ��Ŀ¼�������ļ�
        if (dir.exists() && dir.isDirectory()) {
            if (dir.listFiles().length != 0) {
                File[] files = dir.listFiles();

                for (File f : files) {
//                    Log.e("��ͼ�ļ��б�", "file is " + f);
                }
            }
        }

        file = new File(dirName, fileName);
        // �ж��ļ��Ƿ���ڣ��������򴴽�
        if (!file.exists()) {
            file.createNewFile();
        }
//        Log.e("����ͼƬ��ַ", file + "");
        FileOutputStream fos = null;
        try {
            if (Environment.getExternalStorageState().equals(
                    Environment.MEDIA_MOUNTED)) {
                fos = new FileOutputStream(file);
            } else {
                fos = app.getContext().openFileOutput(fileName, Context.MODE_WORLD_WRITEABLE | Context.MODE_WORLD_READABLE);
            }
            if (fos != null) {
                // ��һ������ͼƬ��ʽ���ڶ�����ͼƬ�������������������
//                bmp.compress(Bitmap.CompressFormat.PNG, 100, fos);
                bmp.compress(Bitmap.CompressFormat.PNG, 100, fos);
//                Log.e("�洢ͼƬ����", "");
                // ����ر�
                fos.flush();
                fos.close();
                Toast.makeText(app.getContext(), "��ͼ���", Toast.LENGTH_SHORT).show();
                return (dirName + fileName);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
