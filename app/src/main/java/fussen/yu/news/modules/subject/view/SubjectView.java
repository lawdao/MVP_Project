package fussen.yu.news.modules.subject.view;

import fussen.yu.news.base.view.BaseView;
import fussen.yu.news.modules.subject.bean.WeekEvent;

/**
 * Created by Fussen on 2016/12/16.
 */

public interface SubjectView extends BaseView {
    void refreshView(WeekEvent data);
}
