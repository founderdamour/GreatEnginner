package com.yzz.great.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.yzz.great.R;

/**
 * 加载提示框
 */
public class LoadingDialog extends Dialog {

    private long mDelayMillis = 60000;

    private TextView tipMsgTv;

    private boolean isCancelable;

    private OnTimeOutListener onTimeOutListener;

    private void setOnTimeOutListener(OnTimeOutListener onTimeOutListener) {
        this.onTimeOutListener = onTimeOutListener;
    }

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            if (isShowing()) {
                if (onTimeOutListener != null) {
                    onTimeOutListener.timeout();
                } else {
                    dismiss();
                    Toast.makeText(getContext(), "超时，自动关闭提示框", Toast.LENGTH_SHORT).show();
                }
            }
            return true;
        }
    });


    public LoadingDialog(@NonNull Context context) {
        super(context, R.style.LoadingDialogStyle);
        setContentView(R.layout.loading_dialog);
        initView();
    }

    private void initView() {
        tipMsgTv = findViewById(R.id.tipMsgTv);
    }

    public void show(long delayMillis, OnTimeOutListener onTimeOutListener) {
        if (delayMillis > 0 && onTimeOutListener != null) {
            handler.sendEmptyMessageDelayed(0, delayMillis);
            setOnTimeOutListener(onTimeOutListener);
        } else {
            handler.sendEmptyMessageDelayed(0, mDelayMillis);
        }
        super.show();
    }

    @Override
    public void setCanceledOnTouchOutside(boolean isCancelable) {
        super.setCanceledOnTouchOutside(isCancelable);
        this.isCancelable = isCancelable;
    }

    public void setTipMsg(String tipMsg) {
        if (!TextUtils.isEmpty(tipMsg)) {
            tipMsgTv.setVisibility(View.VISIBLE);
            tipMsgTv.setText(tipMsg);
        } else {
            tipMsgTv.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, @NonNull KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (isShowing() && !isCancelable) {
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    public interface OnTimeOutListener {
        void timeout();
    }
}
