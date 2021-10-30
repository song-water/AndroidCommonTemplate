package com.water.song.template.demo

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

/**
 * @author Water-Song
 */
class DemoShowActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.demo_activity_show)
        startActivity(Intent(this, ActivityTCardGallery::class.java))
    }
}