package com.cqupt.jobclient.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cqupt.jobclient.R;
import com.cqupt.jobclient.acticity.DetailsActivity;
import com.cqupt.jobclient.acticity.app;
import com.cqupt.jobclient.model.MessageItemXD;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/3/21.
 */
public class RecyclerViewXDAdapter extends RecyclerView.Adapter<RecyclerViewXDAdapter.XDViewHolder> {
    private ArrayList<MessageItemXD> messageItemXDList;
    private Context context;
    private View view;
    public RecyclerViewXDAdapter(Context context,ArrayList<MessageItemXD> messageItemXDList) {
        this.messageItemXDList = messageItemXDList;
        this.context = context;
    }
    @Override
    public XDViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = LayoutInflater.from(app.getContext()).inflate(R.layout.recylerview_xd_item, parent, false);
        return new XDViewHolder(view);
    }

    @Override
    public void onBindViewHolder(XDViewHolder holder, int position) {
        if(messageItemXDList==null||messageItemXDList.size()<=0) {
            Toast.makeText(app.getContext(), "没有数据", Toast.LENGTH_SHORT).show();
            return;
        }
        holder.title.setText(messageItemXDList.get(position).getMessageTitle());
        holder.postTime.setText(messageItemXDList.get(position).getMessagePostTime());
    }

    @Override
    public int getItemCount() {
        return messageItemXDList.size();
    }

    class XDViewHolder extends RecyclerView.ViewHolder{
        public LinearLayout linearLayout;
        public TextView title;
        public TextView postTime;
        public XDViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.XD_title);
            postTime = (TextView) itemView.findViewById(R.id.XD_time);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.main_content);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (getPosition()>-1) {
                        Intent intent = new Intent(context, DetailsActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("url", messageItemXDList.get(getPosition()).getMessageUrl());
                        bundle.putString("title", messageItemXDList.get(getPosition()).getMessageTitle());
                        if(getPosition()==0){
                            bundle.putString("type","XDFirst");
                        }else{
                            bundle.putString("type","XD");
                        }

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
