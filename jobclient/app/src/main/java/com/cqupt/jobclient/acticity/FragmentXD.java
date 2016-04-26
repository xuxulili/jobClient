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
import com.cqupt.jobclient.adapter.RecyclerViewXDAdapter;
import com.cqupt.jobclient.model.MessageItemXD;
import com.cqupt.jobclient.utils.DB.DataBaseTool_XD;
import com.cqupt.jobclient.utils.GetData;
import com.cqupt.jobclient.utils.NetWorkUtil;
import com.pnikosis.materialishprogress.ProgressWheel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/3/18.
 */
public class FragmentXD  extends Fragment {
    private View view;
    private RecyclerView recyclerViewXD;
    private SwipeRefreshLayout swipeRefreshLayoutXD;
    private LinearLayoutManager linearLayoutManager;
    private int pageLoad;
    private boolean isVisible;
    private boolean hasLoad;
    private RecyclerViewXDAdapter recyclerViewXDAdapter;
    private ArrayList<MessageItemXD> messageItemXDList;
    private ProgressWheel progressWheel;
    private DataBaseTool_XD dataBaseTool_xd;
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(getUserVisibleHint()) {
            isVisible = true;
            if(NetWorkUtil.isNetWorkConnected(app.getContext())&&isVisible&&!hasLoad) {
                pageLoad = 0;
                new XDAsyncTask().execute(String.valueOf(pageLoad));
                hasLoad = true;
            }else {
                if(!hasLoad){
                    messageItemXDList = dataBaseTool_xd.selectAll();
//                注意此处页面已经初始化，不会出现recyclerView空值的问题，由于先初始化假如在initView中适配
//                会出现messageItemSCList空值的问题
                    if(messageItemXDList!=null) {
                        recyclerViewXDAdapter = new RecyclerViewXDAdapter(getActivity(), messageItemXDList);
                        recyclerViewXD.setAdapter(recyclerViewXDAdapter);
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
        dataBaseTool_xd = new DataBaseTool_XD(app.getContext());
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
        recyclerViewXD = (RecyclerView) view.findViewById(R.id.recyclerView);
        swipeRefreshLayoutXD = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh);
        swipeRefreshLayoutXD.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        swipeRefreshLayoutXD.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (NetWorkUtil.isNetWorkConnected(app.getContext())) {
                    pageLoad = 0;
                    new XDAsyncTask().execute(String.valueOf(pageLoad));
                } else {
                    resetSwipeLayout();
                    Toast.makeText(app.getContext(), "无网络连接", Toast.LENGTH_SHORT).show();
                }
            }
        });
        recyclerViewXD = (RecyclerView) view.findViewById(R.id.recyclerView);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewXD.setHasFixedSize(true);
        recyclerViewXD.setLayoutManager(linearLayoutManager);
        recyclerViewXD.setItemAnimator(new DefaultItemAnimator());
        recyclerViewXD.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (swipeRefreshLayoutXD.isRefreshing()) {
                    return true;
                } else {
                    return false;
                }

            }
        });
        recyclerViewXD.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
                                    swipeRefreshLayoutXD.setRefreshing(true);
                                    swipeRefreshLayoutXD.setEnabled(false);
                                    new XDAsyncTask().execute(String.valueOf(pageLoad));
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

    private void resetSwipeLayout(){
        if(swipeRefreshLayoutXD.isRefreshing()){
            swipeRefreshLayoutXD.setRefreshing(false);
        }
        swipeRefreshLayoutXD.setEnabled(true);
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

    class XDAsyncTask extends AsyncTask<String,Void,List<MessageItemXD>>{
        ArrayList<MessageItemXD> messageItemXDs;
        @Override
        protected List<MessageItemXD> doInBackground(String... strings) {
            messageItemXDs = GetData.getMessageItemXD(pageLoad);
            return messageItemXDs;
        }

        @Override
        protected void onPostExecute(List<MessageItemXD> messageItemXDs) {
            super.onPostExecute(messageItemXDs);
            if(messageItemXDs==null||messageItemXDs.size()<=0){
                Toast.makeText(app.getContext(), "数据加载失败", Toast.LENGTH_SHORT).show();
                showProgressWheel(false);
                resetSwipeLayout();
//                待重新获取
                return;
            }
            if(pageLoad==0){
                if(messageItemXDList!=null){
                    messageItemXDList.clear();
                }
                dataBaseTool_xd.clear();
                messageItemXDList = new ArrayList<>();
                messageItemXDList.addAll(messageItemXDs);
                dataBaseTool_xd.addToDB(messageItemXDs);
                recyclerViewXDAdapter = new RecyclerViewXDAdapter(getActivity(),messageItemXDList);
                recyclerViewXD.setAdapter(recyclerViewXDAdapter);
                if(recyclerViewXDAdapter.getItemCount()>0){
                    showProgressWheel(false);
                }
            }else {
                messageItemXDList.addAll(messageItemXDs);
                dataBaseTool_xd.addToDB(messageItemXDs);
            }
            if(recyclerViewXDAdapter==null){
                recyclerViewXDAdapter = new RecyclerViewXDAdapter(getActivity(),messageItemXDList);
                recyclerViewXD.setAdapter(recyclerViewXDAdapter);
            }
            recyclerViewXDAdapter.notifyDataSetChanged();
            if(swipeRefreshLayoutXD.isRefreshing()){
                swipeRefreshLayoutXD.setRefreshing(false);
            }
            swipeRefreshLayoutXD.setEnabled(true);
        }
    }
}
