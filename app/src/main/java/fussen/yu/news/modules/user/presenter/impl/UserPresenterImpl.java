package fussen.yu.news.modules.user.presenter.impl;

import java.util.Map;

import javax.inject.Inject;

import fussen.yu.news.base.presenter.BasePresenter;
import fussen.yu.news.modules.course.bean.CourseData;
import fussen.yu.news.modules.course.model.CourseInteractor;
import fussen.yu.news.modules.course.model.impl.CourseInteractorImpl;
import fussen.yu.news.modules.course.presenter.CoursePresenter;
import fussen.yu.news.modules.course.view.CourseView;
import fussen.yu.news.modules.user.presenter.UserPresenter;

/**
 * Created by Fussen on 2016/12/28.
 */

public class UserPresenterImpl extends BasePresenter<CourseView, CourseData> implements UserPresenter {


    private CourseInteractor<CourseData> mCourseInteractor;

    @Inject
    public UserPresenterImpl(CourseInteractorImpl courseInteractor) {
        this.mCourseInteractor = courseInteractor;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }


    @Override
    public void onSuccess(CourseData data) {
        super.onSuccess(data);
        getView().loadData(data);
    }

}
