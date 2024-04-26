package com.example.basesetupforandroid.util

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

object KeyboardUtil {

    fun hide(activity: Activity) {
        val imm = inputMethodManager(activity)
        imm?.hideSoftInputFromWindow(activity.currentFocus?.windowToken, 0)
    }

    fun hide(view: View) {
        val imm = inputMethodManager(view.context)
        imm?.hideSoftInputFromWindow(view.windowToken, 0)
    }

    fun show(context: Context) {
        val imm = inputMethodManager(context)
        imm?.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
    }

    fun show(view: View) {
        val imm = inputMethodManager(view.context)
        imm?.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
    }

    private fun inputMethodManager(context: Context): InputMethodManager? {
        return context.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
    }
}
