package com.example.ndk_source.util

import android.content.Context
import android.widget.Toast

object ToastUtil {
    private var TOAST_DURATION_TYPE = Toast.LENGTH_SHORT

    fun duration(durationType: Int): ToastUtil {
        this.TOAST_DURATION_TYPE = durationType
        return this
    }

    fun show(context: Context, string: String) {
        Toast.makeText(context, string, TOAST_DURATION_TYPE).show()
    }
}