package com.cqupt.jobclient.acticity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Path;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.ClipboardManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.cqupt.jobclient.MainActivity;
import com.cqupt.jobclient.R;
import com.cqupt.jobclient.adapter.ListViewDetailsCQUPTAdapter;
import com.cqupt.jobclient.adapter.RecyclerViewDetailsCQUPTAdapter;
import com.cqupt.jobclient.adapter.RecyclerViewDetailsHKAdapter;
import com.cqupt.jobclient.adapter.RecyclerViewDetailsNYAdapter;
import com.cqupt.jobclient.adapter.RecyclerViewDetailsSCAdapter;
import com.cqupt.jobclient.adapter.RecyclerViewDetailsXDAdapter;
import com.cqupt.jobclient.adapter.RecyclerViewXDFirstAdapter;
import com.cqupt.jobclient.model.DetailsArticleCQUPT;
import com.cqupt.jobclient.model.DetailsArticleHK;
import com.cqupt.jobclient.model.DetailsArticleNY;
import com.cqupt.jobclient.model.DetailsArticleSC;
import com.cqupt.jobclient.model.DetailsArticleXD;
import com.cqupt.jobclient.model.MessageItemXDFirst;
import com.cqupt.jobclient.utils.GetData;
import com.cqupt.jobclient.utils.ShareUtil;
import com.pnikosis.materialishprogress.ProgressWheel;
import com.readystatesoftware.systembartint.SystemBarTintManager;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Administrator on 2016/3/22.
 */
