package fussen.yu.news.modules.login.activity;

import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;

import butterknife.BindView;
import butterknife.OnClick;
import fussen.yu.news.R;
import fussen.yu.news.base.activity.BaseActivity;
import fussen.yu.news.modules.course.fragment.CourseFragment;
import fussen.yu.news.modules.subject.fragment.SubjectFragment;
import fussen.yu.news.modules.user.fragment.UserFragment;

public class HomeActivity extends BaseActivity implements Toolbar.OnMenuItemClickListener {


    @BindView(R.id.fl_content)
    FrameLayout flContent;
    @BindView(R.id.bottom_navigation)
    AHBottomNavigation bottomNavigation;
    @BindView(R.id.content_main)
    LinearLayout contentMain;
    @BindView(R.id.nav_view)
    NavigationView navView;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    private FragmentManager fragmentManager;

    private SubjectFragment subjectFragment;
    private CourseFragment courseFragment;
    private UserFragment userFragment;

    protected Fragment currentFragment;


    @Override
    protected int getContentView() {
        return R.layout.activity_home;
    }

    @Override
    protected void initInject() {

        mActivityComponent.inject(this);

    }

    @Override
    protected void initView() {
        isNeedNavigationView = true;

        toolbar.inflateMenu(R.menu.menu_main);
        toolbar.setOnMenuItemClickListener(this);

        initTabs();

        initFirstFragment();
        initListener();

    }

    private void initFirstFragment() {

        fragmentManager = getSupportFragmentManager();

        FragmentTransaction transaction = fragmentManager.beginTransaction();

        if (subjectFragment == null) {
            subjectFragment = new SubjectFragment();
        }

        if (!subjectFragment.isAdded()) {

            transaction.add(R.id.fl_content, subjectFragment).commit();

            currentFragment = subjectFragment;
        }
        title.setText(getString(R.string.tab_1));

    }

    private void initTabs() {


        // Create items
        AHBottomNavigationItem item1 = new AHBottomNavigationItem(getResources().getString(R.string.tab_1), R.drawable.tab_subject);
        AHBottomNavigationItem item2 = new AHBottomNavigationItem(getResources().getString(R.string.tab_2), R.drawable.tab_sport);
        AHBottomNavigationItem item3 = new AHBottomNavigationItem(getResources().getString(R.string.tab_3), R.drawable.tab_me);


        // Add items
        bottomNavigation.addItem(item1);
        bottomNavigation.addItem(item2);
        bottomNavigation.addItem(item3);


        // 默认背景颜色
        bottomNavigation.setDefaultBackgroundColor(ContextCompat.getColor(this, R.color.bg_bottom_navigation));
        // 切换时颜色的转变
        bottomNavigation.setAccentColor(ContextCompat.getColor(this, R.color.accent_bottom_navigation));
        bottomNavigation.setInactiveColor(ContextCompat.getColor(this, R.color.inactive_bottom_navigation));
        // 强制着色
        bottomNavigation.setForceTint(true);
        // 强制展示标题
        bottomNavigation.setForceTitlesDisplay(true);
        // 使用圆圈效果
        bottomNavigation.setColored(false);
        // Set current item programmatically
        bottomNavigation.setCurrentItem(0);
    }


    private void initListener() {

        bottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public boolean onTabSelected(int position, boolean wasSelected) {

                Log.d("[homeActivity]", "onTabSelected: position:" + position + ",wasSelected:" + wasSelected);

                chooseTabs(position);

                return true;
            }
        });

    }

    private void chooseTabs(int position) {

        switch (position) {
            case 0:
                if (subjectFragment == null) {
                    subjectFragment = new SubjectFragment();
                }
                addOrShowFragment(subjectFragment);
                break;
            case 1:
                if (courseFragment == null) {
                    courseFragment = new CourseFragment();
                }
                addOrShowFragment(courseFragment);
                break;
            case 2:
                if (userFragment == null) {
                    userFragment = new UserFragment();
                }
                addOrShowFragment(userFragment);
                break;
        }
    }


    /**
     * 添加或者显示 fragment
     *
     * @param fragment
     */
    protected void addOrShowFragment(Fragment fragment) {
        if (currentFragment == fragment)
            return;

        FragmentTransaction transaction = fragmentManager.beginTransaction();

        if (!fragment.isAdded()) { // 如果当前fragment未被添加，则添加到Fragment管理器中
            transaction.hide(currentFragment).add(R.id.fl_content, fragment).commit();
        } else {
            transaction.hide(currentFragment).show(fragment).commit();
        }
        currentFragment = fragment;

        setToolbar();
    }

    private void setToolbar() {
        if (currentFragment instanceof SubjectFragment) {
            title.setText(getString(R.string.tab_1));
        } else if (currentFragment instanceof CourseFragment) {
            title.setText(getString(R.string.tab_2));
        } else if (currentFragment instanceof UserFragment) {
            title.setText(getString(R.string.tab_3));
        }
    }


    @OnClick({R.id.iv_back, R.id.tv_toolbar_right})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                break;
            case R.id.tv_toolbar_right:
                break;
        }
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        return false;
    }
}
