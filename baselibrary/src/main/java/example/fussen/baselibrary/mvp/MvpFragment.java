package example.fussen.baselibrary.mvp;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import example.fussen.baselibrary.R;
import example.fussen.baselibrary.base.presenter.PresenterLife;
import example.fussen.baselibrary.base.view.BaseView;
import example.fussen.baselibrary.utils.AppUtil;
import rx.Subscription;

/**
 * Created by Fussen on 2017/1/9.
 */

public abstract class MvpFragment<CV extends View, P extends PresenterLife, V extends BaseView> extends Fragment implements BaseView {

    private static final String TAG = "[Fragment]";
    private Unbinder mUnbinder;
    protected P mPresenter;
    protected Subscription mSubscription;
    protected Bundle mBundle;

    protected View loadingView;
    protected CV contentView;
    protected TextView errorView;

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

        loadingView = view.findViewById(R.id.loadingView);
        contentView = (CV) view.findViewById(R.id.contentView);
        errorView = (TextView) view.findViewById(R.id.errorView);

        if (errorView != null)
            errorView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onErrorViewClicked();
                }
            });

        mUnbinder = ButterKnife.bind(this, view);

        initView(view);

        if (mPresenter != null) {
            mPresenter.onCreate();
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        Log.e(TAG, "======hidden:=====" + hidden);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();

        loadingView = null;
        contentView = null;
        errorView = null;

        if (mPresenter != null) {
            mPresenter.onDestroy();
            mPresenter = null;
        }
        AppUtil.cancelSubscription(mSubscription);

        Log.e(TAG, "======onDestroyView:=====");
    }


    protected void onErrorViewClicked() {
        loadData(false);
    }

    protected abstract void initComponent();

    @LayoutRes
    protected abstract int getContentView();

    protected abstract void initView(View view);


}
