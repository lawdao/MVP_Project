package fussen.yu.news.course.view;

import example.fussen.baselibrary.base.view.BaseView;
import fussen.yu.news.course.bean.CourseData;

/**
 * Created by Fussen on 2016/12/28.
 */

public interface CourseView extends BaseView{
    void loadData(CourseData data);
}
