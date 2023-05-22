package yin_kio.files_and_apps_manager.presentation.overview

import Yin_Koi.files_and_apps_manager.presentation.R
import android.app.Dialog
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import yin_kio.files_and_apps_manager.presentation.dialog.FixedWidthDialogFragment

class DeleteProgressDialog : FixedWidthDialogFragment(R.layout.dialog_deleting_progress) {


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return noCancelableDialog()
    }

    private fun noCancelableDialog() = object : Dialog(requireContext()) {
        init {
            setCancelable(false)
            setCanceledOnTouchOutside(false)

            window?.setBackgroundDrawable(ColorDrawable(android.R.color.transparent))
        }

        override fun onBackPressed() {}
    }


}