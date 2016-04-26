package com.cqupt.jobclient.acticity;

import com.cqupt.jobclient.model.NewsPicture;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/4/24.
 */
public interface OnLoadingListener {
    public void onLoad(ArrayList<NewsPicture> newsPictureArrayList);
}
