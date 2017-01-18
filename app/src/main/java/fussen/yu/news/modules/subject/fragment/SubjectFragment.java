package fussen.yu.news.modules.subject.fragment;

import android.graphics.RectF;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import example.fussen.baselibrary.utils.DateUtils;
import example.fussen.baselibrary.widget.weekview.MonthLoader;
import example.fussen.baselibrary.widget.weekview.WeekView;
import example.fussen.baselibrary.widget.weekview.WeekViewEvent;
import fussen.yu.news.R;
import fussen.yu.news.base.fragment.BaseFragment;
import fussen.yu.news.modules.subject.bean.WeekEvent;
import fussen.yu.news.modules.subject.presenter.impl.SubjectPresenterImpl;
import fussen.yu.news.modules.subject.view.SubjectView;
import fussen.yu.news.utils.ToastUtil;

/**
 * Created by Fussen on 2016/12/16.
 */

public class SubjectFragment extends BaseFragment implements WeekView.EventClickListener, MonthLoader.MonthChangeListener, WeekView.EmptyViewClickListener, SubjectView {

    @BindView(R.id.weekView)
    WeekView weekView;

    @BindView(R.id.view_container)
    FrameLayout viewContainer;

    @BindView(R.id.contentView)
    LinearLayout contentView;

    @Inject
    SubjectPresenterImpl mSubjectPresenter;

    @Override
    protected int getContentView() {
        return R.layout.frag_subject;
    }

    @Override
    protected void initView(View view) {

        mPresenter = mSubjectPresenter;

        mPresenter.onBindView(this);

        weekView.setOnEventClickListener(this);
        weekView.setMonthChangeListener(this);
        weekView.setEmptyViewClickListener(this);

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
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
    }


    @Override
    public void onEventClick(WeekViewEvent event, RectF eventRect) {

    }

    @Override
    public List<? extends WeekViewEvent> onMonthChange(int newYear, int newMonth) {
        return null;
    }

    @Override
    public void onEmptyViewClicked(Calendar time) {
    }

    @Override
    public void refreshView(WeekEvent data) {
        if (data != null) {
            ToastUtil.showToast(data.workTime.startime);
        } else {
            ToastUtil.showToast("失败啦");
        }
    }

    @Override
    public void loadData(boolean pullToRefresh) {
        Map<String, String> params = new HashMap<>();
        params.put("startDate", calculateStartDate());
        params.put("endDate", calculateEndDate());
        mSubjectPresenter.getSubject(params, pullToRefresh);
    }


    /**
     * 返回今天前15天的日期
     *
     * @return
     */
    public String calculateStartDate() {

        //2016-08-26
        Calendar calendar = Calendar.getInstance();

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DATE);

        String nowDay = year + "-" + month + "-" + day;

        return DateUtils.getNextDay(nowDay, String.valueOf(-15));

    }


    public String calculateEndDate() {
        //2016-08-26
        Calendar calendar = Calendar.getInstance();

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DATE);


        String date = year + "-" + month + "-" + day;

        return DateUtils.getNextDay(date, String.valueOf(15));
    }
}
