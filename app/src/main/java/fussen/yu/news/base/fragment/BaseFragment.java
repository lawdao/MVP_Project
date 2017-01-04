package fussen.yu.news.base.fragment;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import fussen.yu.news.base.presenter.PresenterLife;
import example.fussen.baselibrary.utils.AppUtil;
import fussen.yu.news.App;
import fussen.yu.news.inject.component.DaggerFragmentComponent;
import fussen.yu.news.inject.component.FragmentComponent;
import fussen.yu.news.inject.module.FragmentModule;
import rx.Subscription;

/**
 * Created by Fussen on 2016/11/23.
 */

public abstract class BaseFragment<P extends PresenterLife> extends Fragment {

    private Unbinder mUnbinder;

    protected P mPresenter;
    protected Subscription mSubscription;
    protected FragmentComponent mFragmentComponent;
    protected Bundle mBundle;
    protected ProgressBar progressBar;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //获取bundle,并保存起来
        if (savedInstanceState != null) {
            mBundle = savedInstanceState.getBundle("bundle");
        } else {
            mBundle = getArguments() == null ? new Bundle() : getArguments();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mBundle != null) {
            outState.putBundle("bundle", mBundle);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(getContentView(), container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        initComponent();

        super.onViewCreated(view, savedInstanceState);

        mUnbinder = ButterKnife.bind(this, view);

        createProgressbar();

        initView(view);


        if (mPresenter != null) {
            mPresenter.onCreate();
        }
    }

    private void createProgressbar() {
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        lp.gravity = Gravity.CENTER;
        progressBar = new ProgressBar(getActivity());
        progressBar.setLayoutParams(lp);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();

        if (mPresenter != null) {
            mPresenter.onDestroy();
            mPresenter = null;
        }
        AppUtil.cancelSubscription(mSubscription);
    }

    private void initComponent() {

        mFragmentComponent = DaggerFragmentComponent.builder()
                .applicationComponent(App.getmApplicationComponent())
                .fragmentModule(new FragmentModule(this))
                .build();

        initInject();

    }

    @LayoutRes
    protected abstract int getContentView();

    protected abstract void initView(View view);

    //初始化注解
    protected abstract void initInject();


}
