package com.cqupt.jobclient.acticity;

import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.cqupt.jobclient.MainActivity;
import com.cqupt.jobclient.R;
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
import com.pnikosis.materialishprogress.ProgressWheel;
import com.readystatesoftware.systembartint.SystemBarTintManager;

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
                new DetailsSCAsyncTask().execute();
            break;
            case "NY":
                new DetailsNYAsyncTask().execute();
                break;
            case "XD":
                new DetailsXDAsyncTask().execute();
                break;
            case "HK":
                new DetailsHKAsyncTask().execute();
                break;
            case "CQUPT":
                new DetailsCQUPTAsyncTask().execute();
                break;
            case "XDFirst":
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
                return;
            }else{
                recyclerViewDetailsCQUPTAdapter = new RecyclerViewDetailsCQUPTAdapter(detailsArticleCQUPTs);
                recyclerView_details.setAdapter(recyclerViewDetailsCQUPTAdapter);
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
        protected void onPostExecute(ArrayList<DetailsArticleNY> DetailsArticleNYs) {
            super.onPostExecute(DetailsArticleNYs);
            if(detailsArticleNYs==null||detailsArticleNYs.size()<=0){
                return;
            }else{
                recyclerViewDetailsNYAdapter = new RecyclerViewDetailsNYAdapter(detailsArticleNYs);
                recyclerView_details.setAdapter(recyclerViewDetailsNYAdapter);
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
                return;
            }
            recyclerViewDetailsXDAdapter = new RecyclerViewDetailsXDAdapter(detailsArticleXDs);
            recyclerView_details.setAdapter(recyclerViewDetailsXDAdapter);
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
                return;
            }
            recyclerViewDetailsHKAdapter = new RecyclerViewDetailsHKAdapter(detailsArticleHKs);
            recyclerView_details.setAdapter(recyclerViewDetailsHKAdapter);
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
                return;
            }
            recyclerViewDetailsSCAdapter = new RecyclerViewDetailsSCAdapter(detailsArticleSCs);
            recyclerView_details.setAdapter(recyclerViewDetailsSCAdapter);
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
                return;
            }
            recyclerViewXDFirstAdapter = new RecyclerViewXDFirstAdapter(DetailsActivity.this,messageItemXDFirsts);
            recyclerView_details.setAdapter(recyclerViewXDFirstAdapter);
            showProgressWheel(false);
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.base_slide_right_in, R.anim.base_slide_right_out);
    }
}
