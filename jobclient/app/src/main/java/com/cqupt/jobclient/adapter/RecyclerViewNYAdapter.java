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
import com.cqupt.jobclient.model.MessageItemNY;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/3/20.
 */
public class RecyclerViewNYAdapter extends RecyclerView.Adapter<RecyclerViewNYAdapter.NYViewHolder>{
    private ArrayList<MessageItemNY> messageItemNYList;
    private View view;
    private Context context;
    public RecyclerViewNYAdapter(Context context,ArrayList<MessageItemNY> messageItemNYList) {
        this.messageItemNYList = messageItemNYList;
        this.context = context;

    }
    @Override
    public NYViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = LayoutInflater.from(app.getContext()).inflate(R.layout.recylerview_ny_item, parent,false);
        return new NYViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NYViewHolder holder, int position) {
        if(messageItemNYList==null||messageItemNYList.size()<=0) {
            Toast.makeText(app.getContext(), "没有数据", Toast.LENGTH_SHORT).show();
            return;
        }
        if(messageItemNYList.size()>0){
            holder.NY_title.setText(messageItemNYList.get(position).getMessageTitle());
            holder.NY_time.setText(messageItemNYList.get(position).getMessageTime());
            holder.NY_place.setText(messageItemNYList.get(position).getMessagePlace());
        }
    }

    @Override
    public int getItemCount() {
        return messageItemNYList.size();
    }

    class NYViewHolder extends RecyclerView.ViewHolder{
        public TextView NY_title;
        public TextView NY_time;
        public TextView NY_place;
        public LinearLayout linearLayout;
        public NYViewHolder(View itemView) {
            super(itemView);
            NY_title = (TextView) itemView.findViewById(R.id.NY_title);
            NY_time = (TextView) itemView.findViewById(R.id.NY_time);
            NY_place = (TextView) itemView.findViewById(R.id.NY_place);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.list_item);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (getPosition()>-1) {
                        Intent intent = new Intent(context, DetailsActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("url", messageItemNYList.get(getPosition()).getMessageUrl());
                        bundle.putString("title", messageItemNYList.get(getPosition()).getMessageTitle());
                        bundle.putString("type","NY");
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
