package com.cqupt.jobclient.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cqupt.jobclient.R;
import com.cqupt.jobclient.acticity.DetailsActivity;
import com.cqupt.jobclient.acticity.app;
import com.cqupt.jobclient.model.MessageItemHK;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/3/21.
 */
public class RecyclerViewHKAdapter extends RecyclerView.Adapter<RecyclerViewHKAdapter.HKViewHolder> {
    private View view;
    private ArrayList<MessageItemHK> messageItemHKList;
    private Context context;
    public RecyclerViewHKAdapter(Context context,ArrayList<MessageItemHK> messageItemHKList) {
        this.messageItemHKList = messageItemHKList;
        this.context = context;
    }
    @Override
    public HKViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = LayoutInflater.from(app.getContext()).inflate(R.layout.recyclerview_hk_item, parent, false);
        return new HKViewHolder(view);
    }

    @Override
    public void onBindViewHolder(HKViewHolder holder, int position) {
        if(messageItemHKList==null||messageItemHKList.size()<=0) {
            Toast.makeText(app.getContext(), "没有数据", Toast.LENGTH_SHORT).show();
            return;
        }
        holder.title.setText(messageItemHKList.get(position).getMessageTitle());
        holder.postTime.setText(messageItemHKList.get(position).getMessagePostTime());
        holder.viewNum.setText(messageItemHKList.get(position).getMessageViewNum());
    }

    @Override
    public int getItemCount() {
        return messageItemHKList.size();
    }

    class HKViewHolder extends RecyclerView.ViewHolder{
        public TextView title;
        public TextView postTime;
        public TextView viewNum;
        public LinearLayout linearLayout;
        public HKViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.HK_title);
            postTime = (TextView) itemView.findViewById(R.id.HK_post_time);
            viewNum = (TextView) itemView.findViewById(R.id.HK_viewNUm);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.main_content);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (getPosition()>-1) {
                        Intent intent = new Intent(context, DetailsActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("url", messageItemHKList.get(getPosition()).getMessageUrl());
                        bundle.putString("title", messageItemHKList.get(getPosition()).getMessageTitle());
                        bundle.putString("type","HK");
                        intent.putExtras(bundle);
                        //获取网址错误或不规范（不是http开头）
                        // 导致无法启动
                        context.startActivity(intent);
                    }

                }
            });
        }
    }
}
