package fussen.yu.news.inject.component;

import android.app.Activity;
import android.content.Context;

import dagger.Component;
import fussen.yu.news.modules.course.fragment.CourseFragment;
import fussen.yu.news.inject.module.FragmentModule;
import fussen.yu.news.inject.scope.ContextLife;
import fussen.yu.news.inject.scope.ForFragment;
import fussen.yu.news.modules.subject.fragment.SubjectFragment;
import fussen.yu.news.modules.user.fragment.UserFragment;

/**
 * Created by Fussen on 2016/11/28.
 */
@ForFragment
@Component(dependencies = ApplicationComponent.class, modules = FragmentModule.class)
public interface FragmentComponent {

    @ContextLife("Activity")
    Context getActivityContext();

    @ContextLife("Application")
    Context getApplicationContext();


    Activity getActivity();

    void inject(SubjectFragment fragment);
    void inject(CourseFragment fragment);
    void inject(UserFragment fragment);
}
