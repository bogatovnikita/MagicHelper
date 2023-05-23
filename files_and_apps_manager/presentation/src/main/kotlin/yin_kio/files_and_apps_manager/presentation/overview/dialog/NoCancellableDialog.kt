package yin_kio.files_and_apps_manager.presentation.overview.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.drawable.ColorDrawable

fun noCancelableDialog(context: Context) : Dialog {

    return object : Dialog(context) {
        init {
            setCancelable(false)
            setCanceledOnTouchOutside(false)

            window?.setBackgroundDrawable(ColorDrawable(android.R.color.transparent))
        }

        @Deprecated("Deprecated in Java")
        override fun onBackPressed() {}
    }

}