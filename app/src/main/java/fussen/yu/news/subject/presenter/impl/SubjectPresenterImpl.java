package fussen.yu.news.subject.presenter.impl;


import java.util.Map;

import javax.inject.Inject;

import example.fussen.baselibrary.base.presenter.BasePresenter;
import fussen.yu.news.subject.bean.WeekEvent;
import fussen.yu.news.subject.model.SubjectInteractor;
import fussen.yu.news.subject.model.impl.SubjectInteractorImpl;
import fussen.yu.news.subject.presenter.SujectPresenter;
import fussen.yu.news.subject.view.SubjectView;

/**
 * Created by Fussen on 2016/11/29.
 * <p>
 * 当presenter创建的时候，此时model层也应该随之被创建，可以准备获取数据
 */

public class SubjectPresenterImpl extends BasePresenter<SubjectView, WeekEvent> implements SujectPresenter {

    private SubjectInteractor<WeekEvent> mSubjectInteractor;

    @Inject
    public SubjectPresenterImpl(SubjectInteractorImpl subjectInteractor) {
        this.mSubjectInteractor = subjectInteractor;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }


    /**
     * 访问网络成功回调
     *
     * @param data
     */
    @Override
    public void onSuccess(WeekEvent data) {
        super.onSuccess(data);
        getView().refreshView(data);
    }


    /**
     * 访问网络失败回调
     *
     * @param errorMsg
     */
    @Override
    public void onError(String errorMsg) {
        super.onError(errorMsg);
    }

    @Override
    public void getSubject(Map<String, String> params) {
        mSubscription = mSubjectInteractor.getSubject(params, this);
    }
}
