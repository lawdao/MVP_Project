package fussen.yu.news.inject.module;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;

import dagger.Module;
import dagger.Provides;
import fussen.yu.news.inject.scope.ContextLife;
import fussen.yu.news.inject.scope.ForFragment;

/**
 * Created by Fussen on 2016/11/28.
 */

@Module
public class FragmentModule {
    private Fragment mFragment;

    public FragmentModule(Fragment fragment) {
        this.mFragment = fragment;
    }


    @Provides
    @ForFragment
    @ContextLife("Activity")
    public Context provideActivityContext() {
        return mFragment.getActivity();
    }


    @Provides
    @ForFragment
    public Activity provideActivity() {
        return mFragment.getActivity();
    }


    @Provides
    @ForFragment
    public Fragment provideFragment() {
        return mFragment;
    }
}
