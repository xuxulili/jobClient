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
import com.cqupt.jobclient.adapter.RecyclerViewNYAdapter;
import com.cqupt.jobclient.adapter.RecyclerViewNYAdapter;
import com.cqupt.jobclient.adapter.RecyclerViewSCAdapter;
import com.cqupt.jobclient.model.MessageItemNY;
import com.cqupt.jobclient.utils.DB.DataBaseTool_NY;
import com.cqupt.jobclient.utils.GetData;
import com.cqupt.jobclient.utils.NetWorkUtil;
import com.pnikosis.materialishprogress.ProgressWheel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/3/18.
 */
public class FragmentNY extends Fragment {
    private RecyclerView recyclerViewNY;
    private SwipeRefreshLayout swipeRefreshLayoutNY;
    private ArrayList<MessageItemNY> messageItemNYList;
    private LinearLayoutManager linearLayoutManager;
    private int pageLoad;
    private boolean isVisible;
    private boolean hasLoad;
    private RecyclerViewNYAdapter recyclerViewNYAdapter;
    private ProgressWheel progressWheel;
    private DataBaseTool_NY dataBaseTool_ny;
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if(getUserVisibleHint()) {
            isVisible = true;
            if(NetWorkUtil.isNetWorkConnected(app.getContext())&&isVisible&&!hasLoad) {
                pageLoad = 0;
                new NYAsyncTask().execute(String.valueOf(pageLoad));
                hasLoad = true;
            }else {
                if(!hasLoad){
                    messageItemNYList = dataBaseTool_ny.selectAll();
//                注意此处页面已经初始化，不会出现recyclerView空值的问题，由于先初始化假如在initView中适配
//                会出现messageItemSCList空值的问题
                    if(messageItemNYList!=null) {
                        recyclerViewNYAdapter = new RecyclerViewNYAdapter(getActivity(), messageItemNYList);
                        recyclerViewNY.setAdapter(recyclerViewNYAdapter);
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
    public void onAttach(Activity activity){
        super.onAttach(activity);
        dataBaseTool_ny = new DataBaseTool_NY(app.getContext());
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    private View view;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.basefragmentlayout, container,false);
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
        recyclerViewNY = (RecyclerView) view.findViewById(R.id.recyclerView);
        swipeRefreshLayoutNY = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh);
        swipeRefreshLayoutNY.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        swipeRefreshLayoutNY.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (NetWorkUtil.isNetWorkConnected(app.getContext())) {
                    pageLoad = 0;
                    new NYAsyncTask().execute(String.valueOf(pageLoad));
                } else {
                    resetSwipeLayout();
                    Toast.makeText(app.getContext(), "无网络连接", Toast.LENGTH_SHORT).show();
                }
            }
        });
        recyclerViewNY = (RecyclerView) view.findViewById(R.id.recyclerView);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewNY.setHasFixedSize(true);
        recyclerViewNY.setLayoutManager(linearLayoutManager);
        recyclerViewNY.setItemAnimator(new DefaultItemAnimator());
        recyclerViewNY.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (swipeRefreshLayoutNY.isRefreshing()) {
                    return true;
                } else {
                    return false;
                }

            }
        });
        recyclerViewNY.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
                                    swipeRefreshLayoutNY.setRefreshing(true);
                                    swipeRefreshLayoutNY.setEnabled(false);
                                    new NYAsyncTask().execute(String.valueOf(pageLoad));
                                }else{
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

    private void resetSwipeLayout(){
        if(swipeRefreshLayoutNY.isRefreshing()){
            swipeRefreshLayoutNY.setRefreshing(false);
        }
        swipeRefreshLayoutNY.setEnabled(true);
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
    class NYAsyncTask extends AsyncTask<String,Void,List<MessageItemNY>>{
        private ArrayList<MessageItemNY> messageItemNYs;
        @Override
        protected List<MessageItemNY> doInBackground(String... strings) {
            messageItemNYs = GetData.getMessageItemNY(Integer.parseInt(strings[0]));
            return messageItemNYs;
        }

        @Override
        protected void onPostExecute(List<MessageItemNY> messageItemNYs) {
            super.onPostExecute(messageItemNYs);
            if(messageItemNYs==null||messageItemNYs.size()<=0){
                Toast.makeText(app.getContext(), "数据加载失败", Toast.LENGTH_SHORT).show();
                showProgressWheel(false);
                resetSwipeLayout();
//                待重新获取
                return;
            }
            if(pageLoad==0){
                if(messageItemNYList!=null){
                    messageItemNYList.clear();
                }
                dataBaseTool_ny.clear();
                messageItemNYList = new ArrayList<>();
                messageItemNYList.addAll(messageItemNYs);
                dataBaseTool_ny.addToDB(messageItemNYs);
                recyclerViewNYAdapter = new RecyclerViewNYAdapter(getActivity(),messageItemNYList);
                recyclerViewNY.setAdapter(recyclerViewNYAdapter);
                if(recyclerViewNYAdapter.getItemCount()>0){
                    showProgressWheel(false);
                }
            }else {
                messageItemNYList.addAll(messageItemNYs);
                dataBaseTool_ny.addToDB(messageItemNYs);
            }
            if(recyclerViewNYAdapter==null){
                recyclerViewNYAdapter = new RecyclerViewNYAdapter(getActivity(),messageItemNYList);
                recyclerViewNY.setAdapter(recyclerViewNYAdapter);
            }
            recyclerViewNYAdapter.notifyDataSetChanged();
            if(swipeRefreshLayoutNY.isRefreshing()){
                swipeRefreshLayoutNY.setRefreshing(false);
            }
            swipeRefreshLayoutNY.setEnabled(true);
        }
    }
}
