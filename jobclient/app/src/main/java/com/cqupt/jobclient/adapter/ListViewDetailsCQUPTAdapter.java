package com.cqupt.jobclient.adapter;

import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.cqupt.jobclient.R;
import com.cqupt.jobclient.acticity.app;
import com.cqupt.jobclient.model.DetailsArticleCQUPT;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/4/26.
 */
public class ListViewDetailsCQUPTAdapter extends BaseAdapter {
    private ArrayList<DetailsArticleCQUPT> list;
    private LayoutInflater inflater;
    private String path = "jobClient/";
    private String fileName = "";
    private String url = "";
    public ListViewDetailsCQUPTAdapter(ArrayList<DetailsArticleCQUPT> list) {
        this.list = list;
        inflater = LayoutInflater.from(app.getContext());
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }


    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        int type = 0;
        final DetailsArticleCQUPT detailsArticleCQUPT = list.get(position);
        if(detailsArticleCQUPT==null) {
            return null;
        }
        type = detailsArticleCQUPT.getType();
        if (view == null) {
            holder = new ViewHolder();
            switch (type) {
                case 0:
                    view = inflater.inflate(R.layout.details_main_title, viewGroup, false);
                    break;
                case 1:
                    view = inflater.inflate(R.layout.details_time, viewGroup, false);
                    break;
                case 2:
                    view = inflater.inflate(R.layout.details_text, viewGroup, false);
                    break;
                case 3:
                    view = inflater.inflate(R.layout.details_document, viewGroup, false);
                    break;
            }
            holder.textView = (TextView) view.findViewById(R.id.text);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        switch (type) {
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
                holder.textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        path = "jobClient/";
                        fileName = detailsArticleCQUPT.getDocument().getDocument();
                        url = detailsArticleCQUPT.getDocument().getDocumentUrl();
//                        new DownLoadAsyncTask().execute();
                    }

                });
                break;
        }
        return view;
    }

    class ViewHolder {
        public TextView textView;
    }

}
