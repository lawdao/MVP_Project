package fussen.yu.news.modules.login.activity;

import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import fussen.yu.news.R;
import fussen.yu.news.base.activity.BaseActivity;
import fussen.yu.news.modules.course.fragment.CourseFragment;
import fussen.yu.news.modules.subject.fragment.SubjectFragment;
import fussen.yu.news.modules.user.fragment.UserFragment;

public class HomeActivity extends BaseActivity {


    @BindView(R.id.tab_menu_subject)
    RelativeLayout tabMenuSubject;
    @BindView(R.id.tab_menu_model)
    RelativeLayout tabMenuCourse;
    @BindView(R.id.tab_menu_me)
    RelativeLayout tabMenuMe;
    @BindView(R.id.fl_content)
    FrameLayout flContent;
    @BindView(R.id.nav_view)
    NavigationView navView;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @BindView(R.id.tab_menu_subject_tv)
    TextView textMenuSubject;
    @BindView(R.id.tab_menu_model_tv)
    TextView textMenuCourse;
    @BindView(R.id.tab_menu_me_tv)
    TextView textMenuMe;
    private FragmentManager fragmentManager;

    private int currentTabIndex = 0;
    private SubjectFragment subjectFragment;
    private CourseFragment courseFragment;
    private UserFragment userFragment;

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

        iniFragment();
    }

    private void iniFragment() {
        fragmentManager = getSupportFragmentManager();
        tabMenuSubject.performClick();

    }


    @OnClick({R.id.tab_menu_subject, R.id.tab_menu_model, R.id.tab_menu_me})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tab_menu_subject:
                setSelectItem(0);
                currentTabIndex = 0;
                break;
            case R.id.tab_menu_model:
                setSelectItem(1);
                currentTabIndex = 1;
                break;
            case R.id.tab_menu_me:
                setSelectItem(2);
                currentTabIndex = 2;
                break;
        }
    }


    private void setSelectItem(int index) {

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        switch (index) {
            case 0:
                if (!tabMenuSubject.isSelected()) {
                    setMenuState();
                    hideOtherFragment(transaction);
                    tabMenuSubject.setSelected(true);
                    textMenuSubject.setTextColor(getResources().getColor(R.color.app_color));
                    if (subjectFragment == null) {
                        subjectFragment = new SubjectFragment();
                        transaction.add(R.id.fl_content, subjectFragment);
                    } else {
                        transaction.show(subjectFragment);
                    }
                }
                break;
            case 1:
                if (!tabMenuCourse.isSelected()) {
                    setMenuState();
                    hideOtherFragment(transaction);
                    tabMenuCourse.setSelected(true);
                    textMenuCourse.setTextColor(getResources().getColor(R.color.app_color));
                    if (courseFragment == null) {
                        courseFragment = new CourseFragment();
                        transaction.add(R.id.fl_content, courseFragment);
                    } else {
                        transaction.show(courseFragment);
                    }
                }
                break;
            case 2:
                if (!tabMenuMe.isSelected()) {
                    setMenuState();
                    hideOtherFragment(transaction);
                    tabMenuMe.setSelected(true);
                    textMenuMe.setTextColor(getResources().getColor(R.color.app_color));
                    if (userFragment == null) {
                        userFragment = new UserFragment();
                        transaction.add(R.id.fl_content, userFragment);
                    } else {
                        transaction.show(userFragment);
                    }
                }

                break;
        }
        transaction.commit();
    }


    private void setMenuState() {

        textMenuCourse.setTextColor(getResources().getColor(R.color.black));
        textMenuMe.setTextColor(getResources().getColor(R.color.black));
        textMenuSubject.setTextColor(getResources().getColor(R.color.black));
        tabMenuSubject.setSelected(false);
        tabMenuCourse.setSelected(false);
        tabMenuMe.setSelected(false);
    }

    private void hideOtherFragment(FragmentTransaction transaction) {

        if (subjectFragment != null) transaction.hide(subjectFragment);

        if (courseFragment != null) transaction.hide(courseFragment);

        if (userFragment != null) transaction.hide(userFragment);
    }
}
