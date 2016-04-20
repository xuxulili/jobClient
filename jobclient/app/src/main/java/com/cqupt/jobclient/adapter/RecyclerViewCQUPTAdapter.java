package com.cqupt.jobclient.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.cqupt.jobclient.R;
import com.cqupt.jobclient.acticity.DetailsActivity;
import com.cqupt.jobclient.acticity.app;
import com.cqupt.jobclient.model.MessageItemCQUPT;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/4/20.
 */
public class RecyclerViewCQUPTAdapter extends RecyclerView.Adapter<RecyclerViewCQUPTAdapter.CQUPTViewHolder> {
    private Context context;
    private ArrayList<MessageItemCQUPT> messageItemCQUPTArrayList;
    private View view;
    private LayoutInflater inflater;
    public RecyclerViewCQUPTAdapter(Context context,ArrayList<MessageItemCQUPT> messageItemCQUPTArrayList) {
        this.messageItemCQUPTArrayList = messageItemCQUPTArrayList;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }
    @Override
    public CQUPTViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = inflater.inflate(R.layout.recyclerview_cqupt_item, parent, false);
        return new CQUPTViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CQUPTViewHolder holder, int position) {
        if(messageItemCQUPTArrayList==null||messageItemCQUPTArrayList.size()<=0) {
            Toast.makeText(app.getContext(), "没有数据", Toast.LENGTH_SHORT).show();
            return;
        }
        MessageItemCQUPT messageItemCQUPT = messageItemCQUPTArrayList.get(position);
        holder.CQUPT_title.setText(messageItemCQUPT.getMessageTitle());
        holder.CQUPT_time.setText(messageItemCQUPT.getMessageTime());
        holder.CQUPT_target.setText(messageItemCQUPT.getMessageNeedType());
        holder.CQUPT_place.setText(messageItemCQUPT.getMessagePlace());
        holder.CQUPT_scan.setText(messageItemCQUPT.getMessageViewTime());
        holder.CQUPT_num.setText(messageItemCQUPT.getMessageNeedNum());
    }

    @Override
    public int getItemCount() {
        return messageItemCQUPTArrayList.size();
    }

    class CQUPTViewHolder extends RecyclerView.ViewHolder {
        public TextView CQUPT_title;
        public TextView CQUPT_time;
        public TextView CQUPT_place;
        public TextView CQUPT_target;
        public TextView CQUPT_num;
        public TextView CQUPT_scan;
        public CQUPTViewHolder(View itemView) {
            super(itemView);
            CQUPT_title = (TextView) itemView.findViewById(R.id.CQUPT_title);
            CQUPT_time = (TextView) itemView.findViewById(R.id.CQUPT_time);
            CQUPT_place = (TextView) itemView.findViewById(R.id.CQUPT_place);
            CQUPT_target = (TextView) itemView.findViewById(R.id.CQUPT_target);
            CQUPT_num = (TextView) itemView.findViewById(R.id.CQUPT_needNum);
            CQUPT_scan = (TextView) itemView.findViewById(R.id.CQUPT_viewNUm);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (getPosition()>-1) {
                        Intent intent = new Intent(context, DetailsActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("url", messageItemCQUPTArrayList.get(getPosition()).getMessageUrl());
                        bundle.putString("title", messageItemCQUPTArrayList.get(getPosition()).getMessageTitle());
                        bundle.putString("type","CQUPT");
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
