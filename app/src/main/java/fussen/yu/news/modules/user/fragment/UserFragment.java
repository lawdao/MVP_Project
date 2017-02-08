package fussen.yu.news.modules.user.fragment;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.io.File;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import example.fussen.baselibrary.utils.BitmapUtils;
import example.fussen.baselibrary.utils.LogUtil;
import example.fussen.baselibrary.utils.image.ImageUtils;
import example.fussen.baselibrary.utils.image.OnCompressListener;
import example.fussen.baselibrary.widget.CircleImageView;
import example.fussen.baselibrary.widget.dialog.GlobalDialog;
import fussen.yu.news.R;
import fussen.yu.news.User;
import fussen.yu.news.base.fragment.BaseFragment;
import fussen.yu.news.modules.user.bean.UpLoad;
import fussen.yu.news.modules.user.presenter.impl.UserPresenterImpl;
import fussen.yu.news.modules.user.view.UserView;
import fussen.yu.news.utils.PreferUtils;
import fussen.yu.news.utils.ToastUtil;
import fussen.yu.news.utils.UiUtils;
import fussen.yu.news.utils.db.DbUtils;
import fussen.yu.news.utils.listener.PermissionsResultListener;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Fussen on 2016/12/16.
 */

public class UserFragment extends BaseFragment implements UserView {

    @BindView(R.id.user_head)
    CircleImageView userHead;
    @BindView(R.id.tv_user_name)
    TextView tvUserName;
    @BindView(R.id.view_editor)
    RelativeLayout viewEditor;
    @BindView(R.id.view_photo)
    RelativeLayout viewPhoto;

    @Inject
    UserPresenterImpl mUserPresnter;

    private static final int TAKE_PHOTO_ALBUM = 521;
    private String filePath;
    private String url;
    private ProgressDialog progressDialog;

    @Override
    protected int getContentView() {
        return R.layout.frag_user;
    }

    @Override
    protected void initView(View view) {

        mPresenter = mUserPresnter;

        mPresenter.onBindView(this);

        User user = DbUtils.getUserById(PreferUtils.getInstance().getUserUid());

        url = user.getAvatarUrl();
        Glide.with(this)
                .load(url)
                .dontAnimate()
                .into(userHead);

        progressDialog = new ProgressDialog(getActivity());

        hideProgress();
    }

    @Override
    protected void initInject() {
        mFragmentComponent.inject(this);
    }

    @Override
    protected void showErrorMessage(String errorMsg, boolean pullToRefresh) {

    }


    @OnClick({R.id.view_editor, R.id.view_photo, R.id.user_head})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.view_editor:
                uploadPhoto();
                break;
            case R.id.view_photo:

                download();
                break;
            case R.id.user_head:

                showSelectDialog();
                break;
        }
    }


    private void showSelectDialog() {
        GlobalDialog.showListDialog(mFragmentComponent.getActivity(), "选择照片", new String[]{"从相册中选取", "拍照"}, true, new GlobalDialog.DialogItemClickListener() {
            @Override
            public void confirm(String result) {
                if (result.equalsIgnoreCase("从相册中选取")) {

                    openPhonePhoto();
                } else {

                }
            }
        });
    }

    private void openPhonePhoto() {

        requestPermissions(getResources().getString(R.string.permission_desc), new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE}
                , PER_REQUEST_CODE, new PermissionsResultListener() {
                    @Override
                    public void onPermissionGranted() {
                        Intent intent = new Intent();
                        intent.setType("image/*");
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        intent.addCategory(Intent.CATEGORY_OPENABLE);
                        startActivityForResult(intent, TAKE_PHOTO_ALBUM);
                    }

                    @Override
                    public void onPermissionDenied() {
                        ToastUtil.showToast("拒绝申请权限");
                    }
                });

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == RESULT_OK) {
            if (requestCode == TAKE_PHOTO_ALBUM) {
                final Uri selectedUri = data.getData();
                if (selectedUri != null) {

                    String path = selectedUri.getPath();

                    try {
                        File file = BitmapUtils.saveUriToFile(UiUtils.getContext(), selectedUri);
                        LogUtil.fussenLog().d("1008611" + "===selectedUri.getPath()=====" + path);

                        ImageUtils.get(UiUtils.getContext())
                                .load(file)
                                .setCompressListener(new OnCompressListener() {
                                    @Override
                                    public void onStart() {

                                    }

                                    @Override
                                    public void onSuccess(File file) {
                                        filePath = file.getAbsolutePath();
                                        LogUtil.fussenLog().d("1008611" + "=====压缩后路径======" + file.getAbsolutePath());
                                        ToastUtil.showToast("压缩成功");
                                    }

                                    @Override
                                    public void onError(Throwable e) {

                                    }
                                }).launch();


                        Glide.with(this)
                                .load(selectedUri)
                                .into(userHead);
                    } catch (Exception e) {
                        e.printStackTrace();

                    }


                } else {
                    ToastUtil.showToast("无法选择该图片");
                }
            }
        } else {
            ToastUtil.showToast("没拿到照片");
        }
    }


    private void download() {

        progressDialog.setMessage("下载中...");
        progressDialog.show();
        mUserPresnter.downLoadImage("http://dev.bodyplus.cc:8088//uploads//video//stuffVideoDemo.mp4");

    }

    private static final int PER_REQUEST_CODE = 1;

    private void uploadPhoto() {

        if (TextUtils.isEmpty(filePath)) {
            ToastUtil.showToast("请先点击头像选择图片");
            return;
        }
        progressDialog.setMessage("上传中...");
        progressDialog.show();
        mUserPresnter.upLoadImage(new File(filePath));
    }


    @Override
    public void hideProgress() {
        super.hideProgress();
        progressDialog.dismiss();
    }


    @Override
    public void loadData(boolean pullToRefresh) {

    }

    @Override
    public void upLoadImageSucce(UpLoad upLoad) {
        ToastUtil.showToast("上传成功");
    }

    @Override
    public void downLoadImageSucce(String path) {
        ToastUtil.showToast("视频已保存在" + path + "目录下");
    }

    @Override
    public void onProgress(long downSize, long fileSize) {

        progressDialog.setMessage("已下载：" + downSize);

    }
}
