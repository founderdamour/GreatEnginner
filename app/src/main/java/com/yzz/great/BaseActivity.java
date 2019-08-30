package com.yzz.great;

import android.annotation.SuppressLint;

import androidx.appcompat.app.AppCompatActivity;

/**
 * 所有activity基类，必须继承
 */
@SuppressLint("Registered")
public class BaseActivity extends AppCompatActivity {

    /**
     * 返回
     */
    public void onBackClick() {
        onBackPressed();
    }
}
