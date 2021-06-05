package com.angelica.myfavs.utils

import android.graphics.Color
import android.provider.Settings.Global.getString
import android.view.View
import com.angelica.myfavs.R
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar

class Snackbar(val view: View, val msg: String) {

    fun showSnackbar() {
        Snackbar.make(view, msg, Snackbar.LENGTH_INDEFINITE)
            .setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_FADE)
            .setBackgroundTint(Color.BLACK)
            .setActionTextColor(Color.YELLOW)
            .setTextColor(Color.YELLOW)
            .setAction("Ok") {}
            .show()
    }
}