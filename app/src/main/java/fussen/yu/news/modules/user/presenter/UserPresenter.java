package fussen.yu.news.modules.user.presenter;

import java.io.File;

import example.fussen.baselibrary.base.presenter.PresenterLife;

/**
 * Created by Fussen on 2016/12/28.
 */

public interface UserPresenter extends PresenterLife {

    void upLoadImage(File file);

    void downLoadImage(String url);
}
