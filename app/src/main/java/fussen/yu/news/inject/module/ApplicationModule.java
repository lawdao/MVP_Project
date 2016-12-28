package fussen.yu.news.inject.module;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import fussen.yu.news.App;
import fussen.yu.news.inject.scope.ContextLife;
import fussen.yu.news.inject.scope.ForApp;

/**
 * Created by Fussen on 2016/11/28.
 */

@Module
public class ApplicationModule {
    private App mApplication;

    public ApplicationModule(App application) {
        mApplication = application;
    }

    @Provides
    @ForApp
    @ContextLife("Application")
    public Context provideApplicationContext() {
        return mApplication.getApplicationContext();
    }
}
