package fussen.yu.news.base.fragment;

import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;

import example.fussen.baselibrary.mvp.LceAnimator;
import example.fussen.baselibrary.mvp.MvpFragment;
import fussen.yu.news.App;
import fussen.yu.news.R;
import fussen.yu.news.inject.component.DaggerFragmentComponent;
import fussen.yu.news.inject.component.FragmentComponent;
import fussen.yu.news.inject.module.FragmentModule;
import fussen.yu.news.utils.ToastUtil;
import fussen.yu.news.utils.listener.PermissionsResultListener;

/**
 * Created by Fussen on 2016/11/23.
 */

public abstract class BaseFragment extends MvpFragment {

    protected FragmentComponent mFragmentComponent;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void initComponent() {
        mFragmentComponent = DaggerFragmentComponent.builder()
                .applicationComponent(App.getmApplicationComponent())
                .fragmentModule(new FragmentModule(this))
                .build();
        initInject();
    }


    @Override
    public void showProgress(boolean pullToRefresh) {
        if (!pullToRefresh) {
            LceAnimator.showLoading(loadingView, contentView, errorView);
        }
    }


    @Override
    public void hideProgress() {
        LceAnimator.showContent(loadingView, contentView, errorView);
    }


    @Override
    public void showErrorMsg(String errorMsg, boolean pullToRefresh) {
        showErrorMessage(errorMsg, pullToRefresh);

        if (pullToRefresh) {
            ToastUtil.showToast(errorMsg);
        } else {
            errorView.setText(errorMsg);
            animateErrorViewIn();
        }
    }


    //初始化注解
    protected abstract void initInject();

    //页面出错处理
    protected abstract void showErrorMessage(String errorMsg, boolean pullToRefresh);

    protected void animateErrorViewIn() {
        LceAnimator.showErrorView(loadingView, contentView, errorView);
    }



    /** ==================================权限申请=============================================*/

    private PermissionsResultListener mListener;

    private int mRequestCode;


    protected void requestPermissions(String desc, String[] permissions, int requestCode, PermissionsResultListener listener) {
        if (permissions == null || permissions.length == 0) return;
        mRequestCode = requestCode;
        mListener = listener;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkEachSelfPermission(permissions)) {// 检查是否声明了权限
                requestEachPermissions(desc, permissions, requestCode);
            } else {// 已经申请权限
                if (mListener != null) {
                    mListener.onPermissionGranted();
                }
            }
        } else {
            if (mListener != null) {
                mListener.onPermissionGranted();
            }
        }
    }

    private void requestEachPermissions(String desc, String[] permissions, int requestCode) {
        if (shouldShowRequestPermissionRationale(permissions)) {// 需要再次声明
            showRationaleDialog(desc, permissions, requestCode);
        } else {
            requestPermissions(permissions, requestCode);
        }
    }

    private void showRationaleDialog(String desc, final String[] permissions, final int requestCode) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(getString(R.string.tips))
                .setMessage(desc)
                .setPositiveButton(getResources().getString(R.string.confrim), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        requestPermissions(permissions, requestCode);
                    }
                })
                .setNegativeButton(getResources().getString(R.string.cancle), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .setCancelable(false)
                .show();
    }


    /**
     * 再次申请权限时，是否需要声明
     *
     * @param permissions
     * @return
     */
    private boolean shouldShowRequestPermissionRationale(String[] permissions) {
        for (String permission : permissions) {
            if (shouldShowRequestPermissionRationale(permission)) {
                return true;
            }
        }
        return false;
    }


    /**
     * 检察每个权限是否申请
     *
     * @param permissions
     * @return true 需要申请权限,false 已申请权限
     */

    private boolean checkEachSelfPermission(String[] permissions) {
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(getActivity(), permission) != PackageManager.PERMISSION_GRANTED) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == mRequestCode) {
            if (checkEachPermissionsGranted(grantResults)) {
                if (mListener != null) {
                    mListener.onPermissionGranted();
                }
            } else {// 用户拒绝申请权限
                if (mListener != null) {
                    mListener.onPermissionDenied();
                }
            }
        }
    }

    private boolean checkEachPermissionsGranted(int[] grantResults) {
        for (int result : grantResults) {
            if (result != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }
}
