package com.water.song.template.app

import android.content.Intent
import android.os.Bundle
import android.view.Window
import androidx.annotation.VisibleForTesting
import androidx.appcompat.app.AppCompatActivity
import com.water.song.template.home.SplashActivity

/**
 * @author Water-Song
 * 使用一层空白 Activity ，是为了解决按 HOME 返回的问题。这在某些 OS 版本上会显示异常的动画。
 */
class LauncherActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        super.onCreate(savedInstanceState)

        if (shouldFinishImmediately(isTaskRoot, intent)) {
            finish()
        } else {
            startActivity(Intent(this, SplashActivity::class.java))
        }
    }

    @VisibleForTesting
    internal fun shouldFinishImmediately(taskRoot: Boolean, argsIntent: Intent?): Boolean {
        if (!taskRoot) {
            argsIntent?.let {
                if (it.hasCategory(Intent.CATEGORY_LAUNCHER) && it.action == Intent.ACTION_MAIN) {
                    return true
                }
            }
        }
        return false
    }
}