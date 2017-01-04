package fussen.yu.news.modules.login.model;

import java.util.Map;

import fussen.yu.news.utils.network.callback.RequestCallBack;
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

public interface LoginInteractor<T> {
    Subscription loginApp(Map<String, String> accountInfo,RequestCallBack<T> callBack);
}
