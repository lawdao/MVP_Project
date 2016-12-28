package fussen.yu.news.utils;

import android.content.Context;
import android.content.SharedPreferences;


public class PreferUtils {
    private SharedPreferences mSharedPre;
    public static PreferUtils mInstance;

    private static final String KEY_COOKIE = "key_cookie";

    private static final String KEY_APP_IS_LOGIN = "key_app_is_login";

    private static final String KEY_USER_NAME = "key_user_name";

    private static final String KEY_USER_UID = "key_user_uid";

    private static final String KEY_USER_ACCOUNT = "key_login_account";

    private static final String KEY_USER_PASSWORD = "key_login_password";

    private static final String KEY_USER_HEAD_URL = "key_user_head_url";

    private static final String KEY_USER_GENDER = "key_user_gender";

    private static final String KEY_USER_BIRTHDAY = "key_user_birthday";

    private static final String KEY_USER_PROVINCE = "key_user_province";//省

    private static final String KEY_USER_CITY = "key_user_city";//城市

    private static final String KEY_USER_MILEAGE = "key_user_mileage";//里程

    private static final String KEY_USER_INTRODUCTION = "key_user_introduction";//简介

    private static final String KEY_USER_COLLECT_NUM = "key_user_collect_num";

    private static final String KEY_REMEMBER_PASSWORD = "key_remember_password";

    private static final String KEY_IS_REGISTER_PROCESS = "key_is_register_process";

    private static final String KEY_IS_FORGOT_PASSWORD_PROCESS = "key_is_forgot_password_process";

    private static final String KEY_REGISTER_PHONE_NUMBER = "key_register_phone_number";

    private static final String KEY_REGISTER_PASSWORD = "key_register_password";

    private static final String APP_USER_LOGINTYPE = "AppLoginType";

    private static final String APP_USER_OPENID = "openid";
    private static final String USER_MOBILE = "user_mobile";
    //SharedPreferences名称
    public static final String SHARED_NAME = "coach_config";

    public PreferUtils() {
        mSharedPre = UiUtils.getContext().getSharedPreferences(SHARED_NAME, Context.MODE_PRIVATE);
    }


    public void clearAll() {
        mSharedPre.getAll().clear();
    }


    public static PreferUtils getInstance() {
        if (mInstance == null) {
            mInstance = new PreferUtils();
        }
        return mInstance;
    }


    public String getUserMobile() {
        return mSharedPre.getString(USER_MOBILE, "");
    }

    public void setUserMobile(String mobile) {
        mSharedPre.edit().putString(USER_MOBILE, mobile).commit();
    }

    public String getAppLoginType() {
        return mSharedPre.getString(APP_USER_LOGINTYPE, "1");
    }

    public void setAppLoginType(String AppLoginType) {
        mSharedPre.edit().putString(APP_USER_LOGINTYPE, AppLoginType).commit();
    }

    public String getUserOpenId() {
        return mSharedPre.getString(APP_USER_OPENID, "1");
    }

    public void setUserOpenId(String UserOpenId) {
        mSharedPre.edit().putString(APP_USER_OPENID, UserOpenId).commit();
    }

    public void setCookie(String cookie) {
        mSharedPre.edit().putString(KEY_COOKIE, cookie).commit();
    }

    public String getCookie() {
        return mSharedPre.getString(KEY_COOKIE, "");
    }

    public void setAppIsLogin(boolean isLogin) {
        mSharedPre.edit().putBoolean(KEY_APP_IS_LOGIN, isLogin).commit();
    }

    public boolean getAppIsLogin() {
        return mSharedPre.getBoolean(KEY_APP_IS_LOGIN, false);
    }

    public void setUserUid(String uid) {
        mSharedPre.edit().putString(KEY_USER_UID, uid).commit();
    }

    public String getUserUid() {
        return mSharedPre.getString(KEY_USER_UID, "");
    }

    public void setUserName(String name) {
        mSharedPre.edit().putString(KEY_USER_NAME, name).commit();
    }

    public String getUserName() {
        return mSharedPre.getString(KEY_USER_NAME, "");
    }

    public void setUserPassword(String password) {
        mSharedPre.edit().putString(KEY_USER_PASSWORD, password).commit();
    }

