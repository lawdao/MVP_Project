package example.fussen.baselibrary.network.callback;

/**
 * Created by Fussen on 2016/11/24.
 * 请求回调
 */

public interface RequestCallBack<T> {
    void onStart();//请求前

    void onSuccess(T data); //请求成功

    void onError(String errorMsg); //请求失败

    void onCompleted();//请求完成
}
