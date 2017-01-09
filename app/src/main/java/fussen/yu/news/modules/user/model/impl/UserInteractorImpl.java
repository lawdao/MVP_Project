package fussen.yu.news.modules.user.model.impl;

import java.io.File;

import javax.inject.Inject;

import example.fussen.baselibrary.config.FileConfig;
import fussen.yu.news.modules.user.bean.UpLoad;
import fussen.yu.news.modules.user.model.UserInteractor;
import fussen.yu.news.utils.UiUtils;
import fussen.yu.news.utils.network.DownLoadCallBack;
import fussen.yu.news.utils.network.NetConfig;
import fussen.yu.news.utils.network.NetworkUtils;
import fussen.yu.news.utils.network.callback.RequestCallBack;
import fussen.yu.news.utils.network.callback.ResponseCallBack;
import rx.Subscription;

/**
 * Created by Fussen on 2017/1/9.
 */

public class UserInteractorImpl implements UserInteractor {

    @Inject
    public UserInteractorImpl() {

    }

    @Override
    public Subscription upLoadImage(File file, final RequestCallBack callBack) {
        if (file == null) {
            callBack.onError("文件为空");
            callBack.onCompleted();
            return null;
        }

        return NetworkUtils.getInstance(UiUtils.getContext()).uploadFlie(NetConfig.UPLOAD_PHOTO, file, new ResponseCallBack<UpLoad>() {
            @Override
            public void onStart() {
                callBack.onStart();
            }

            @Override
            public void onCompleted() {
                callBack.onCompleted();
            }

            @Override
            public void onError(Throwable e) {
                callBack.onError(e.getMessage());
            }

            @Override
            public void onSuccee(UpLoad response) {
                callBack.onSuccess(response);
            }
        });
    }

    @Override
    public Subscription downLoadImage(String url, final RequestCallBack callBack) {
        return  NetworkUtils.getInstance(UiUtils.getContext()).download(url, FileConfig.DOWNLOAD_IMG_PATH, "touxiang", new DownLoadCallBack() {
            @Override
            public void onStart() {
                callBack.onStart();
            }

            @Override
            public void onError(Throwable e) {
                callBack.onError(e.getMessage());
            }

            @Override
            public void onSuccess(String path, String fileName, long fileSize) {
                callBack.dowloadSuccess(path,fileName,fileSize);
            }

            @Override
            public void onProgress(long downSize, long fileSize) {
                callBack.onProgress(downSize,fileSize);
            }
        });
    }
}