    public String getUserPassword() {
        return mSharedPre.getString(KEY_USER_PASSWORD, "");
    }

    public void setUserAccount(String account) {
        mSharedPre.edit().putString(KEY_USER_ACCOUNT, account).commit();
    }

    public String getUserAccount() {
        return mSharedPre.getString(KEY_USER_ACCOUNT, "");
    }

    public void setUserHeadUrl(String headUrl) {
        mSharedPre.edit().putString(KEY_USER_HEAD_URL, headUrl).commit();
    }

    public String getUserHeadUrl() {
        return mSharedPre.getString(KEY_USER_HEAD_URL, "");
    }

    public void setUserGender(String gender) {
        mSharedPre.edit().putString(KEY_USER_GENDER, gender).commit();
    }

    public String getUserGender() {
        return mSharedPre.getString(KEY_USER_GENDER, "");
    }

    public void setUserBirthday(String birthday) {
        mSharedPre.edit().putString(KEY_USER_BIRTHDAY, birthday).commit();
    }

    public String getUserBirthday() {
        return mSharedPre.getString(KEY_USER_BIRTHDAY, "");
    }

    public String getUserProvince() {
        return mSharedPre.getString(KEY_USER_PROVINCE, "");
    }

    public void setUserProvince(String province) {
        mSharedPre.edit().putString(KEY_USER_PROVINCE, province).commit();
    }

    public String getUserCity() {
        return mSharedPre.getString(KEY_USER_CITY, "");
    }

    public void setUserCity(String address) {
        mSharedPre.edit().putString(KEY_USER_CITY, address).commit();
    }

    public String getUserMileage() {
        return mSharedPre.getString(KEY_USER_MILEAGE, "0");
    }

    public void setUserMileage(String mileage) {
        mSharedPre.edit().putString(KEY_USER_MILEAGE, mileage).commit();
    }

    public String getUserIntroduction() {
        return mSharedPre.getString(KEY_USER_INTRODUCTION, "");
    }

    public void setUserIntroduction(String introduction) {
        mSharedPre.edit().putString(KEY_USER_INTRODUCTION, introduction).commit();
    }

    //保存收藏的模板数量
    public void setUserCollectNum(String collectNum) {
        mSharedPre.edit().putString(KEY_USER_COLLECT_NUM, collectNum).commit();
    }

    public String getUserCollectNum() {
        return mSharedPre.getString(KEY_USER_COLLECT_NUM, "0");
    }

    public void setRememberPassword(boolean isRemember) {
        mSharedPre.edit().putBoolean(KEY_REMEMBER_PASSWORD, isRemember).commit();
    }

    public boolean getIsRememberPassword() {
        return mSharedPre.getBoolean(KEY_REMEMBER_PASSWORD, false);
    }

    public boolean isRegisterProcess() {
        return mSharedPre.getBoolean(KEY_IS_REGISTER_PROCESS, false);
    }

    public void setRegisterProcess() {
        mSharedPre.edit().putBoolean(KEY_IS_FORGOT_PASSWORD_PROCESS, false).commit();
        mSharedPre.edit().putBoolean(KEY_IS_REGISTER_PROCESS, true).commit();
    }

    public boolean isForgotPasswordProcess() {
        return mSharedPre.getBoolean(KEY_IS_FORGOT_PASSWORD_PROCESS, false);
    }

    public void setForgotPasswordProcess() {
        mSharedPre.edit().putBoolean(KEY_IS_REGISTER_PROCESS, false).commit();
        mSharedPre.edit().putBoolean(KEY_IS_FORGOT_PASSWORD_PROCESS, true).commit();
    }

    public void setRegisterPhoneNumber(String number) {
        mSharedPre.edit().putString(KEY_REGISTER_PHONE_NUMBER, number).commit();
    }

    public String getRegisterPhoneNumber() {
        return mSharedPre.getString(KEY_REGISTER_PHONE_NUMBER, "");
    }

    public void setRegisterPassword(String password) {
        mSharedPre.edit().putString(KEY_REGISTER_PASSWORD, password).commit();
    }

    public String getRegisterPassword() {
        return mSharedPre.getString(KEY_REGISTER_PASSWORD, "");
    }


}
