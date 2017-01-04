package fussen.yu.news.modules.login.utils;

import android.text.TextUtils;

import example.fussen.baselibrary.utils.secret.MCrypt;
import fussen.yu.news.utils.ToastUtil;

/**
 * Created by Fussen on 2016/12/15.
 */

public class LoginUtil {


    public static boolean checkAcount(String account, String password) {
        if (TextUtils.isEmpty(account)) {
            ToastUtil.showToast("请填写手机号！");
            return false;
        } else if (account.length() != 11) {
            ToastUtil.showToast("电话号码格式不正确!");
            return false;
        } else if (TextUtils.isEmpty(password)) {
            ToastUtil.showToast("请输入密码");
            return false;
        } else if (password.length() < 6 || password.length() > 20) {
            ToastUtil.showToast("密码格式错误，密码长度最少6位最多20位");
            return false;
        } else {
            return true;
        }
    }

    /**
     * 加密字符串
     */
    public static String encryptString(String account) {
        MCrypt mCrypt = new MCrypt();
        try {
            return mCrypt.bytesToHex(mCrypt.encrypt(account));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 解密
     *
     * @param encryptString
     * @return
     */
    public static String decryptString(String encryptString) {
        MCrypt mCrypt = new MCrypt();
        try {
            return new String(mCrypt.decrypt(encryptString));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
