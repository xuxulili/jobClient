package com.cqupt.jobclient.adapter;

import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.cqupt.jobclient.R;
import com.cqupt.jobclient.acticity.app;
import com.cqupt.jobclient.model.DetailsArticleCQUPT;
import com.cqupt.jobclient.utils.FileUtils;
import com.cqupt.jobclient.utils.HttpDownloader;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.HttpHandler;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by Administrator on 2016/4/20.
 */
public class RecyclerViewDetailsCQUPTAdapter extends RecyclerView.Adapter<RecyclerViewDetailsCQUPTAdapter.DetailsCQUPTViewHolder> {
    private ArrayList<DetailsArticleCQUPT> detailsArticleCQUPTArrayList = null;
    private View view;
    private LayoutInflater inflater;
    private String path = "jobClient/";
    private String fileName = "";
    private String url = "";
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
        final DetailsArticleCQUPT detailsArticleCQUPT = detailsArticleCQUPTArrayList.get(position);
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
                    holder.textView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            path = "jobClient/";
                            fileName = detailsArticleCQUPT.getDocument().getDocument();
                            url = detailsArticleCQUPT.getDocument().getDocumentUrl();
                            new DownLoadAsyncTask().execute();
//                            HttpUtils http = new HttpUtils();
//                            HttpHandler handler = http.download(url,
//                                    path+fileName,
//                                    true, // 如果目标文件存在，接着未完成的部分继续下载。服务器不支持RANGE时将从新下载。
//                                    true, // 如果从请求返回信息中获取到文件名，下载完成后自动重命名。
//                                    new RequestCallBack<File>() {
//
//                                        @Override
//                                        public void onStart() {
//                                        }
//
//                                        @Override
//                                        public void onLoading(long total, long current, boolean isUploading) {
//                                        }
//
//                                        @Override
//                                        public void onSuccess(ResponseInfo<File> responseInfo) {
//                                            Toast.makeText(app.getContext(), "下载成功！", Toast.LENGTH_SHORT).show();
//                                        }
//
//                                        @Override
//                                        public void onFailure(HttpException error, String msg) {
//                                            Toast.makeText(app.getContext(), "下载失败！", Toast.LENGTH_SHORT).show();
//                                        }
//                                    });
                        }

                    });
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

    class DownLoadAsyncTask extends AsyncTask <String,Void,Integer>{{}

        @Override
        protected Integer doInBackground(String... strings) {
            HttpDownloader httpDownLoader=new HttpDownloader();
            int result=httpDownLoader.downfile(url, path, fileName);

            return result;
        }

        @Override
        protected void onPostExecute(Integer result) {
            super.onPostExecute(result);
            if(result==0)
            {
                Toast.makeText(app.getContext(), "下载成功！", Toast.LENGTH_SHORT).show();
            }
            else if(result==1) {
                Toast.makeText(app.getContext(), "已有文件！", Toast.LENGTH_SHORT).show();
            }
            else if(result==-1){
                Toast.makeText(app.getContext(), "下载失败！", Toast.LENGTH_SHORT).show();
            }
        }
    }

    class DetailsCQUPTViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;
        public DetailsCQUPTViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.text);
        }
    }
}
