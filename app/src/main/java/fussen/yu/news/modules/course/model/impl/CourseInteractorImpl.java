package fussen.yu.news.modules.course.model.impl;

import java.util.Map;

import javax.inject.Inject;

import example.fussen.baselibrary.callback.RequestCallBack;
import fussen.yu.news.modules.course.bean.CourseData;
import fussen.yu.news.modules.course.model.CourseInteractor;
import fussen.yu.news.utils.UiUtils;
import fussen.yu.news.utils.network.NetConfig;
import fussen.yu.news.utils.network.NetworkUtils;
import fussen.yu.news.utils.network.callback.ResponseCallBack;
import rx.Subscription;

/**
 * Created by Fussen on 2016/12/28.
 */

public class CourseInteractorImpl implements CourseInteractor<CourseData> {


    @Inject
    public CourseInteractorImpl() {
    }

    @Override
    public Subscription getAllCourseType(Map<String, String> params, final RequestCallBack<CourseData> callBack) {


        return NetworkUtils.getInstance(UiUtils.getContext()).executePost(NetConfig.GET_ALL_TYPE, new ResponseCallBack<CourseData>() {
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
                callBack.onError(e.getMessage(), false);
            }

            @Override
            public void onSuccee(CourseData response) {
                callBack.onSuccess(response);
            }
        });
    }
}
