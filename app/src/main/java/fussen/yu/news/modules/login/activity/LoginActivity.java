package fussen.yu.news.modules.login.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import example.fussen.baselibrary.utils.LogUtil;
import example.fussen.baselibrary.widget.DeleteEditText;
import fussen.yu.news.App;
import fussen.yu.news.R;
import fussen.yu.news.base.activity.BaseActivity;
import fussen.yu.news.modules.login.bean.UserInfo;
import fussen.yu.news.modules.login.presenter.impl.LoginPresenterImpl;
import fussen.yu.news.modules.login.utils.LoginUtil;
import fussen.yu.news.modules.login.view.LoginView;
import fussen.yu.news.utils.ToastUtil;

public class LoginActivity extends BaseActivity implements LoginView {


    @Inject
    LoginPresenterImpl mLoginPresenter;
    @BindView(R.id.edit_login_phone)
    DeleteEditText editLoginPhone;
    @BindView(R.id.edit_login_password)
    DeleteEditText editLoginPassword;
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.tv_register_user)
    TextView tvRegisterUser;
    @BindView(R.id.tv_forgot_password)
    TextView tvForgotPassword;

    private ProgressDialog progressDialog;

    @Override
    protected int getContentView() {
        return R.layout.activity_login;
    }

    @Override
    protected void initInject() {

        mActivityComponent.inject(this);

    }

    @Override
    protected void initView() {
        isNeedNavigationView = false;

        mPresenter = mLoginPresenter;
        //presenter和View的绑定
        mPresenter.onBindView(this);

        back.setVisibility(View.GONE);
        title.setText("登录");
        title.setTextColor(getResources().getColor(R.color.rgb_color_01));
        titleView.setBackgroundColor(getResources().getColor(R.color.transparent));

        progressDialog = new ProgressDialog(this);

        progressDialog.setMessage("登陆中...");


    }


    @Override
    public void showProgress() {

//        progressDialog.show();

    }

    @Override
    public void hideProgress() {
        progressDialog.dismiss();
    }

    @Override
    public void showErrorMsg(String errorMsg) {
        LogUtil.fussenLog().d("1008611" + "====showErrorMsg========" + errorMsg);
        ToastUtil.showToast(errorMsg);

    }


    @Override
    public void toHomeActivity(UserInfo data) {

        if (data != null) {
            startActivity(new Intent(App.getContext(), HomeActivity.class));
            overridePendingTransition(0, android.R.anim.fade_out);
            finish();
        }
    }


    @OnClick({R.id.btn_login, R.id.tv_register_user, R.id.tv_forgot_password})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                login();
                break;
            case R.id.tv_register_user:
                break;
            case R.id.tv_forgot_password:
                break;
        }
    }


    private void login() {

        String number = editLoginPhone.getText().toString().trim();
        String password = editLoginPassword.getText().toString().trim();

        if (LoginUtil.checkAcount(number, password)) {
            String encryptNum = LoginUtil.encryptString(number);
            String encryptPassword = LoginUtil.encryptString(password);

            Map<String, String> params = new HashMap<>();

            params.put("username", encryptNum);
            params.put("password", encryptPassword);
            mLoginPresenter.login(params);
        }

    }
}
