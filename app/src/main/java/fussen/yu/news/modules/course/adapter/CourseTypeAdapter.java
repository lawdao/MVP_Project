package fussen.yu.news.modules.course.adapter;

import java.util.ArrayList;

import fussen.yu.news.base.adapter.BaseHolder;
import fussen.yu.news.base.adapter.MyBaseAdapter;
import fussen.yu.news.modules.course.adapter.holder.CourseTypeHolder;
import fussen.yu.news.modules.course.bean.CourseData;

/**
 * Created by Fussen on 2016/12/28.
 */

public class CourseTypeAdapter extends MyBaseAdapter<CourseData.DataListUser> {
    public CourseTypeAdapter(ArrayList<CourseData.DataListUser> data) {
        super(data);
    }

    @Override
    public BaseHolder getHolder(int position) {
        return new CourseTypeHolder();
    }
}
