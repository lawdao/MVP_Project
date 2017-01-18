package fussen.yu.news.modules.user.model;

import java.io.File;

import example.fussen.baselibrary.callback.RequestCallBack;
import rx.Subscription;

/**
 * Created by Fussen on 2016/12/28.
 */

public interface UserInteractor<T> {
    Subscription upLoadImage(File file, RequestCallBack<T> callBack);
    Subscription downLoadImage(String url, RequestCallBack<T> callBack);
}
