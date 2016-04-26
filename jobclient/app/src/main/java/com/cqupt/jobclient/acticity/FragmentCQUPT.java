package com.cqupt.jobclient.acticity;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.Time;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.cqupt.jobclient.R;
import com.cqupt.jobclient.adapter.RecyclerViewCQUPTAdapter;
import com.cqupt.jobclient.adapter.RecyclerViewHKAdapter;
import com.cqupt.jobclient.model.MessageItemCQUPT;
import com.cqupt.jobclient.model.MessageItemHK;
import com.cqupt.jobclient.model.NewsPicture;
import com.cqupt.jobclient.utils.DB.CQUPT_DB;
import com.cqupt.jobclient.utils.DB.DataBaseTool_CQUPT;
import com.cqupt.jobclient.utils.GetData;
import com.cqupt.jobclient.utils.NetWorkUtil;
import com.pnikosis.materialishprogress.ProgressWheel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Timer;

/**
 * Created by Administrator on 2016/4/20.
 */
public class FragmentCQUPT extends Fragment {
    private boolean isVisible;
    private boolean hasLoad;
    private View view;
    private RecyclerView recyclerViewCQUPT;
    private SwipeRefreshLayout swipeRefreshLayoutCQUPT;
    private LinearLayoutManager linearLayoutManager;
    private ArrayList<MessageItemCQUPT> messageItemCQUPTList;
    private RecyclerViewCQUPTAdapter recyclerViewCQUPTAdapter;
    private ProgressWheel progressWheel;
    private String curDate;
    private String dateRemain;
    private DataBaseTool_CQUPT dataBaseTool_cqupt;
    private boolean firstLoad;
    private boolean isLoading;
    private int hasLoadPicture;
    private ArrayList<NewsPicture> newsPictureArrayList;
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(getUserVisibleHint()) {

            isVisible = true;
            if(NetWorkUtil.isNetWorkConnected(app.getContext())&&isVisible&&!hasLoad) {
                curDate = getCurTime();
                firstLoad = true;
                new CQUPTAsyncTask().execute(curDate);
                hasLoad = true;
            }else {
                if(!hasLoad){
                    messageItemCQUPTList = dataBaseTool_cqupt.selectAll();
                    hasLoad = true;
                }
//                Toast.makeText(app.getContext(), "加载缓存数据", Toast.LENGTH_SHORT).show();

            }
        }else {
            isVisible = false;
        }
    }

    private OnLoadingListener onLoadingListener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        onLoadingListener = (OnLoadingListener) activity;

        dataBaseTool_cqupt = new DataBaseTool_CQUPT(app.getContext());
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String date = sDateFormat.format(new java.util.Date());
        dateRemain = date;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.basefragmentlayout, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
    }

    private void initView() {
        progressWheel = (ProgressWheel) view.findViewById(R.id.progress_wheel);
        showProgressWheel(true);
        recyclerViewCQUPT = (RecyclerView) view.findViewById(R.id.recyclerView);
        swipeRefreshLayoutCQUPT = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh);
        swipeRefreshLayoutCQUPT.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        swipeRefreshLayoutCQUPT.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (NetWorkUtil.isNetWorkConnected(app.getContext())) {
                    curDate = getCurTime();
                    firstLoad = true;
                    dateRemain = curDate;
                    new CQUPTAsyncTask().execute(curDate);
                } else {
                    Toast.makeText(app.getContext(), "无网络连接", Toast.LENGTH_SHORT).show();
                    resetSwipeLayout();
                }
            }
        });
        recyclerViewCQUPT = (RecyclerView) view.findViewById(R.id.recyclerView);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewCQUPT.setHasFixedSize(true);
        recyclerViewCQUPT.setLayoutManager(linearLayoutManager);
        recyclerViewCQUPT.setItemAnimator(new DefaultItemAnimator());
        recyclerViewCQUPT.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (swipeRefreshLayoutCQUPT.isRefreshing()) {
                    return true;
                } else {
                    return false;
                }

            }
        });
        recyclerViewCQUPT.addOnScrollListener(new RecyclerView.OnScrollListener() {
            private int mCurrentState = RecyclerView.SCROLL_STATE_IDLE;
            private int lastdy = 0;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                mCurrentState = newState;
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (mCurrentState == RecyclerView.SCROLL_STATE_DRAGGING || mCurrentState == RecyclerView.SCROLL_STATE_SETTLING) {

                    if (dy < 0) {//向下滑动

                        //可以不处理，在SwipeRefreshLayout的onRefreshListener中实现下拉刷新
                    } else {//向上滑动
                        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                        if (layoutManager instanceof LinearLayoutManager) {
                            int lastitem = linearLayoutManager.findLastCompletelyVisibleItemPosition();
                            if (recyclerView.getAdapter().getItemCount() == lastitem + 1) {
                                if (NetWorkUtil.isNetWorkConnected(app.getContext())) {
                                    String nextTime = getNextTime(dateRemain);
                                    swipeRefreshLayoutCQUPT.setRefreshing(true);
                                    swipeRefreshLayoutCQUPT.setEnabled(false);
                                    new CQUPTAsyncTask().execute(nextTime);
                                } else {
                                    Toast.makeText(app.getContext(), "无网络连接", Toast.LENGTH_SHORT).show();
                                }

                            }
                        }
                    }

                    lastdy = dy;
                }
                super.onScrolled(recyclerView, dx, dy);
            }
        });
