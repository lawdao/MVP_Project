package fussen.yu.news.inject.module;

import android.app.Service;
import android.content.Context;

import dagger.Module;
import dagger.Provides;
import fussen.yu.news.inject.scope.ContextLife;
import fussen.yu.news.inject.scope.ForService;

/**
 * Created by Fussen on 2016/11/28.
 */

@Module
public class ServiceModule {

    private Service mService;

    public ServiceModule(Service service) {
        this.mService = service;
    }


    @Provides
    @ForService
    @ContextLife("Service")
    public Context provideServiceContext() {
        return mService;
    }
}
