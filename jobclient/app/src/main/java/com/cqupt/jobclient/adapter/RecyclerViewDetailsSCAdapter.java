package com.cqupt.jobclient.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cqupt.jobclient.R;
import com.cqupt.jobclient.acticity.app;
import com.cqupt.jobclient.model.DetailsArticleSC;
import android.view.LayoutInflater;
import java.util.ArrayList;

/**
 * Created by Administrator on 2016/3/22.
 */
public class RecyclerViewDetailsSCAdapter extends RecyclerView.Adapter<RecyclerViewDetailsSCAdapter.DetailsSCViewHolder> {
    ArrayList<DetailsArticleSC> detailsArticleSCs;
    private View view;
    public RecyclerViewDetailsSCAdapter(ArrayList<DetailsArticleSC> detailsArticleSCs) {
        this.detailsArticleSCs = detailsArticleSCs;
    }
    @Override
    public DetailsSCViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType<0) {
            return null;
        }
        LayoutInflater inflater = LayoutInflater.from(app.getContext());
        switch (viewType) {
            case 0:
                view = inflater.inflate(R.layout.details_main_title, parent, false);
                return new DetailsSCViewHolder(view);
            case 1:
                view = inflater.inflate(R.layout.details_time, parent, false);
                return new DetailsSCViewHolder(view);
            case 2:
                view = inflater.inflate(R.layout.details_title, parent, false);
                return new DetailsSCViewHolder(view);
            case 3:
                view = inflater.inflate(R.layout.details_text, parent, false);
                return new DetailsSCViewHolder(view);
            case 4:
                view = inflater.inflate(R.layout.details_recruit, parent, false);
                return new DetailsSCViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(DetailsSCViewHolder holder, int position) {
        DetailsArticleSC detailsArticleSC = detailsArticleSCs.get(position);
        if(detailsArticleSC!=null){
            switch (detailsArticleSC.getType()){
                case 0:
                    holder.textView.setText(detailsArticleSC.getMainTitle());
                    break;
                case 1:
                    holder.textView.setText(detailsArticleSC.getTime());
                    break;
                case 2:
                    holder.textView.setText(detailsArticleSC.getTitle());
                    break;
                case 3:
                    holder.textView.setText(detailsArticleSC.getText());
                    break;
                case 4:
                    holder.textView.setText(detailsArticleSC.getRecruit());
                    break;
            }
        }
    }


    @Override
    public int getItemCount() {
        return detailsArticleSCs.size();
    }

    @Override
    public int getItemViewType(int position) {
        switch (detailsArticleSCs.get(position).getType()) {
            case 0:
                return 0;
            case 1:
                return 1;
            case 2:
                return 2;
            case 3:
                return 3;
            case 4:
                return 4;
        }
        return -1;
    }

    class DetailsSCViewHolder extends RecyclerView.ViewHolder{
        public TextView textView;
        public DetailsSCViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.text);
        }
    }
}