//        用于无网络加载缓存数据
        if(messageItemCQUPTList!=null){
            recyclerViewCQUPTAdapter = new RecyclerViewCQUPTAdapter(getActivity(), messageItemCQUPTList);
            recyclerViewCQUPT.setAdapter(recyclerViewCQUPTAdapter);
            showProgressWheel(false);
        }
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

    public String getCurTime() {
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String date = sDateFormat.format(new java.util.Date());
        return date;
    }

    public  String getNextTime(String date) {
        String nextTime = "";
        Calendar c = Calendar.getInstance();
        int day = Integer.parseInt(date.substring(date.length() - 2, date.length()));
        int month = Integer.parseInt(date.substring(5, date.length()-3));
        int year = Integer.parseInt(date.substring(0, 4));
        int num = 0;
        c.set(Calendar.YEAR,year);
        c.set(Calendar.MONTH,month-1);
        c.set(Calendar.DAY_OF_MONTH,day);
        num = c.get(Calendar.DAY_OF_MONTH)+7;
        c.set(Calendar.DAY_OF_MONTH, num);
        int monthRemain = c.get(Calendar.MONTH) + 1;
        String monthStr = "";
        int dayRemain = c.get(Calendar.DAY_OF_MONTH);
        String dayStr = "";
        if(dayRemain<10) {
            dayStr = "0" + dayRemain;
        }else {
            dayStr = "" + dayRemain;
        }
        if(monthRemain<10) {
            monthStr = "0" + monthRemain;
        }else {
            monthStr = "" + monthRemain;
        }
        dateRemain = "";
        dateRemain = c.get(Calendar.YEAR) + "-" + monthStr+ "-" + dayStr;
        nextTime = dateRemain;
        return nextTime;
    }

    private void resetSwipeLayout(){
        if(swipeRefreshLayoutCQUPT.isRefreshing()){
            swipeRefreshLayoutCQUPT.setRefreshing(false);
        }
        swipeRefreshLayoutCQUPT.setEnabled(true);
    }

    class CQUPTAsyncTask extends AsyncTask<String,Void,ArrayList<MessageItemCQUPT>> {
        ArrayList<MessageItemCQUPT> messageItemCQUPTs = null;
        String date = "";
        @Override
        protected ArrayList<MessageItemCQUPT> doInBackground(String... strings) {
            messageItemCQUPTs = GetData.getMessageItemCQUT(strings[0]);
            date = strings[0];
            if(hasLoadPicture==1) {
                newsPictureArrayList = GetData.getNewsPicture();
            }
            hasLoadPicture ++;
            return messageItemCQUPTs;
        }

        @Override
        protected void onPostExecute(ArrayList<MessageItemCQUPT> messageItemCQUPTs) {
            super.onPostExecute(messageItemCQUPTs);
            if(messageItemCQUPTs==null||messageItemCQUPTs.size()<=0){
                Toast.makeText(app.getContext(), "无数据，请重新加载", Toast.LENGTH_SHORT).show();
                resetSwipeLayout();
//                待重新获取
                return;
            }

            if(firstLoad){
                if(messageItemCQUPTList!=null){
                    messageItemCQUPTList.clear();
                }
//                清理数据库前加此句，有可能出现执行清空数据操作后，不存在这个表
                dataBaseTool_cqupt = new DataBaseTool_CQUPT(app.getContext());
                dataBaseTool_cqupt.clear();
                messageItemCQUPTList = new ArrayList<>();
                messageItemCQUPTList.addAll(messageItemCQUPTs);
                dataBaseTool_cqupt.addToDB(messageItemCQUPTs);
                //！！！注意当messageItemCQUPTList被清空一次后，需要重新创建适配器对象，否则即使messageItemCQUPTList
                // 有数据，列表也更新为空，
                recyclerViewCQUPTAdapter = new RecyclerViewCQUPTAdapter(getActivity(),messageItemCQUPTList);
                recyclerViewCQUPT.setAdapter(recyclerViewCQUPTAdapter);
                firstLoad = false;

            }else {
                if(messageItemCQUPTList==null){
                    messageItemCQUPTList = new ArrayList<>();
                }
                messageItemCQUPTList.addAll(messageItemCQUPTs);
                dataBaseTool_cqupt.addToDB(messageItemCQUPTs);
            }
            if(messageItemCQUPTList.size()<6) {
                String getTime = getNextTime(dateRemain);
                new CQUPTAsyncTask().execute(getTime);

            }else{
                if(recyclerViewCQUPTAdapter == null) {
                    recyclerViewCQUPTAdapter = new RecyclerViewCQUPTAdapter(getActivity(),messageItemCQUPTList);
                    recyclerViewCQUPT.setAdapter(recyclerViewCQUPTAdapter);
                }
                recyclerViewCQUPTAdapter.notifyDataSetChanged();
                showProgressWheel(false);
                resetSwipeLayout();
                if(hasLoadPicture==2){
                    onLoadingListener.onLoad(newsPictureArrayList);
                }
            }
        }
    }
}
