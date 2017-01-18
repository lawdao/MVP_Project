package example.fussen.baselibrary.base.view;

/**
 * Created by Fussen on 2016/11/23.
 * <p>
 * 所有view的基类
 */

public interface BaseView extends MvpView {
    void showProgress(boolean pullToRefresh);

    void hideProgress();

    void showErrorMsg(String errorMsg,boolean pullToRefresh);

    void loadData(boolean pullToRefresh);
}
