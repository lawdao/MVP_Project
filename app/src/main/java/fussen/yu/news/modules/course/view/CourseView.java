package fussen.yu.news.modules.course.view;

import example.fussen.baselibrary.base.view.BaseView;
import fussen.yu.news.modules.course.bean.CourseData;

/**
 * Created by Fussen on 2016/12/28.
 */

public interface CourseView extends BaseView {
    void loadDataSucces(CourseData data);
}
