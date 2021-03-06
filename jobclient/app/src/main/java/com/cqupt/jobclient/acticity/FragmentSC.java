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
import com.cqupt.jobclient.adapter.RecyclerViewSCAdapter;
import com.cqupt.jobclient.model.MessageItemSC;
import com.cqupt.jobclient.utils.DB.DataBaseTool_SC;
import com.cqupt.jobclient.utils.GetData;
import com.cqupt.jobclient.utils.NetWorkUtil;
import com.pnikosis.materialishprogress.ProgressWheel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/3/18.
 */
public class FragmentSC  extends Fragment {
    private View view;
    private SwipeRefreshLayout swipeRefreshLayoutSC;
    private RecyclerView recyclerViewSC;
    private LinearLayoutManager linearLayoutManager;
    private ArrayList<MessageItemSC> messageItemSCList;
    private boolean isVisible;
    private boolean hasLoad;
    private boolean isRefreshing;
    private int pageLoad;
    private ProgressWheel progressWheel;
    private RecyclerViewSCAdapter recyclerViewSCAdapter;
    private DataBaseTool_SC dataBaseTool_sc;
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if(getUserVisibleHint()) {
            isVisible = true;
            if(NetWorkUtil.isNetWorkConnected(app.getContext())&&isVisible&&!hasLoad) {
                pageLoad = 0;
                new SCAsyncTask().execute(String.valueOf(pageLoad));
                hasLoad = true;
            }else {
                if(!hasLoad){
                    messageItemSCList = dataBaseTool_sc.selectAll();
//                注意此处页面已经初始化，不会出现recyclerView空值的问题，由于先初始化假如在initView中适配
//                会出现messageItemSCList空值的问题
                    if(messageItemSCList!=null) {
                        recyclerViewSCAdapter = new RecyclerViewSCAdapter(getActivity(), messageItemSCList);
                        recyclerViewSC.setAdapter(recyclerViewSCAdapter);
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
        dataBaseTool_sc = new DataBaseTool_SC(app.getContext());
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.basefragmentlayout, container,false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
    }

    private void initView() {
        progressWheel = (ProgressWheel) view.findViewById(R.id.progress_wheel);
        showProgressWheel(true);
        swipeRefreshLayoutSC = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh);
        swipeRefreshLayoutSC.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        swipeRefreshLayoutSC.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (NetWorkUtil.isNetWorkConnected(app.getContext())) {
                    pageLoad = 0;
                    new SCAsyncTask().execute(String.valueOf(pageLoad));
                } else {
                    Toast.makeText(app.getContext(), "无网络连接", Toast.LENGTH_SHORT).show();
                    resetSwipeLayout();
                }
            }
        });
        recyclerViewSC = (RecyclerView) view.findViewById(R.id.recyclerView);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewSC.setHasFixedSize(true);
        recyclerViewSC.setLayoutManager(linearLayoutManager);
        recyclerViewSC.setItemAnimator(new DefaultItemAnimator());
        recyclerViewSC.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (swipeRefreshLayoutSC.isRefreshing()) {
                    return true;
                } else {
                    return false;
                }

            }
        });
        recyclerViewSC.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
                                    pageLoad++;
                                    swipeRefreshLayoutSC.setRefreshing(true);
                                    swipeRefreshLayoutSC.setEnabled(false);
                                    new SCAsyncTask().execute(String.valueOf(pageLoad));
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
    private void resetSwipeLayout(){
        if(swipeRefreshLayoutSC.isRefreshing()){
            swipeRefreshLayoutSC.setRefreshing(false);
        }
        swipeRefreshLayoutSC.setEnabled(true);
    }
    class SCAsyncTask extends AsyncTask<String, Void, List<MessageItemSC>>{
        private ArrayList<MessageItemSC> messageItemSCs;

        @Override
        protected List<MessageItemSC> doInBackground(String... strings) {
            messageItemSCs = GetData.getMessageItemSC(Integer.parseInt(strings[0]));
            return messageItemSCs;
        }
        @Override
        protected void onPostExecute(List<MessageItemSC> messageItemSC) {
            super.onPostExecute(messageItemSC);
            if(messageItemSC==null||messageItemSC.size()<=0){
                Toast.makeText(app.getContext(), "数据加载失败", Toast.LENGTH_SHORT).show();
                showProgressWheel(false);
                resetSwipeLayout();
//                待重新获取
                return;
            }
            if(pageLoad==0){
                if(messageItemSCList!=null){
                    messageItemSCList.clear();//清除存储list后需要重新新建适配器对象 否则列表将更新为空白
                }
                dataBaseTool_sc.clear();
                messageItemSCList = new ArrayList<>();
                messageItemSCList.addAll(messageItemSC);
                dataBaseTool_sc.addToDB(messageItemSC);
                recyclerViewSCAdapter = new RecyclerViewSCAdapter(getActivity(),messageItemSCList);
                recyclerViewSC.setAdapter(recyclerViewSCAdapter);
                if(recyclerViewSCAdapter.getItemCount()>0){
                    showProgressWheel(false);
                }
            }else {
                messageItemSCList.addAll(messageItemSC);
                dataBaseTool_sc.addToDB(messageItemSC);
            }
            if(recyclerViewSCAdapter==null){
                recyclerViewSCAdapter = new RecyclerViewSCAdapter(getActivity(),messageItemSCList);
                recyclerViewSC.setAdapter(recyclerViewSCAdapter);
            }
            recyclerViewSCAdapter.notifyDataSetChanged();
            if(swipeRefreshLayoutSC.isRefreshing()){
                swipeRefreshLayoutSC.setRefreshing(false);
                isRefreshing = false;
            }
            swipeRefreshLayoutSC.setEnabled(true);
        }
    }
}
