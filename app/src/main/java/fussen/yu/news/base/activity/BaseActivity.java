package fussen.yu.news.base.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import fussen.yu.news.base.presenter.PresenterLife;
import fussen.yu.news.App;
import fussen.yu.news.R;
import fussen.yu.news.inject.component.ActivityComponent;
import fussen.yu.news.inject.component.DaggerActivityComponent;
import fussen.yu.news.inject.module.ActivityModule;
import fussen.yu.news.modules.login.activity.LoginActivity;
import fussen.yu.news.utils.ToastUtil;

/**
 * Created by Fussen on 2016/11/23.
 */

public abstract class BaseActivity<P extends PresenterLife> extends AppCompatActivity {

    protected ImageView back;
    protected TextView cancel;
    protected TextView title;
    protected TextView confirm;
    protected ImageView more;
    protected View titleView;

    private Unbinder mUbinder;

    protected P mPresenter;

    protected boolean isNeedNavigationView;
    protected DrawerLayout mDrawerLayout;
    private Class mClass;
    protected ActivityComponent mActivityComponent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentView());

        initBaseTile();

        initComponent();

        initToolBar();

        initView();

        if (isNeedNavigationView) {
            initDrawerLayout();
        }

        if (mPresenter != null) {
            mPresenter.onCreate();
        }
    }


    @Override
    public void onContentChanged() {
        super.onContentChanged();
        mUbinder = ButterKnife.bind(this);
    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        if (isNeedNavigationView) {
            overridePendingTransition(0, 0);
        }
    }


    private void initBaseTile() {

        titleView = findViewById(R.id.base_title_view);
        back = (ImageView) findViewById(R.id.base_back);
        cancel = (TextView) findViewById(R.id.base_cancel);
        title = (TextView) findViewById(R.id.base_title);
        confirm = (TextView) findViewById(R.id.base_confirm);
        more = (ImageView) findViewById(R.id.base_more);
    }


    private void initToolBar() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }


    private void initDrawerLayout() {

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        NavigationView navView = (NavigationView) findViewById(R.id.nav_view);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        if (navView != null) {
            navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.nav_news:
                            ToastUtil.showToast("新闻");
                            break;
                        case R.id.nav_photo:
                            ToastUtil.showToast("图片");
                            break;
                        case R.id.nav_video:
                            ToastUtil.showToast("视频");
                            break;
                        case R.id.nav_night_mode:
                            ToastUtil.showToast("夜间");
                            break;
                        case R.id.nav_login:

//                            startActivity(new Intent(UiUtils.getContext(), LoginActivity.class));
                            mClass = LoginActivity.class;
                            break;
                    }
                    mDrawerLayout.closeDrawer(GravityCompat.START);
                    return false;
                }
            });


            mDrawerLayout.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
                @Override
                public void onDrawerClosed(View drawerView) {
                    super.onDrawerClosed(drawerView);
                    if (mClass != null) {
                        Intent intent = new Intent(BaseActivity.this, mClass);
                        // 此标志用于启动一个Activity的时候，若栈中存在此Activity实例，则把它调到栈顶。不创建多一个
                        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                        startActivity(intent);
                        overridePendingTransition(0, 0);
                        mClass = null;
                    }
                }
            });
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    finishAfterTransition();
                } else {
                    finish();
                }
                break;
            case R.id.action_about:
                if (isNeedNavigationView) {
                    ToastUtil.showToast("about");
                }
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (isNeedNavigationView) {
            getMenuInflater().inflate(R.menu.main, menu);
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (mPresenter != null) {
            mPresenter.onDestroy();
            mPresenter = null;
        }
        mUbinder.unbind();
    }

    @Override
    public void onBackPressed() {
        if (isNeedNavigationView && mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private void initComponent() {

        mActivityComponent = DaggerActivityComponent.builder()
                .applicationComponent(App.getmApplicationComponent())
                .activityModule(new ActivityModule(this))
                .build();

        initInject();

    }

    protected abstract int getContentView();

    protected abstract void initInject();

    protected abstract void initView();
}
