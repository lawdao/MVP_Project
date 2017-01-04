package fussen.yu.news.inject.component;

import android.app.Activity;
import android.content.Context;

import dagger.Component;
import fussen.yu.news.inject.module.ActivityModule;
import fussen.yu.news.inject.scope.ContextLife;
import fussen.yu.news.inject.scope.ForActivity;
import fussen.yu.news.modules.login.activity.HomeActivity;
import fussen.yu.news.modules.login.activity.LoginActivity;

/**
/**
 * Created by Fussen on 2016/11/28.
 */


@ForActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    @ContextLife("Activity")
    Context getActivityContext();

    @ContextLife("Application")
    Context getApplicationContext();

    Activity getActivity();

    void inject(HomeActivity homeActivity);

    void inject(LoginActivity loginActivity);
}

