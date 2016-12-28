package fussen.yu.news.utils;

import android.widget.Toast;

import fussen.yu.news.App;

public class ToastUtil {
    private static Toast toast;

    /**
     * 可以连续弹吐司，不用等上个吐司消失
     *
     * @param text
     */
    public static void showToast(String text) {
        if (toast == null) {
            toast = Toast.makeText(App.getContext(),text, Toast.LENGTH_SHORT);
        }
        toast.setText(text);
        toast.show();
    }
}
