package com.water.song.template.home

import android.os.Bundle
import com.water.song.template.common.base.BaseActivity

/**
 * @author Water-Song
 */
class SplashActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_activity_splash)
    }
}