package com.example.basesetupforandroid.util.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Window
import com.example.basesetupforandroid.R

object Loader {
    private var dialog: Dialog? = null

    fun show(context: Context) {
        if (dialog?.isShowing == true) {
            hide()
        }
        try {
            dialog = Dialog(context)
            dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog?.setContentView(R.layout.progressbar_dialog_layout)
            dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog?.setCanceledOnTouchOutside(false)
            dialog?.show()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun hide() {
        dialog?.let {
            if (it.isShowing) {
                it.dismiss()
            }
        }
    }
}