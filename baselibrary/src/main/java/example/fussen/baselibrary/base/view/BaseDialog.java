package example.fussen.baselibrary.base.view;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;

import example.fussen.baselibrary.R;


public class BaseDialog extends Dialog {
    private Handler _handler;

    public BaseDialog(Context context) {
        super(context, R.style.customDialog);
        _handler = new Handler();
    }

    public BaseDialog(Context context, int theme) {
        super(context, theme);
        getWindow().setBackgroundDrawable(new BitmapDrawable((Resources) null));
        _handler = new Handler();
    }

    public void post(final Runnable r) {
        if (_handler != null) {
            _handler.post(new Runnable() {

                @Override
                public void run() {
                    r.run();
                }
            });
        }
    }

    public void postDelayed(final Runnable r, long delayMillis) {
        if (_handler != null) {
            _handler.postDelayed(new Runnable() {

                @Override
                public void run() {
                    r.run();
                }
            }, delayMillis);
        }
    }

    @Override
    public void dismiss() {
        super.dismiss();
        _handler = null;
    }

}
