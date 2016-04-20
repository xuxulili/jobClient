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
import com.cqupt.jobclient.model.MessageItemXDFirst;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/3/31.
 */
public class RecyclerViewXDFirstAdapter extends RecyclerView.Adapter<RecyclerViewXDFirstAdapter.XDFirstViewHolder> {
    private View view;
    private LayoutInflater inflater;
    private Context context;
    private ArrayList<MessageItemXDFirst> messageItemXDFirsts;
    public RecyclerViewXDFirstAdapter(Context context,ArrayList<MessageItemXDFirst> messageItemXDFirsts) {
        inflater = LayoutInflater.from(app.getContext());
        this.context = context;
        this.messageItemXDFirsts = messageItemXDFirsts;
    }
    @Override
    public XDFirstViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = inflater.inflate(R.layout.recylerview_ny_item, parent, false);
        return new XDFirstViewHolder(view);
    }

    @Override
    public void onBindViewHolder(XDFirstViewHolder holder, int position) {
        if(messageItemXDFirsts==null||messageItemXDFirsts.size()<=0) {
            Toast.makeText(app.getContext(), "没有数据", Toast.LENGTH_SHORT).show();
            return;
        }
        if(messageItemXDFirsts.size()>0){
            holder.title.setText(messageItemXDFirsts.get(position).getMessageTitle());
            holder.time.setText(messageItemXDFirsts.get(position).getMessageTime());
            holder.place.setText(messageItemXDFirsts.get(position).getMessagePlace());
        }
    }

    @Override
    public int getItemCount() {
        return messageItemXDFirsts.size();
    }

    class XDFirstViewHolder extends RecyclerView.ViewHolder{
        public TextView title;
        public TextView time;
        public TextView place;
        public LinearLayout linearLayout;
        public XDFirstViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.NY_title);
            time = (TextView) itemView.findViewById(R.id.NY_time);
            place = (TextView) itemView.findViewById(R.id.NY_place);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.list_item);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (getPosition()>-1) {
                        Intent intent = new Intent(context, DetailsActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("url", messageItemXDFirsts.get(getPosition()).getMessageUrl());
                        bundle.putString("title", messageItemXDFirsts.get(getPosition()).getMessageTitle());
                        bundle.putString("type","XD");
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
