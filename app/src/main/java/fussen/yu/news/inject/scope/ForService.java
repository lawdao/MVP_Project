package fussen.yu.news.inject.scope;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Created by Fussen on 2016/11/28.
 */
@Scope
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface ForService {
}
