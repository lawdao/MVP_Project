package example.fussen.baselibrary.utils;

import android.os.Looper;

import rx.Subscription;

/**
 * Created by Fussen on 2016/12/23.
 */

public class AppUtil {
    /**
     * 解除订阅
     *
     * @param subscription
     */
    public static void cancelSubscription(Subscription subscription) {
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
            subscription = null;
        }
    }


    /**
     * 检查是否在主线程内
     *
     * @return
     */
    public static boolean checkMain() {
        return Thread.currentThread() == Looper.getMainLooper().getThread();
    }
}
