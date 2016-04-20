package com.cqupt.jobclient.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cqupt.jobclient.R;
import com.cqupt.jobclient.acticity.app;
import com.cqupt.jobclient.model.DetailsArticleHK;
import com.cqupt.jobclient.model.DetailsArticleXD;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/3/30.
 */
public class RecyclerViewDetailsHKAdapter extends RecyclerView.Adapter<RecyclerViewDetailsHKAdapter.DetailsHKViewHolder> {
    private ArrayList<DetailsArticleHK> detailsArticleHKs;
    private View view;
    private LayoutInflater inflater;
    public RecyclerViewDetailsHKAdapter(ArrayList<DetailsArticleHK> detailsArticleHKs) {
        this.detailsArticleHKs = detailsArticleHKs;
        inflater = LayoutInflater.from(app.getContext());
    }
    @Override
    public DetailsHKViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case 0:
                view = inflater.inflate(R.layout.details_main_title,parent,false);
                return new DetailsHKViewHolder(view);
            case 1:
                view = inflater.inflate(R.layout.details_time,parent,false);
                return new DetailsHKViewHolder(view);
            case 2:
                view = inflater.inflate(R.layout.details_text, parent, false);
                return new DetailsHKViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(DetailsHKViewHolder holder, int position) {
        DetailsArticleHK detailsArticleHK = detailsArticleHKs.get(position);
        if(detailsArticleHK!=null){
            switch (detailsArticleHK.getType()){
                case 0:
                    holder.textView.setText(detailsArticleHK.getTitle());
                    break;
                case 1:
                    holder.textView.setText(detailsArticleHK.getTime());
                    break;
                case 2:
                    holder.textView.setText(detailsArticleHK.getText());
                    break;
            }
        }
    }

    @Override
    public int getItemCount() {
        return detailsArticleHKs.size();
    }

    @Override
    public int getItemViewType(int position) {
        int type = detailsArticleHKs.get(position).getType();
        switch (type) {
            case 0:
                return 0;
            case 1:
                return 1;
            case 2:
                return 2;
        }
        return -1;
    }

    class DetailsHKViewHolder extends RecyclerView.ViewHolder{
        public TextView textView;
        public DetailsHKViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.text);
        }
    }
}
