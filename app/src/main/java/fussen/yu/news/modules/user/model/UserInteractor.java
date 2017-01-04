package fussen.yu.news.modules.user.model;

import java.util.Map;

import fussen.yu.news.utils.network.callback.RequestCallBack;
import rx.Subscription;

/**
 * Created by Fussen on 2016/12/28.
 */

public interface UserInteractor<T> {
    Subscription getAllCourseType(Map<String, String> params, RequestCallBack<T> callBack);
}
