package fussen.yu.news.inject.component;

import android.content.Context;

import dagger.Component;
import fussen.yu.news.inject.module.ApplicationModule;
import fussen.yu.news.inject.scope.ContextLife;
import fussen.yu.news.inject.scope.ForApp;

/**
 * Created by Fussen on 2016/11/28.
 */

@ForApp
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {
    @ContextLife("Application")
    Context getApplication();
}
