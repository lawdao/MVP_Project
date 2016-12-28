package fussen.yu.news.course.fragment;

import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import example.fussen.baselibrary.widget.ListViewForScroll;
import fussen.yu.news.R;
import fussen.yu.news.base.fragment.BaseFragment;
import fussen.yu.news.course.adapter.CourseTypeAdapter;
import fussen.yu.news.course.bean.CourseData;
import fussen.yu.news.course.presenter.impl.CoursePresenterImpl;
import fussen.yu.news.course.view.CourseView;
import fussen.yu.news.utils.ToastUtil;

/**
 * Created by Fussen on 2016/12/16.
 */

public class CourseFragment extends BaseFragment implements CourseView {

    @BindView(R.id.listview)
    ListViewForScroll listview;
    @BindView(R.id.view_null)
    LinearLayout viewNull;
    @BindView(R.id.view_data)
    LinearLayout viewData;
    @BindView(R.id.view_container)
    FrameLayout viewContainer;


    @Inject
    CoursePresenterImpl mCoursepresenter;

    @Override
    protected int getContentView() {
        return R.layout.frag_course;
    }

    @Override
    protected void initView(View view) {
        viewContainer.addView(progressBar);

        mPresenter = mCoursepresenter;

        mPresenter.onBindView(this);

        mCoursepresenter.getAllCourseType(null);
    }

    @Override
    protected void initInject() {

        mFragmentComponent.inject(this);
    }


    @OnClick(R.id.view_null)
    public void onClick() {
    }

    @Override
    public void showProgress() {
        viewData.setVisibility(View.GONE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
        viewData.setVisibility(View.VISIBLE);
    }

    @Override
    public void showErrorMsg(String errorMsg) {
        ToastUtil.showToast(errorMsg);
    }

    @Override
    public void loadData(CourseData data) {
        if (data != null) {
            CourseTypeAdapter adapter = new CourseTypeAdapter(data.dataList);
            listview.setAdapter(adapter);
        }
    }
}
