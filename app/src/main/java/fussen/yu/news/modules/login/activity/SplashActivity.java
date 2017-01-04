package fussen.yu.news.modules.login.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.concurrent.TimeUnit;

import fussen.yu.news.utils.PreferUtils;
import fussen.yu.news.utils.UiUtils;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;


public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        toHomeActivity();
    }


    private void toHomeActivity() {
        Observable.timer(2000, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
                        Intent intent = new Intent();

                        if (PreferUtils.getInstance().getAppIsLogin()) {
                            intent.setClass(UiUtils.getContext(), HomeActivity.class);
                        } else {
                            intent.setClass(UiUtils.getContext(), LoginActivity.class);
                        }
//                        intent.setClass(UiUtils.getContext(), LoginActivity.class);
                        startActivity(intent);
                        overridePendingTransition(0, android.R.anim.fade_out);
                        finish();
                    }
                });
    }
}
