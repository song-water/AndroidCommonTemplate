package com.water.song.template.common

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.water.song.template.common.util.applyWindowInsets
import com.water.song.template.common.util.makeImmersive

/**
 * @author Water-Song
 */
class FullScreenDialog : DialogFragment() {
    companion object {
        const val TAG = "FullScreenDialog"

        @JvmStatic
        fun getInstance() = FullScreenDialog()
    }

    fun show(fm: FragmentManager) {
        fm.beginTransaction().add(this, TAG).commit()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.FullScreenDialogTheme)
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.makeImmersive()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.dialog_full_screen, container, false)
        dialog?.window?.let { win ->
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.Q) {
                win.attributes?.let { attr ->
                    attr.layoutInDisplayCutoutMode =
                        WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                        attr.fitInsetsTypes = WindowInsetsCompat.Type.statusBars()
                    }
                }
            } else {
                view.fitsSystemWindows = true
            }
            win.applyWindowInsets(view)
        }
        isCancelable = true
        return view
    }
}