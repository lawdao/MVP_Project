package example.fussen.baselibrary.widget.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import example.fussen.baselibrary.R;


public class GlobalDialog {
    public final static int SELECT_DIALOG = 1;

    public final static int RADIO_DIALOG = 2;

    private final static String NO_TOAST = "no_toast";

    /**
     * 创建一个内容多选对话框
     *
     * @param context
     * @param title                   标题
     * @param items                   数组
     * @param cancelable              按返回键不能退出
     * @param dialogItemClickListener 监听点击的内容结果
     * @return
     */
    public static Dialog showListDialog(Context context, String title, String[] items, boolean cancelable,
                                        final DialogItemClickListener dialogItemClickListener) {
        return ShowDialog(context, title, items, cancelable, dialogItemClickListener);
    }

    /**
     * 创建一个单选对话框
     *
     * @param context
     * @param title               提示标题
     * @param cancelable          按返回键不能退出
     * @param dialogClickListener 点击监听
     * @return
     */
    public static Dialog showRadioDialog(Context context, String title, boolean cancelable,
                                         final DialogClickListener dialogClickListener) {
        return ShowDialog(context, title, NO_TOAST, cancelable, dialogClickListener, RADIO_DIALOG);
    }


    /**
     * 创建一个单选对话框
     *
     * @param context
     * @param title               提示标题
     * @param toast               提示消息
     * @param cancelable          按返回键不能退出
     * @param dialogClickListener 点击监听
     * @return
     */
    public static Dialog showRadioDialog(Context context, String title, String toast, boolean cancelable,
                                         final DialogClickListener dialogClickListener) {
        return ShowDialog(context, title, toast, cancelable, dialogClickListener, RADIO_DIALOG);
    }


    /**
     * 创建一个选择对话框
     *
     * @param context
     * @param title               提示标题
     * @param cancelable          按返回键不能退出
     * @param dialogClickListener 点击监听
     * @return
     */
    public static Dialog showSelectDialog(Context context, String title, boolean cancelable,
                                          final DialogClickListener dialogClickListener) {
        return ShowDialog(context, title, NO_TOAST, cancelable, dialogClickListener, SELECT_DIALOG);
    }

    /**
     * 创建一个选择对话框
     *
     * @param context
     * @param title               提示标题
     * @param toast               提示消息
     * @param cancelable          按返回键不能退出
     * @param dialogClickListener 点击监听
     * @return
     */
    public static Dialog showSelectDialog(Context context, String title, String toast, boolean cancelable,
                                          final DialogClickListener dialogClickListener) {
        return ShowDialog(context, title, toast, cancelable, dialogClickListener, SELECT_DIALOG);
    }

