package com.yzz.great.base;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.yzz.great.view.LoadingDialog;

/**
 * 所有activity基类，必须继承
 */
@SuppressLint("Registered")
public class BaseActivity extends AppCompatActivity {

    private boolean mIsDestroy;

    private LoadingDialog loadingDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mIsDestroy = false;
    }

    @Override
    protected void onDestroy() {
        mIsDestroy = true;
        super.onDestroy();
    }

    /**
     * 返回
     */
    public void onBackClick() {
        onBackPressed();
    }

    /**
     * 显示加载框
     */
    public void showLoadingDialog() {
        showLoadingDialog(false);
    }

    /**
     * 显示加载框
     *
     * @param isCancelable 是否可以取消
     */
    public void showLoadingDialog(boolean isCancelable) {
        showLoadingDialog(isCancelable, "");
    }

    /**
     * 显示加载框
     *
     * @param isCancelable 是否可以取消
     * @param tipMsg       加载时的提示信息
     */
    public void showLoadingDialog(boolean isCancelable, String tipMsg) {
        showLoadingDialog(isCancelable, tipMsg, 0, null);
    }

    /**
     * 显示加载框
     *
     * @param isCancelable      是否可以取消
     * @param tipMsg            加载时的提示信息
     * @param delayMillis       延迟时间（默认多少时间后异常）
     * @param onTimeOutListener 超时监听
     */
    public void showLoadingDialog(boolean isCancelable, String tipMsg, long delayMillis, LoadingDialog.OnTimeOutListener onTimeOutListener) {
        if (mIsDestroy) {
            return;
        }
        if (loadingDialog == null) {
            loadingDialog = new LoadingDialog(this);
        }
        loadingDialog.setCanceledOnTouchOutside(isCancelable);
        loadingDialog.setTipMsg(tipMsg);
        loadingDialog.show(delayMillis, onTimeOutListener);
    }

    /**
     * 关闭加载框
     */
    public void dismissLoadingDialog() {
        if (loadingDialog != null && loadingDialog.isShowing()) {
            loadingDialog.dismiss();
        }
    }

}
