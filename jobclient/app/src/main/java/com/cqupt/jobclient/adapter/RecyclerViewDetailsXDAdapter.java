package com.cqupt.jobclient.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cqupt.jobclient.R;
import com.cqupt.jobclient.acticity.app;
import com.cqupt.jobclient.model.DetailsArticleSC;
import com.cqupt.jobclient.model.DetailsArticleXD;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/3/30.
 */
public class RecyclerViewDetailsXDAdapter extends RecyclerView.Adapter<RecyclerViewDetailsXDAdapter.DetailsXDViewHolder> {
    private ArrayList<DetailsArticleXD> detailsArticleXDs;
    private View view;
    private LayoutInflater inflater;
    public RecyclerViewDetailsXDAdapter(ArrayList<DetailsArticleXD> detailsArticleXDs) {
        this.detailsArticleXDs = detailsArticleXDs;
        inflater = LayoutInflater.from(app.getContext());
    }
    @Override
    public DetailsXDViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType>-1) {
            switch (viewType) {
                case 0:
                    view = inflater.inflate(R.layout.details_main_title,parent,false);
                    return new DetailsXDViewHolder(view);
                case 1:
                    view = inflater.inflate(R.layout.details_time,parent,false);
                    return new DetailsXDViewHolder(view);
                case 2:
                    view = inflater.inflate(R.layout.details_text, parent, false);
                    return new DetailsXDViewHolder(view);
            }
        }
        return null;
    }

    @Override
    public void onBindViewHolder(DetailsXDViewHolder holder, int position) {
        DetailsArticleXD detailsArticleXD = detailsArticleXDs.get(position);
        if(detailsArticleXD!=null){
            switch (detailsArticleXD.getType()){
                case 0:
                    holder.textView.setText(detailsArticleXD.getTitle());
                    break;
                case 1:
                    holder.textView.setText(detailsArticleXD.getTime());
                    break;
                case 2:
                    holder.textView.setText(detailsArticleXD.getText());
                    break;
            }
        }
    }

    @Override
    public int getItemCount() {
        return detailsArticleXDs.size();
    }

    @Override
    public int getItemViewType(int position) {
        DetailsArticleXD detailsArticleXD = detailsArticleXDs.get(position);
        int type = detailsArticleXD.getType();
        if(detailsArticleXD!=null){
            switch (type){
                case 0:
                    return 0;
                case 1:
                    return 1;
                case 2:
                    return 2;
            }
        }
        return -1;
    }

    class DetailsXDViewHolder extends RecyclerView.ViewHolder{
        public TextView textView;
        public DetailsXDViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.text);
        }
    }
}
