package fussen.yu.news.inject.component;

import android.content.Context;

import dagger.Component;
import fussen.yu.news.inject.module.ServiceModule;
import fussen.yu.news.inject.scope.ContextLife;
import fussen.yu.news.inject.scope.ForService;

/**
 * Created by Fussen on 2016/11/28.
 */


@ForService
@Component(dependencies = ApplicationComponent.class, modules = ServiceModule.class)
public interface ServiceComponent {

    @ContextLife("Service")
    Context getServiceContext();

    @ContextLife("Application")
    Context getApplicationContext();
}
