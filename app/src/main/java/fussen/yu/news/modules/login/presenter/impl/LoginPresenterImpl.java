package fussen.yu.news.modules.login.presenter.impl;


import java.util.Map;

import javax.inject.Inject;

import fussen.yu.news.base.presenter.BasePresenter;
import fussen.yu.news.modules.login.bean.UserInfo;
import fussen.yu.news.modules.login.model.LoginInteractor;
import fussen.yu.news.modules.login.model.impl.LoginInteractorImpl;
import fussen.yu.news.modules.login.presenter.LoginPresenter;
import fussen.yu.news.modules.login.view.LoginView;

/**
 * Created by Fussen on 2016/11/29.
 * <p>
 * 当presenter创建的时候，此时model层也应该随之被创建，可以准备获取数据
 */

public class LoginPresenterImpl extends BasePresenter<LoginView, UserInfo> implements LoginPresenter {

    private LoginInteractor<UserInfo> mLoginInteractor;

    @Inject
    public LoginPresenterImpl(LoginInteractorImpl loginInteractor) {
        this.mLoginInteractor = loginInteractor;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }


    @Override
    public void login(Map<String, String> accountInfo) {
        getView().showProgress();
        toLogin(accountInfo);
    }

    /**
     * 调用model层的方法
     *
     * @param accountInfo
     */
    private void toLogin(Map<String, String> accountInfo) {
        mSubscription = mLoginInteractor.loginApp(accountInfo, this);
    }


    /**
     * 访问网络成功回调
     *
     * @param data
     */
    @Override
    public void onSuccess(UserInfo data) {
        super.onSuccess(data);
        getView().toHomeActivity(data);
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
}
