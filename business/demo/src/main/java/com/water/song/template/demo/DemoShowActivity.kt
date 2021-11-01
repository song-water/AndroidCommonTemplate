package com.water.song.template.demo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.water.song.template.common.FullScreenDialog

/**
 * @author Water-Song
 */
class DemoShowActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.demo_activity_show)
        //startActivity(Intent(this, ActivityTCardGallery::class.java))
        FullScreenDialog.getInstance().show(supportFragmentManager)
    }
}