package fussen.yu.news.utils;

import android.content.Context;

import fussen.yu.news.App;

/**
 * Created by Fussen on 2016/11/23.
 */

public class UiUtils {

    public static Context getContext(){
        return App.getContext();
    }


    /**
     * dp转px
     */
    public static int dip2px(float dpValue) {
        final float scale =App.getContext().getResources()
                .getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }


}
