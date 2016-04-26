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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.cqupt.jobclient.R;
import com.cqupt.jobclient.adapter.RecyclerViewHKAdapter;
import com.cqupt.jobclient.adapter.RecyclerViewNYAdapter;
import com.cqupt.jobclient.adapter.RecyclerViewXDAdapter;
import com.cqupt.jobclient.model.MessageItemHK;
import com.cqupt.jobclient.utils.DB.DataBaseTool_HK;
import com.cqupt.jobclient.utils.GetData;
import com.cqupt.jobclient.utils.NetWorkUtil;
import com.pnikosis.materialishprogress.ProgressWheel;

import java.util.ArrayList;
import java.util.zip.Inflater;

/**
 * Created by Administrator on 2016/3/18.
 */
public class FragmentHK extends Fragment {
    private boolean isVisible;
    private boolean hasLoad;
    private int pageLoad;
    private View view;
    private RecyclerView recyclerViewHK;
    private SwipeRefreshLayout swipeRefreshLayoutHK;
    private LinearLayoutManager linearLayoutManager;
    private ArrayList<MessageItemHK> messageItemHKList;
    private RecyclerViewHKAdapter recyclerViewHKAdapter;
    private ProgressWheel progressWheel;
    private DataBaseTool_HK dataBaseTool_hk;
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(getUserVisibleHint()) {
            isVisible = true;
            if(NetWorkUtil.isNetWorkConnected(app.getContext())&&isVisible&&!hasLoad) {
                pageLoad = 0;
                new HKAsyncTask().execute(String.valueOf(pageLoad));
                hasLoad = true;
            }else {
                if(!hasLoad){
                    messageItemHKList = dataBaseTool_hk.selectAll();
//                注意此处页面已经初始化，不会出现recyclerView空值的问题，由于先初始化假如在initView中适配
//                会出现messageItemSCList空值的问题
                    if(messageItemHKList!=null) {
                        recyclerViewHKAdapter = new RecyclerViewHKAdapter(getActivity(), messageItemHKList);
                        recyclerViewHK.setAdapter(recyclerViewHKAdapter);
                        showProgressWheel(false);
                    }
//                Toast.makeText(app.getContext(), "加载缓存数据", Toast.LENGTH_SHORT).show();
                    hasLoad = true;
                }

            }
        }else {
            isVisible = false;
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        dataBaseTool_hk = new DataBaseTool_HK(app.getContext());
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
    private void resetSwipeLayout(){
        if(swipeRefreshLayoutHK.isRefreshing()){
            swipeRefreshLayoutHK.setRefreshing(false);
        }
        swipeRefreshLayoutHK.setEnabled(true);
    }

    private void initView() {
        progressWheel = (ProgressWheel) view.findViewById(R.id.progress_wheel);
        showProgressWheel(true);
        recyclerViewHK = (RecyclerView) view.findViewById(R.id.recyclerView);
        swipeRefreshLayoutHK = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh);
        swipeRefreshLayoutHK.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        swipeRefreshLayoutHK.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (NetWorkUtil.isNetWorkConnected(app.getContext())) {
                    pageLoad = 0;
                    new HKAsyncTask().execute(String.valueOf(pageLoad));
                } else {
                    Toast.makeText(app.getContext(), "无网络连接", Toast.LENGTH_SHORT).show();
                    resetSwipeLayout();
                }
            }
        });
        recyclerViewHK = (RecyclerView) view.findViewById(R.id.recyclerView);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewHK.setHasFixedSize(true);
        recyclerViewHK.setLayoutManager(linearLayoutManager);
        recyclerViewHK.setItemAnimator(new DefaultItemAnimator());
        recyclerViewHK.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (swipeRefreshLayoutHK.isRefreshing()) {
                    return true;
                } else {
                    return false;
                }

            }
        });
        recyclerViewHK.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
                                if(NetWorkUtil.isNetWorkConnected(app.getContext())){
                                    pageLoad++;
                                    swipeRefreshLayoutHK.setRefreshing(true);
                                    swipeRefreshLayoutHK.setEnabled(false);
                                    new HKAsyncTask().execute(String.valueOf(pageLoad));
                                }else {
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
    class HKAsyncTask extends AsyncTask<String,Void,ArrayList<MessageItemHK>>{
        ArrayList<MessageItemHK> messageItemHKs;
        @Override
        protected ArrayList<MessageItemHK> doInBackground(String... strings) {
            messageItemHKs = GetData.getMessageItemHK(Integer.parseInt(strings[0]));
            return messageItemHKs;
        }

        @Override
        protected void onPostExecute(ArrayList<MessageItemHK> messageItemHKs) {
            super.onPostExecute(messageItemHKs);
            if(messageItemHKs==null||messageItemHKs.size()<=0){
                Toast.makeText(app.getContext(), "数据加载失败", Toast.LENGTH_SHORT).show();
                showProgressWheel(false);
                resetSwipeLayout();
//                待重新获取
                return;
            }
            if(pageLoad==0){
                if(messageItemHKList!=null){
                    messageItemHKList.clear();
                }
                dataBaseTool_hk.clear();
                messageItemHKList = new ArrayList<>();
                messageItemHKList.addAll(messageItemHKs);
                dataBaseTool_hk.addToDB(messageItemHKs);
                recyclerViewHKAdapter = new RecyclerViewHKAdapter(getActivity(),messageItemHKList);
                recyclerViewHK.setAdapter(recyclerViewHKAdapter);
                if(recyclerViewHKAdapter.getItemCount()>0){
                    showProgressWheel(false);
                }
            }else {
                messageItemHKList.addAll(messageItemHKs);
                dataBaseTool_hk.addToDB(messageItemHKs);
            }
            if(recyclerViewHKAdapter==null){
                recyclerViewHKAdapter = new RecyclerViewHKAdapter(getActivity(),messageItemHKList);
                recyclerViewHK.setAdapter(recyclerViewHKAdapter);
            }
            recyclerViewHKAdapter.notifyDataSetChanged();
            if(swipeRefreshLayoutHK.isRefreshing()){
                swipeRefreshLayoutHK.setRefreshing(false);
            }
            swipeRefreshLayoutHK.setEnabled(true);
        }
    }
}
