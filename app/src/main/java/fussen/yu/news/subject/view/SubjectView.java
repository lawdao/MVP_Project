package fussen.yu.news.subject.view;

import example.fussen.baselibrary.base.view.BaseView;
import fussen.yu.news.subject.bean.WeekEvent;

/**
 * Created by Fussen on 2016/12/16.
 */

public interface SubjectView extends BaseView {
    void refreshView(WeekEvent data);
}
