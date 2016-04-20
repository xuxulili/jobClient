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
import com.cqupt.jobclient.model.MessageItemSC;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

/**
 * Created by Administrator on 2016/3/19.
 */
public class RecyclerViewSCAdapter extends RecyclerView.Adapter<RecyclerViewSCAdapter.SCViewHolder> {
    ArrayList<MessageItemSC> messageItemSCList;
    private View view;
    private Context context;
    public RecyclerViewSCAdapter(Context context,ArrayList<MessageItemSC> messageItemSCList) {
        this.messageItemSCList = messageItemSCList;
        this.context = context;
    }
    @Override
    public SCViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = LayoutInflater.from(app.getContext()).inflate(R.layout.recylerview_sc_item,parent,false);
        return new SCViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SCViewHolder holder, int position) {
        if(messageItemSCList==null||messageItemSCList.size()<=0) {
            Toast.makeText(app.getContext(), "没有数据", Toast.LENGTH_SHORT).show();
            return;
        }
        holder.title.setText(messageItemSCList.get(position).getMessageTitle());
    }

    @Override
    public int getItemCount() {
        return messageItemSCList.size();
    }
    class SCViewHolder extends RecyclerView.ViewHolder{
        public TextView title;
        private LinearLayout linearLayout;
        public SCViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.SC_title);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.list_item);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (getPosition()>-1) {
                        Intent intent = new Intent(context, DetailsActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("url", messageItemSCList.get(getPosition()).getMessageUrl());
                        bundle.putString("title", messageItemSCList.get(getPosition()).getMessageTitle());
                        bundle.putString("type", "SC");
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
