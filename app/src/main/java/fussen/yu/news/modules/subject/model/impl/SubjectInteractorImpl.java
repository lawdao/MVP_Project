package fussen.yu.news.modules.subject.model.impl;

import java.util.Map;

import javax.inject.Inject;

import fussen.yu.news.modules.subject.bean.WeekEvent;
import fussen.yu.news.modules.subject.model.SubjectInteractor;
import fussen.yu.news.utils.network.NetConfig;
import fussen.yu.news.utils.network.NetworkUtils;
import fussen.yu.news.utils.network.callback.RequestCallBack;
import fussen.yu.news.utils.network.callback.ResponseCallBack;
import example.fussen.baselibrary.utils.LogUtil;
import fussen.yu.news.App;
import rx.Subscription;

/**
 * Created by Fussen on 2016/11/29.
 * <p>
 * 此类为model层，在此类中做耗时操作
 */

public class SubjectInteractorImpl implements SubjectInteractor<WeekEvent> {

    @Inject
    public SubjectInteractorImpl() {
    }


    @Override
    public Subscription getSubject(Map<String, String> params, final RequestCallBack<WeekEvent> callBack) {


        return NetworkUtils.getInstance(App.getContext()).executePost(NetConfig.GETSUBJECT_URL, params, new ResponseCallBack<WeekEvent>() {
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
                LogUtil.fussenLog().d("1008611" + "========onError========" + e.getMessage());
            }

            @Override
            public void onSuccee(WeekEvent response) {
                LogUtil.fussenLog().d("1008611" + "========onSuccee========" + response.workTime.endTime);
                callBack.onSuccess(response);
            }
        });

    }

}
