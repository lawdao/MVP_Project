package fussen.yu.news.widget;

import android.content.Context;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import example.fussen.baselibrary.base.view.BaseDialog;
import fussen.yu.news.R;

public class SelectSportStateDialog extends BaseDialog implements View.OnClickListener{
    private TextView continueBtn;
    private TextView endBtn;
    private HandlerDialogSelectSportStateListener mListener;

    public static final String CONTINUE = "continue";
    public static final String END = "end";

    public SelectSportStateDialog(Context context, HandlerDialogSelectSportStateListener listener) {
        super(context);
        setContentView(R.layout.dialog_select_sports_state);
        Window window = getWindow();
        window.setWindowAnimations(R.style.dialog_up_down_animation);

        initView();
        mListener = listener;
    }

    private void initView(){
        continueBtn = (TextView) findViewById(R.id.sport_continue_btn);
        continueBtn.setOnClickListener(this);
        endBtn = (TextView) findViewById(R.id.sport_end_btn);
        endBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v == continueBtn){
            mListener.onSelectSportState(CONTINUE);
            dismiss();
        }else if(v == endBtn){
            mListener.onSelectSportState(END);
            dismiss();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    public interface HandlerDialogSelectSportStateListener{
        void onSelectSportState(String i);
    }
}
