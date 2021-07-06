package com.example.coroutines.customize.dialog

import android.app.Dialog
import android.content.Context
import android.view.Window
import com.example.coroutines.R

class LoadingDialog(context: Context): Dialog(context) {

    private var countLoading = 0

    init {
        initLoadingProgress()
    }

    private fun initLoadingProgress() {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE)
        this.setContentView(R.layout.dialog_loading)
        this.window!!.setBackgroundDrawableResource(android.R.color.transparent)
        this.setCancelable(false)
        this.setCanceledOnTouchOutside(false)
    }

    override fun show() {
        try {
            if (countLoading == 0) {
                super.show()
            }
            countLoading++
        } catch (e: Exception) {
        }
    }

    override fun dismiss() {
        countLoading--
        if (countLoading > 0 || !super.isShowing()) return
        super.dismiss()
    }

    companion object {
        var dialog: LoadingDialog? = null

        fun show(context: Context) {
            if (dialog == null) {
                dialog = LoadingDialog(context)
                dialog?.show()
            }
        }

        fun dismiss() {
            dialog?.dismiss()
            dialog = null
        }
    }
}