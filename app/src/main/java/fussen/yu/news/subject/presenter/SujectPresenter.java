package fussen.yu.news.subject.presenter;

import java.util.Map;

import example.fussen.baselibrary.base.presenter.PresenterLife;


/**
 * Created by Fussen on 2016/11/29.
 * <p>
 * 此接口是view层操作presenter层，presenter须实现此接口
 *
 * 在view层调用接口中的方法
 */

public interface SujectPresenter extends PresenterLife {
    void getSubject(Map<String, String> params);
}
