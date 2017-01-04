package fussen.yu.news.base.view;

/**
 * Created by Fussen on 2016/11/23.
 * <p>
 * 所有view的基类
 */

public interface BaseView extends MvpView {
    void showProgress();

    void hideProgress();

    void showErrorMsg(String errorMsg);
}
