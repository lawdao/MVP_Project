package fussen.yu.news.modules.login.model.impl;

import java.util.Map;

import javax.inject.Inject;

import example.fussen.baselibrary.callback.RequestCallBack;
import example.fussen.baselibrary.utils.LogUtil;
import example.fussen.baselibrary.utils.TransformUtils;
import fussen.yu.news.User;
import fussen.yu.news.modules.login.bean.UserInfo;
import fussen.yu.news.modules.login.model.LoginInteractor;
import fussen.yu.news.utils.PreferUtils;
import fussen.yu.news.utils.db.DbUtils;
import fussen.yu.news.utils.network.NetConfig;
import fussen.yu.news.utils.network.NetworkUtils;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Action1;

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
    public Subscription loginApp(final Map<String, String> params, final RequestCallBack<UserInfo> callBack) {

        LogUtil.fussenLog().d("1008611" + "========model层开始登陆========");

        params.put("loginType", "1");
        params.put("loginSource", "2");
        params.put("type", "2");//用户身份1:学员 2:教练


        return NetworkUtils.getLogin()
                .toLogin(NetConfig.LOGING_URL, params)
                .doOnNext(new Action1<UserInfo>() {
                    @Override
                    public void call(UserInfo info) {
                        User user = new User();
                        user.setUid(info.userId);
                        user.setNickName(info.nickName);
                        user.setAvatarUrl(info.avatarUrl);
                        user.setGender(info.gender);
                        user.setMobile(info.mobile);
                        DbUtils.insertUser(user);

                    }
                })
                .compose(TransformUtils.schedulersTransformer())
                .subscribe(new Subscriber<UserInfo>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.fussenLog().d("1008611" + "=====onError====" + e.getMessage());
                        callBack.onError(e.getMessage(), false);
                        PreferUtils.getInstance().setAppIsLogin(false);
                    }

                    @Override
                    public void onNext(UserInfo info) {

                        LogUtil.fussenLog().d("1008611" + "=====onNext====" + info.nickName);
                        PreferUtils.getInstance().setUserUid(info.userId);
                        callBack.onSuccess(info);
                        PreferUtils.getInstance().setAppIsLogin(true);
                    }
                });

    }

}
