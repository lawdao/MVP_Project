package fussen.yu.news.login.model.impl;

import java.util.Map;

import javax.inject.Inject;

import example.fussen.baselibrary.network.NetConfig;
import example.fussen.baselibrary.network.NetworkUtils;
import example.fussen.baselibrary.network.callback.RequestCallBack;
import example.fussen.baselibrary.network.callback.ResponseCallBack;
import example.fussen.baselibrary.utils.LogUtil;
import example.fussen.baselibrary.utils.PreferUtils;
import fussen.yu.news.login.bean.UserInfo;
import fussen.yu.news.login.model.LoginInteractor;
import fussen.yu.news.utils.UiUtils;
import rx.Subscription;

/**
 * Created by Fussen on 2016/11/29.
 * <p>
 * 此类为model层，在此类中做耗时操作
 */

public class LoginInteractorImpl implements LoginInteractor<UserInfo> {

    @Inject
    public LoginInteractorImpl() {
    }


    /**
     * 登录app
     *
     * @param params   账户信息
     * @param callBack 回调
     * @return
     */
    @Override
    public Subscription loginApp(Map<String, String> params, final RequestCallBack<UserInfo> callBack) {

        LogUtil.fussenLog().d("1008611" + "========model层开始登陆========");

        params.put("loginType", "1");
        params.put("loginSource", "2");
        params.put("type", "2");//用户身份1:学员 2:教练

        return NetworkUtils.getInstance(UiUtils.getContext()).executePost(NetConfig.LOGING_URL, params, new ResponseCallBack<UserInfo>() {
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
                LogUtil.fussenLog().d("1008611"+"=====onError===Login======="+e.getMessage());
                PreferUtils.getInstance(UiUtils.getContext()).setAppIsLogin(false);
                callBack.onError(e.getMessage());

            }

            @Override
            public void onSuccee(UserInfo info) {
                LogUtil.fussenLog().d("1008611"+"=====onSuccee===Login======="+info.nickName);
                PreferUtils.getInstance(UiUtils.getContext()).setAppIsLogin(true);
                callBack.onSuccess(info);
            }
        });

    }
}
