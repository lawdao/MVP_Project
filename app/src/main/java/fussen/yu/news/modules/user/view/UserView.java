package fussen.yu.news.modules.user.view;

import example.fussen.baselibrary.base.view.BaseView;
import fussen.yu.news.modules.user.bean.UpLoad;

/**
 * Created by Fussen on 2017/1/9.
 */

public interface UserView extends BaseView {
    void upLoadImageSucce(UpLoad upLoad);
    void downLoadImageSucce(String path);
    void onProgress(long downSize, long fileSize);
}
