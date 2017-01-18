package fussen.yu.news.modules.course.fragment;

import android.view.View;
import android.widget.LinearLayout;

import javax.inject.Inject;

import butterknife.BindView;
import example.fussen.baselibrary.widget.ListViewForScroll;
import fussen.yu.news.R;
import fussen.yu.news.base.fragment.BaseFragment;
import fussen.yu.news.modules.course.adapter.CourseTypeAdapter;
import fussen.yu.news.modules.course.bean.CourseData;
import fussen.yu.news.modules.course.presenter.impl.CoursePresenterImpl;
import fussen.yu.news.modules.course.view.CourseView;

/**
 * Created by Fussen on 2016/12/16.
 */

public class CourseFragment extends BaseFragment implements CourseView {

    @BindView(R.id.listview)
    ListViewForScroll listview;
    @BindView(R.id.contentView)
    LinearLayout contentView;


    @Inject
    CoursePresenterImpl mCoursepresenter;

    @Override
    protected int getContentView() {
        return R.layout.frag_course;
    }

    @Override
    protected void initView(View view) {

        mPresenter = mCoursepresenter;

        mPresenter.onBindView(this);

        loadData(false);
    }

    @Override
    protected void initInject() {

        mFragmentComponent.inject(this);
    }

    @Override
    protected void showErrorMessage(String errorMsg, boolean pullToRefresh) {

    }

    @Override
    public void loadData(boolean pullToRefresh) {
        mCoursepresenter.getAllCourseType(null,pullToRefresh);
    }


    @Override
    public void loadDataSucces(CourseData data) {
        if (data != null) {
            CourseTypeAdapter adapter = new CourseTypeAdapter(data.dataList);
            listview.setAdapter(adapter);
        }
    }
}
