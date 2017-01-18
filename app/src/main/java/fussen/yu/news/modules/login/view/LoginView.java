package fussen.yu.news.modules.login.view;

import example.fussen.baselibrary.base.view.BaseView;
import fussen.yu.news.modules.login.bean.UserInfo;

/**
 * Created by Fussen on 2016/11/29.
 *
 * 此接口是presenter层操作view层
 *
 * view层须实现此接口，并实现该接口的方法
 *
 * 调用在presenter层调用即可
 */

public interface LoginView extends BaseView {
    void toHomeActivity(UserInfo data);
}
