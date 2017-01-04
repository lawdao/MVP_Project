package fussen.yu.news.modules.course.model;

import java.util.Map;

import fussen.yu.news.utils.network.callback.RequestCallBack;
import rx.Subscription;

/**
 * Created by Fussen on 2016/12/28.
 */

public interface CourseInteractor<T> {
    Subscription getAllCourseType(Map<String, String> params, RequestCallBack<T> callBack);
}
