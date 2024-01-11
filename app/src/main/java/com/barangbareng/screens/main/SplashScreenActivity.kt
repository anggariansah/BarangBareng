package com.barangbareng.screens.main

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import com.barangbareng.R
import com.barangbareng.base.BaseActivity
import com.barangbareng.databinding.ActivitySplashScreenBinding
import com.barangbareng.screens.MainActivity
import com.barangbareng.utils.Constants

class SplashScreenActivity : BaseActivity<ActivitySplashScreenBinding>() {
    override fun getLayoutId(): Int = R.layout.activity_splash_screen

    override fun ActivitySplashScreenBinding.initializeView(savedInstanceState: Bundle?) {
        Handler().postDelayed({
            val intent = Intent(this@SplashScreenActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, Constants.SPLASH_SCREEN_DELAY_DURATION)
    }
}