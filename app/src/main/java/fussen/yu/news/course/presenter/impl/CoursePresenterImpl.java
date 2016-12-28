package fussen.yu.news.course.presenter.impl;

import java.util.Map;

import javax.inject.Inject;

import example.fussen.baselibrary.base.presenter.BasePresenter;
import fussen.yu.news.course.bean.CourseData;
import fussen.yu.news.course.model.CourseInteractor;
import fussen.yu.news.course.model.impl.CourseInteractorImpl;
import fussen.yu.news.course.presenter.CoursePresenter;
import fussen.yu.news.course.view.CourseView;

/**
 * Created by Fussen on 2016/12/28.
 */

public class CoursePresenterImpl extends BasePresenter<CourseView, CourseData> implements CoursePresenter {


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
    public void getAllCourseType(Map<String, String> params) {

        mSubscription = mCourseInteractor.getAllCourseType(null,this);
    }


    @Override
    public void onSuccess(CourseData data) {
        super.onSuccess(data);
        getView().loadData(data);
    }

}
