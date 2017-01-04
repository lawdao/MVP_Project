package fussen.yu.news.base.presenter;

import android.support.annotation.NonNull;

import fussen.yu.news.base.view.BaseView;

/**
 * Created by Fussen on 2016/11/24.
 * presenter的生命周期
 * 每个presenter须继承此接口
 * 执行顺序：onBindView() --> onCreate() --> onDestroy()
 */

public interface PresenterLife {
    void onCreate();

    void onBindView(@NonNull BaseView view);

    void onDestroy();
}
