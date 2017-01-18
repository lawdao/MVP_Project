package fussen.yu.news.modules.user.presenter.impl;

import java.io.File;

import javax.inject.Inject;

import example.fussen.baselibrary.base.presenter.BasePresenter;
import example.fussen.baselibrary.callback.RequestCallBack;
import fussen.yu.news.modules.user.bean.UpLoad;
import fussen.yu.news.modules.user.model.UserInteractor;
import fussen.yu.news.modules.user.model.impl.UserInteractorImpl;
import fussen.yu.news.modules.user.presenter.UserPresenter;
import fussen.yu.news.modules.user.view.UserView;

/**
 * Created by Fussen on 2016/12/28.
 */

public class UserPresenterImpl extends BasePresenter<UserView, Object> implements UserPresenter, RequestCallBack<Object> {


    private UserInteractor mUserInteractor;

    @Inject
    public UserPresenterImpl(UserInteractorImpl userInteractor) {
        this.mUserInteractor = userInteractor;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }


    @Override
    public void onSuccess(Object data) {
        super.onSuccess(data);
        if (data instanceof UpLoad) {
            getView().upLoadImageSucce((UpLoad) data);
        }
    }

    @Override
    public void onProgress(long downSize, long fileSize) {
        if (isViewAttached()) {
            getView().onProgress(downSize, fileSize);
        }
    }

    @Override
    public void dowloadSuccess(String path, String fileName, long fileSize) {
        super.dowloadSuccess(path, fileName, fileSize);
        if (isViewAttached())
            getView().downLoadImageSucce(path);
    }

    @Override
    public void upLoadImage(File file) {
        mSubscription = mUserInteractor.upLoadImage(file, this);
    }

    @Override
    public void downLoadImage(String url) {
        getView().showProgress(false);
        mSubscription = mUserInteractor.downLoadImage(url, this);
    }
}