    private static Dialog ShowDialog(Context context, String title, String toast, boolean cancelable,
                                     final DialogClickListener dialogClickListener, int DialogType) {
        final android.app.Dialog dialog = new android.app.Dialog(context, R.style.globalDialog);
        dialog.setCancelable(cancelable);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog, null);
        dialog.setContentView(view);
        ((TextView) view.findViewById(R.id.point)).setText(title);
        if (!toast.equalsIgnoreCase(NO_TOAST)) {
            ((TextView) view.findViewById(R.id.toast)).setVisibility(View.VISIBLE);
//            ((TextView) view.findViewById(R.id.tv_line1)).setVisibility(View.VISIBLE);
            ((TextView) view.findViewById(R.id.toast)).setText(toast);
        }
        if (DialogType == RADIO_DIALOG) {
        } else {
            view.findViewById(R.id.ok).setVisibility(View.GONE);
            view.findViewById(R.id.divider).setVisibility(View.VISIBLE);
        }
        view.findViewById(R.id.cancel).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                dialog.dismiss();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        dialogClickListener.cancel();
                    }
                }, 200);
            }
        });
        view.findViewById(R.id.confirm).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                dialog.dismiss();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        dialogClickListener.confirm();
                    }
                }, 200);
            }
        });
        view.findViewById(R.id.ok).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                dialog.dismiss();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        dialogClickListener.confirm();
                    }
                }, 200);
            }
        });
        Window mWindow = dialog.getWindow();
        WindowManager.LayoutParams lp = mWindow.getAttributes();
        if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {// 横屏
            lp.width = getScreenHeight(context) / 10 * 8;
        } else {
            lp.width = getScreenWidth(context) / 10 * 8;
        }
        mWindow.setAttributes(lp);
        dialog.show();

        return dialog;
    }

    public static Dialog ShowDialog(Context context, String title, String[] items, boolean cancelable,
                                    final DialogItemClickListener dialogClickListener) {
        final android.app.Dialog dialog = new android.app.Dialog(context, R.style.globalDialog);
        dialog.setCancelable(cancelable);
        dialog.setCanceledOnTouchOutside(true);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_radio, null);
        dialog.setContentView(view);
        TextView titleText = (TextView) view.findViewById(R.id.title);
        titleText.setText(title);
        titleText.setTextSize(13);
        titleText.setClickable(false);
        // 根据items动态创建
        LinearLayout parent = (LinearLayout) view.findViewById(R.id.dialogLayout);
        parent.removeAllViews();
        int length = items.length;
        for (int i = 0; i < items.length; i++) {
            LayoutParams params1 = new LayoutParams(-1, -2);
            params1.rightMargin = 1;
            final TextView tv = new TextView(context);
            tv.setLayoutParams(params1);
            tv.setTextSize(16);
            tv.setText(items[i]);
            tv.setTextColor(context.getResources().getColor(R.color.dialogBlue));
            int pad = context.getResources().getDimensionPixelSize(R.dimen.indicator_right_padding);
            tv.setPadding(pad, pad, pad, pad);
            tv.setSingleLine(true);
            tv.setGravity(Gravity.CENTER);
            if (i != length - 1)
                tv.setBackgroundResource(R.drawable.menudialog_center_selector);
            else
                tv.setBackgroundResource(R.drawable.menudialog_bottom2_selector);

            tv.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    dialog.dismiss();
                    dialogClickListener.confirm(tv.getText().toString());
                }
            });
            parent.addView(tv);
            if (i != length - 1) {
                TextView divider = new TextView(context);
                LayoutParams params = new LayoutParams(-1, (int) 1);
                divider.setLayoutParams(params);
                divider.setBackgroundResource(R.color.divider_color_c8);
                parent.addView(divider);
            }
        }
        view.findViewById(R.id.ok).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                dialog.dismiss();
            }
        });
        Window mWindow = dialog.getWindow();
        WindowManager.LayoutParams lp = mWindow.getAttributes();
        lp.width = getScreenWidth(context);
        mWindow.setGravity(Gravity.BOTTOM);
        // 添加动画
        mWindow.setWindowAnimations(R.style.timepopwindow_anim_style);
        mWindow.setAttributes(lp);
        dialog.show();
        return dialog;
    }

    public static Dialog ShowDialoglist(Context context, String title, String[] items, boolean cancelable,
                                        final DialogClickListener dialogClickListener) {
        final android.app.Dialog dialog = new android.app.Dialog(context, R.style.globalDialog);
        dialog.setCancelable(cancelable);
        dialog.setCanceledOnTouchOutside(true);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_list, null);
        TextView cancel = (TextView) view.findViewById(R.id.cancel);
        TextView confirm = (TextView) view.findViewById(R.id.confirm);
        cancel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                dialog.dismiss();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        dialogClickListener.cancel();
                    }
                }, 200);
            }
        });
        confirm.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                dialog.dismiss();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        dialogClickListener.confirm();
                    }
                }, 200);
            }
        });

        dialog.setContentView(view);
        ((TextView) view.findViewById(R.id.point)).setText(title);
        // 根据items动态创建
        LinearLayout parent = (LinearLayout) view.findViewById(R.id.dialogLayout);
        parent.removeAllViews();
        int length = items.length;
        for (int i = 0; i < items.length; i++) {
            LayoutParams params1 = new LayoutParams(-1, -2);
            params1.rightMargin = 1;
            params1.leftMargin = 10;
            final TextView tv = new TextView(context);
            tv.setLayoutParams(params1);
            tv.setTextSize(14);
            tv.setText(items[i]);
            tv.setTextColor(context.getResources().getColor(R.color.material_text_color_47));
            int pad = context.getResources().getDimensionPixelSize(R.dimen.indicator_right_padding);
            tv.setPadding(50, pad, pad, pad);
            tv.setSingleLine(true);
            if (i != length - 1)
                tv.setBackgroundResource(R.drawable.menudialog_center_selector);
            else
                tv.setBackgroundResource(R.drawable.menudialog_bottom2_selector);

            parent.addView(tv);
//            if (i != length - 1) {
//                TextView divider = new TextView(context);
//                LayoutParams params = new LayoutParams(-1, (int) 1);
//                divider.setLayoutParams(params);
//                divider.setBackgroundResource(R.color.divider_color_c8);
//                parent.addView(divider);
//            }
        }

        Window mWindow = dialog.getWindow();
        WindowManager.LayoutParams lp = mWindow.getAttributes();
        if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {// 横屏
            lp.width = getScreenHeight(context) / 10 * 8;
        } else {
            lp.width = getScreenWidth(context) / 10 * 8;
        }
        mWindow.setAttributes(lp);
        dialog.show();
        return dialog;
    }

    /**
     * 获取屏幕分辨率宽
     */
    public static int getScreenWidth(Context context) {
        DisplayMetrics dm = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm.widthPixels;
    }

    /**
     * 获取屏幕分辨率高
     */
    public static int getScreenHeight(Context context) {
        DisplayMetrics dm = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm.heightPixels;
    }

    public interface DialogClickListener {
        public abstract void confirm();

        public abstract void cancel();
    }

    public interface DialogItemClickListener {
        public abstract void confirm(String result);
    }
}