package fussen.yu.news.subject.model;

import java.util.Map;

import example.fussen.baselibrary.network.callback.RequestCallBack;
import rx.Subscription;

/**
 * Created by Fussen on 2016/11/29.
 * <p>
 * 此接口为presenter层操作model层
 * <p>
 * 在model层实现此接口
 * <p>
 * 在presenter层调用接口中的方法
 */

public interface SubjectInteractor<T> {
    Subscription getSubject(Map<String, String> params, RequestCallBack<T> callBack);
}
