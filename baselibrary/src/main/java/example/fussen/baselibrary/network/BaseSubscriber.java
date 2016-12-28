package example.fussen.baselibrary.network;

import rx.Subscriber;

/**
 * Created by Fussen on 2016/12/22.
 */

public abstract class BaseSubscriber<T> extends Subscriber<T> {
    @Override
    public void onCompleted() {

    }


    @Override
    public void onError(Throwable e) {
    }

    @Override
    public void onNext(T t) {

    }
}
