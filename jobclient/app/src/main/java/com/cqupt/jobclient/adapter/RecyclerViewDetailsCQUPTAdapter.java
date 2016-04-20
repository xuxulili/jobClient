package com.cqupt.jobclient.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cqupt.jobclient.R;
import com.cqupt.jobclient.acticity.app;
import com.cqupt.jobclient.model.DetailsArticleCQUPT;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/4/20.
 */
public class RecyclerViewDetailsCQUPTAdapter extends RecyclerView.Adapter<RecyclerViewDetailsCQUPTAdapter.DetailsCQUPTViewHolder> {
    private ArrayList<DetailsArticleCQUPT> detailsArticleCQUPTArrayList = null;
    private View view;
    private LayoutInflater inflater;
    public RecyclerViewDetailsCQUPTAdapter(ArrayList<DetailsArticleCQUPT> detailsArticleCQUPTArrayList) {
        this.detailsArticleCQUPTArrayList = detailsArticleCQUPTArrayList;
        inflater = LayoutInflater.from(app.getContext());
    }
    @Override
    public RecyclerViewDetailsCQUPTAdapter.DetailsCQUPTViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType>-1) {
            switch (viewType) {
                case 0:
                    view = inflater.inflate(R.layout.details_main_title,parent,false);
                    return new DetailsCQUPTViewHolder(view);
                case 1:
                    view = inflater.inflate(R.layout.details_time, parent, false);
                    return new DetailsCQUPTViewHolder(view);
                case 2:
                    view = inflater.inflate(R.layout.details_text, parent, false);
                    return new DetailsCQUPTViewHolder(view);
                case 3:
                    view = inflater.inflate(R.layout.details_document, parent, false);
                    return new DetailsCQUPTViewHolder(view);
            }
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerViewDetailsCQUPTAdapter.DetailsCQUPTViewHolder holder, int position) {
        DetailsArticleCQUPT detailsArticleCQUPT = detailsArticleCQUPTArrayList.get(position);
        if(detailsArticleCQUPT!=null) {
            switch (detailsArticleCQUPT.getType()) {
                case 0:
                    holder.textView.setText(detailsArticleCQUPT.getTitle());
                    break;
                case 1:
                    holder.textView.setText(detailsArticleCQUPT.getTime());
                    break;
                case 2:
                    holder.textView.setText(detailsArticleCQUPT.getText());
                    break;
                case 3:
                    holder.textView.setText(detailsArticleCQUPT.getDocument().getDocument());
                    break;
            }
        }
    }

    @Override
    public int getItemCount() {
        return detailsArticleCQUPTArrayList.size();
    }

    @Override
    public int getItemViewType(int position) {
        int type = detailsArticleCQUPTArrayList.get(position).getType();
        switch (type) {
            case 0:
                return 0;
            case 1:
                return 1;
            case 2:
                return 2;
            case 3:
                return 3;
        }
        return -1;
    }

    class DetailsCQUPTViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;
        public DetailsCQUPTViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.text);
        }
    }
}
