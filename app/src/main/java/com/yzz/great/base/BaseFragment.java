package com.yzz.great.base;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.yzz.great.view.LoadingDialog;

/**
 * 所有fragment的基类
 */
public class BaseFragment extends Fragment {

    private boolean mIsDestroy;

    private LoadingDialog loadingDialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mIsDestroy = false;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mIsDestroy = true;
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
        if (loadingDialog == null && getActivity() != null) {
            loadingDialog = new LoadingDialog(getActivity());
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
