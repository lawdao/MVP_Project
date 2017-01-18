package fussen.yu.news.modules.course.presenter.impl;

import java.util.Map;

import javax.inject.Inject;

import example.fussen.baselibrary.base.presenter.BasePresenter;
import example.fussen.baselibrary.callback.RequestCallBack;
import fussen.yu.news.modules.course.bean.CourseData;
import fussen.yu.news.modules.course.model.CourseInteractor;
import fussen.yu.news.modules.course.model.impl.CourseInteractorImpl;
import fussen.yu.news.modules.course.presenter.CoursePresenter;
import fussen.yu.news.modules.course.view.CourseView;

/**
 * Created by Fussen on 2016/12/28.
 */

public class CoursePresenterImpl extends BasePresenter<CourseView, CourseData> implements CoursePresenter, RequestCallBack<CourseData> {


    private CourseInteractor<CourseData> mCourseInteractor;

    @Inject
    public CoursePresenterImpl(CourseInteractorImpl courseInteractor) {
        this.mCourseInteractor = courseInteractor;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void getAllCourseType(Map<String, String> params, boolean pullToRefresh) {

        getView().showProgress(pullToRefresh);

        mSubscription = mCourseInteractor.getAllCourseType(null, this);
    }


    @Override
    public void onSuccess(CourseData data) {
        super.onSuccess(data);
        if (isViewAttached())
            getView().loadDataSucces(data);
    }

}
