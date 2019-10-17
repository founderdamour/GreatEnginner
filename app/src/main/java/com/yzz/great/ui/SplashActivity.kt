package com.yzz.great.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import com.yzz.great.R
import com.yzz.great.base.BaseActivity

class SplashActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler().postDelayed({
            startActivity(Intent(this@SplashActivity, MainActivity::class.java))
            finish()
        }, 3000)

    }
}