public class DetailsActivity extends SwipeBackActivity {
    private String url;
    private String title;
    private String type;
    private boolean visible;
    private int times;
    private Toolbar toolbar;
    private RecyclerView recyclerView_details;
    private ProgressWheel progressWheel;
    private LinearLayout linearLayout;
    private AppBarLayout appBarLayout;
    private ArrayList<DetailsArticleSC> detailsArticleSCList;
    private RecyclerViewDetailsNYAdapter recyclerViewDetailsNYAdapter;
    private RecyclerViewDetailsCQUPTAdapter recyclerViewDetailsCQUPTAdapter;
    private RecyclerViewDetailsSCAdapter recyclerViewDetailsSCAdapter;
    private RecyclerViewDetailsXDAdapter recyclerViewDetailsXDAdapter;
    private RecyclerViewDetailsHKAdapter recyclerViewDetailsHKAdapter;
    private RecyclerViewXDFirstAdapter recyclerViewXDFirstAdapter;

//    private ListView listView;
//    private ListViewDetailsCQUPTAdapter listViewDetailsCQUPTAdapter;
    private String shareString;
    private int size;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.details_activity);

        Bundle bundle = getIntent().getExtras();
        url = bundle.getString("url");
        title = bundle.getString("title");
        type = bundle.getString("type");
        initView();
        switch (type) {
            case "SC":
                shareString = title + url + "\n四川大学就业网";
                new DetailsSCAsyncTask().execute();
            break;
            case "NY":
                shareString = title + url + "\n南京邮电大学就业网";
                new DetailsNYAsyncTask().execute();
                break;
            case "XD":
                shareString = title + url + "\n西安电子科技大学就业网";
                new DetailsXDAsyncTask().execute();
                break;
            case "HK":
                shareString = title + url + "\n华中科技大学就业网";
                new DetailsHKAsyncTask().execute();
                break;
            case "CQUPT":
                shareString = title + url + "\n重庆邮电大学就业网";
                new DetailsCQUPTAsyncTask().execute();
                break;
            case "XDFirst":
                shareString = title + url + "\n西安电子科技大学就业网";
                new DetailsXDFirstAsyncTask().execute();
                break;
            default:
                break;

        }

    }



    private void initView() {
        View decorView = getWindow().getDecorView();
        times = 0;
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        appBarLayout = (AppBarLayout) findViewById(R.id.appBar_details);
        toolbar = (Toolbar) findViewById(R.id.toolbar_details);
        recyclerView_details = (RecyclerView) findViewById(R.id.recyclerView_details);
//        listView = (ListView) findViewById(R.id.listView_details);
        progressWheel = (ProgressWheel) findViewById(R.id.progress_wheel);
        linearLayout = (LinearLayout) findViewById(R.id.main_content_details);
        showProgressWheel(true);

        recyclerView_details.setLayoutManager(new LinearLayoutManager(this));
        recyclerView_details.setItemAnimator(new DefaultItemAnimator());
//        recyclerView_details.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            private int mCurrentState = RecyclerView.SCROLL_STATE_IDLE;
//            private int lastdy = 0;
//
//            @Override
//            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
//                mCurrentState = newState;
//                super.onScrollStateChanged(recyclerView, newState);
//            }
//
//            @Override
//            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//
//                super.onScrolled(recyclerView, dx, dy);
//                times = 0;
//                if (mCurrentState == RecyclerView.SCROLL_STATE_DRAGGING || mCurrentState == RecyclerView.SCROLL_STATE_SETTLING) {
//                    if (dy < 0) {//向下滑动
//                        if(visible==false){
//                            Log.e("下拉", "");
//                            Animation animation_in = AnimationUtils.loadAnimation(app.getContext(), R.anim.toolbar_in);
//                            appBarLayout.setAnimation(animation_in);
//                            appBarLayout.setVisibility(View.VISIBLE);
//                            visible = true;
//                        }
//
//                        //可以不处理，在SwipeRefreshLayout的onRefreshListener中实现下拉刷新
//                    } else {//向上滑动
//                        if(visible==true){
//                            Log.e("上拉", "");
//                            Animation animation_out = AnimationUtils.loadAnimation(app.getContext(), R.anim.toolbar_out);
//                            appBarLayout.setAnimation(animation_out);
//                            appBarLayout.setVisibility(View.GONE);
//                            visible = false;
//                        }
//                    }
//                    lastdy = dy;
//                }
//            }
//        });
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//增加返回图标
        getSupportActionBar().setTitle(title);
    }

    //    进度条操作
    private void showProgressWheel(boolean visible) {
        progressWheel.setBarColor(getResources().getColor(R.color.dark_red));
        if (visible) {
            if (!progressWheel.isSpinning())
                progressWheel.spin();
        } else {
            progressWheel.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (progressWheel.isSpinning()) {
                        progressWheel.stopSpinning();
                    }
                }
            }, 500);
        }
    }

    class DetailsCQUPTAsyncTask extends AsyncTask<String,Void,ArrayList<DetailsArticleCQUPT>>{
        private ArrayList<DetailsArticleCQUPT> detailsArticleCQUPTs;
        @Override
        protected ArrayList<DetailsArticleCQUPT> doInBackground(String... strings) {
            detailsArticleCQUPTs = GetData.getDetailsArticleCQUPT(url);
            return detailsArticleCQUPTs;
        }

        @Override
        protected void onPostExecute(ArrayList<DetailsArticleCQUPT> DetailsArticleCQUPTs) {
            super.onPostExecute(DetailsArticleCQUPTs);
            if(detailsArticleCQUPTs==null||detailsArticleCQUPTs.size()<=0){
                Toast.makeText(app.getContext(), "网络错误", Toast.LENGTH_SHORT).show();
                showProgressWheel(false);
                return;
            }else{
                recyclerViewDetailsCQUPTAdapter = new RecyclerViewDetailsCQUPTAdapter(detailsArticleCQUPTs);
                size = detailsArticleCQUPTs.size();
//                listViewDetailsCQUPTAdapter = new ListViewDetailsCQUPTAdapter(detailsArticleCQUPTs);
//                listView.setAdapter(listViewDetailsCQUPTAdapter);
                recyclerView_details.setAdapter(recyclerViewDetailsCQUPTAdapter);
                int type = 0;
                shareString = "";
                for(DetailsArticleCQUPT detailsArticleCQUPT:detailsArticleCQUPTs) {
                    type = detailsArticleCQUPT.getType();
                    switch (type) {
                        case 0:
                            shareString = shareString +detailsArticleCQUPT.getTitle();
                            break;
                        case 1:
                            break;
                        case 2:
                            shareString = shareString + "\n"+ detailsArticleCQUPT.getText();
                            break;
                        case 3:
                            shareString = shareString +  "\n"+"附件\n" + detailsArticleCQUPT.getDocument().getDocumentUrl();
                            break;
                    }
                }
                shareString = shareString + "\n原网址:\n" + url;
                showProgressWheel(false);
            }
        }
    }
    class DetailsNYAsyncTask extends AsyncTask<String,Void,ArrayList<DetailsArticleNY>>{
        private ArrayList<DetailsArticleNY> detailsArticleNYs;
        @Override
        protected ArrayList<DetailsArticleNY> doInBackground(String... strings) {
            detailsArticleNYs = GetData.getDetailsArticleNY(url);
            return detailsArticleNYs;
        }

        @Override
        protected void onPostExecute(ArrayList<DetailsArticleNY> detailsArticleNYs) {
            super.onPostExecute(detailsArticleNYs);
            if(detailsArticleNYs==null||detailsArticleNYs.size()<=0){
                Toast.makeText(app.getContext(), "网络错误", Toast.LENGTH_SHORT).show();
                showProgressWheel(false);
                return;
            }else{
                recyclerViewDetailsNYAdapter = new RecyclerViewDetailsNYAdapter(detailsArticleNYs);
                recyclerView_details.setAdapter(recyclerViewDetailsNYAdapter);
                int type = 0;
                shareString = "";
                for(DetailsArticleNY detailsArticleNY:detailsArticleNYs) {
                    type = detailsArticleNY.getType();
                    switch (type) {
                        case 0:
                            shareString = shareString +detailsArticleNY.getTitle();
                            break;
                        case 1:
                            shareString = shareString + "\n"+ detailsArticleNY.getText();
                            break;
                        case 2:
                            shareString = shareString + "\n"+ detailsArticleNY.getText();
                            break;
                    }
                }
                shareString = shareString + "\n原网址:\n" + url;
                showProgressWheel(false);
            }
        }
    }

    class DetailsXDAsyncTask extends AsyncTask<String,Void,ArrayList<DetailsArticleXD>>{
        private ArrayList<DetailsArticleXD> detailsArticleXDs;
        @Override
        protected ArrayList<DetailsArticleXD> doInBackground(String... strings) {
            detailsArticleXDs = GetData.getDetailsArticleXD(url);
            return detailsArticleXDs;
        }

        @Override
        protected void onPostExecute(ArrayList<DetailsArticleXD> detailsArticleXDs) {
            super.onPostExecute(detailsArticleXDs);
            if(detailsArticleXDs==null||detailsArticleXDs.size()<=0){
                Toast.makeText(app.getContext(), "网络错误", Toast.LENGTH_SHORT).show();
                showProgressWheel(false);
                return;
            }
            recyclerViewDetailsXDAdapter = new RecyclerViewDetailsXDAdapter(detailsArticleXDs);
            recyclerView_details.setAdapter(recyclerViewDetailsXDAdapter);
            int type = 0;
            shareString = "";
            for(DetailsArticleXD detailsArticleXD:detailsArticleXDs) {
                type = detailsArticleXD.getType();
                switch (type) {
                    case 0:
                        shareString = shareString +detailsArticleXD.getTitle();
                        break;
                    case 1:
                        shareString = shareString + "\n"+ detailsArticleXD.getTime();
                        break;
                    case 2:
                        shareString = shareString + "\n"+ detailsArticleXD.getText();
                        break;
                }
            }
            shareString = shareString + "\n原网址:\n" + url;
            showProgressWheel(false);
        }
    }

    class DetailsHKAsyncTask extends AsyncTask<String,Void,ArrayList<DetailsArticleHK>>{
        private ArrayList<DetailsArticleHK> detailsArticleHKs;
        @Override
        protected ArrayList<DetailsArticleHK> doInBackground(String... strings) {
            detailsArticleHKs = GetData.getDetailsArticleHK(url);
            return detailsArticleHKs;
        }

        @Override
        protected void onPostExecute(ArrayList<DetailsArticleHK> detailsArticleHKs) {
            super.onPostExecute(detailsArticleHKs);
            if(detailsArticleHKs==null||detailsArticleHKs.size()<=0){
                Toast.makeText(app.getContext(), "网络错误", Toast.LENGTH_SHORT).show();
                showProgressWheel(false);
                return;
            }
            recyclerViewDetailsHKAdapter = new RecyclerViewDetailsHKAdapter(detailsArticleHKs);
            recyclerView_details.setAdapter(recyclerViewDetailsHKAdapter);
            int type = 0;
            shareString = "";
            for(DetailsArticleHK detailsArticleHK:detailsArticleHKs) {
                type = detailsArticleHK.getType();
                switch (type) {
                    case 0:
                        shareString = shareString +detailsArticleHK.getTitle();
                        break;
                    case 1:
                        shareString = shareString + "\n"+ detailsArticleHK.getTime();
                        break;
                    case 2:
                        shareString = shareString + "\n"+ detailsArticleHK.getText();
                        break;
                }
            }
            shareString = shareString + "\n原网址:\n" + url;
            showProgressWheel(false);
        }
    }

    class DetailsSCAsyncTask extends AsyncTask<String,Void,ArrayList<DetailsArticleSC>>{
        private ArrayList<DetailsArticleSC> detailsArticleSCs;
        @Override
        protected ArrayList<DetailsArticleSC> doInBackground(String... strings) {
            detailsArticleSCs = GetData.getDetailsArticleSC(url);
            return detailsArticleSCs;
        }

        @Override
        protected void onPostExecute(ArrayList<DetailsArticleSC> detailsArticleSCs) {
            super.onPostExecute(detailsArticleSCs);
            if(detailsArticleSCs==null||detailsArticleSCs.size()<=0){
                Toast.makeText(app.getContext(), "网络错误", Toast.LENGTH_SHORT).show();
                showProgressWheel(false);
                return;
            }
            recyclerViewDetailsSCAdapter = new RecyclerViewDetailsSCAdapter(detailsArticleSCs);
            recyclerView_details.setAdapter(recyclerViewDetailsSCAdapter);
            int type = 0;
            shareString = "";
            for(DetailsArticleSC detailsArticleSC:detailsArticleSCs) {
                type = detailsArticleSC.getType();
                switch (type) {
                    case 0:
                        shareString = shareString +detailsArticleSC.getMainTitle();
                        break;
                    case 1:
                        shareString = shareString + "\n"+ detailsArticleSC.getTime();
                        break;
                    case 2:
                        shareString = shareString + "\n"+ detailsArticleSC.getTitle();
                        break;
                    case 3:
                        shareString = shareString + "\n"+ detailsArticleSC.getText();
                        break;
                    case 4:
                        shareString = shareString + "\n"+ detailsArticleSC.getRecruit();
                        break;
                }
            }
            shareString = shareString + "\n原网址:\n" + url;
            showProgressWheel(false);
        }
    }

    class DetailsXDFirstAsyncTask extends AsyncTask<String,Void,ArrayList<MessageItemXDFirst>>{
        private ArrayList<MessageItemXDFirst> messageItemXDFirsts;
        @Override
        protected ArrayList<MessageItemXDFirst> doInBackground(String... strings) {
            messageItemXDFirsts = GetData.getMessageItemXDFirst();
            return messageItemXDFirsts;
        }

        @Override
        protected void onPostExecute(ArrayList<MessageItemXDFirst> messageItemXDFirsts) {
            super.onPostExecute(messageItemXDFirsts);
            if(messageItemXDFirsts==null||messageItemXDFirsts.size()<=0){
                Toast.makeText(app.getContext(), "网络错误", Toast.LENGTH_SHORT).show();
                showProgressWheel(false);
                return;
            }
            recyclerViewXDFirstAdapter = new RecyclerViewXDFirstAdapter(DetailsActivity.this,messageItemXDFirsts);
            recyclerView_details.setAdapter(recyclerViewXDFirstAdapter);
            showProgressWheel(false);
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_details,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        if(item.getItemId()==R.id.action_share) {
            ShareUtil.shareText(this, shareString);
            return true;
        }
        if(item.getItemId()==R.id.action_cut) {
            String path = "";
            Bitmap bitmap = null;
            try {
                bitmap = ShareUtil.myShot_recyclerView(recyclerView_details);
                path = ShareUtil.saveToSD_recyclerView(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
            ShareUtil.sharePicture(this, path,"求职信息");
            return true;
        }

        if(item.getItemId()==R.id.action_copy){
            ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            // 将文本内容放到系统剪贴板里。
            cm.setText(shareString);
            Toast.makeText(app.getContext(), "复制成功，可以发给朋友们了。", Toast.LENGTH_LONG).show();
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.base_slide_right_in, R.anim.base_slide_right_out);
    }
}
