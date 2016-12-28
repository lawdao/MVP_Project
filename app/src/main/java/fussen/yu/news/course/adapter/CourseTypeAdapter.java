package fussen.yu.news.course.adapter;

import java.util.ArrayList;

import example.fussen.baselibrary.base.adapter.BaseHolder;
import example.fussen.baselibrary.base.adapter.MyBaseAdapter;
import fussen.yu.news.course.adapter.holder.CourseTypeHolder;
import fussen.yu.news.course.bean.CourseData;

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
