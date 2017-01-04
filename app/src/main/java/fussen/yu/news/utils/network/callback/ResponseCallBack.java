package fussen.yu.news.utils.network.callback;


/**
 * Created by Fussen on 2016/12/22.
 */

public interface ResponseCallBack<T> {
     void onStart();

    void onCompleted();

    void onError(Throwable e);

    void onSuccee(T response);
}
