package com.cqupt.jobclient.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cqupt.jobclient.R;
import com.cqupt.jobclient.acticity.app;
import com.cqupt.jobclient.model.DetailsArticleNY;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/3/25.
 */
public class RecyclerViewDetailsNYAdapter extends RecyclerView.Adapter<RecyclerViewDetailsNYAdapter.DetailsNYViewHolder> {
    private ArrayList<DetailsArticleNY> detailsArticleNYArrayList;
    private View view;
    private LayoutInflater inflater;
    public RecyclerViewDetailsNYAdapter(ArrayList<DetailsArticleNY> detailsArticleNYList) {
        this.detailsArticleNYArrayList = detailsArticleNYList;
        inflater = LayoutInflater.from(app.getContext());
    }
    @Override
    public DetailsNYViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType>-1) {
            switch (viewType) {
                case 0:
                    view = inflater.inflate(R.layout.details_main_title,parent,false);
                    return new DetailsNYViewHolder(view);
                case 1:
                    view = inflater.inflate(R.layout.details_recruit,parent,false);
                    return new DetailsNYViewHolder(view);
                case 2:
                    view = inflater.inflate(R.layout.details_text, parent, false);
                    return new DetailsNYViewHolder(view);
            }
        }
        return null;
    }

    @Override
    public void onBindViewHolder(DetailsNYViewHolder holder, int position) {
        DetailsArticleNY detailsArticleNY = detailsArticleNYArrayList.get(position);
        int type = detailsArticleNY.getType();
        switch (type) {
            case 0:
                holder.text.setText(detailsArticleNY.getTitle());
                break;
            case 1:
                holder.text.setText(detailsArticleNY.getText());
                break;
            case 2:
                holder.text.setText(detailsArticleNY.getText());

        }
    }

    @Override
    public int getItemCount() {
        return detailsArticleNYArrayList.size();
    }

    @Override
    public int getItemViewType(int position) {
        int viewType = detailsArticleNYArrayList.get(position).getType();
        switch (viewType) {
            case 0:
                return 0;
            case 1:
                return 1;
            case 2:
                return 2;
        }
        return -1;
    }

    class DetailsNYViewHolder extends RecyclerView.ViewHolder{
        public TextView text;
        public DetailsNYViewHolder(View itemView) {
            super(itemView);
            text = (TextView) itemView.findViewById(R.id.text);
        }
    }
}
