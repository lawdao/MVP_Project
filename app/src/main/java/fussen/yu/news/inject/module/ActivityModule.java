package fussen.yu.news.inject.module;

import android.app.Activity;
import android.content.Context;

import dagger.Module;
import dagger.Provides;
import fussen.yu.news.inject.scope.ContextLife;
import fussen.yu.news.inject.scope.ForActivity;

/**
 * Created by Fussen on 2016/11/28.
 */

@Module
public class ActivityModule {

    private Activity mActivity;

    public ActivityModule(Activity activity) {
        this.mActivity = activity;
    }


    @Provides
    @ForActivity
    @ContextLife("Activity")
    public Context provideActivityContext() {
        return mActivity;
    }


    @Provides
    @ForActivity
    public Activity provideActivity() {
        return mActivity;
    }
}
