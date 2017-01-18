package fussen.yu.news.modules.subject.presenter.impl;


import java.util.Map;

import javax.inject.Inject;

import example.fussen.baselibrary.base.presenter.BasePresenter;
import example.fussen.baselibrary.callback.RequestCallBack;
import fussen.yu.news.modules.subject.bean.WeekEvent;
import fussen.yu.news.modules.subject.model.SubjectInteractor;
import fussen.yu.news.modules.subject.model.impl.SubjectInteractorImpl;
import fussen.yu.news.modules.subject.presenter.SujectPresenter;
import fussen.yu.news.modules.subject.view.SubjectView;

/**
 * Created by Fussen on 2016/11/29.
 * <p>
 * 当presenter创建的时候，此时model层也应该随之被创建，可以准备获取数据
 */

public class SubjectPresenterImpl extends BasePresenter<SubjectView, WeekEvent> implements SujectPresenter, RequestCallBack<WeekEvent> {

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
        if (isViewAttached())
            getView().refreshView(data);
    }


    /**
     * 访问网络失败回调
     *
     * @param errorMsg
     * @param pullToRefresh
     */
    @Override
    public void onError(String errorMsg, boolean pullToRefresh) {
        super.onError(errorMsg, pullToRefresh);
    }


    /**
     * 正式访问网络 展示加载对话框
     * @param params
     * @param pullToRefresh
     */
    @Override
    public void getSubject(Map<String, String> params, boolean pullToRefresh) {

        getView().showProgress(pullToRefresh);

        mSubscription = mSubjectInteractor.getSubject(params, this);
    }
}
