package yin_kio.files_and_apps_manager.presentation.overview

import Yin_Koi.files_and_apps_manager.presentation.R
import android.app.Dialog
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import jamycake.lifecycle_aware.previousBackStackEntry
import kotlinx.coroutines.launch
import yin_kio.files_and_apps_manager.presentation.dialog.FixedWidthDialogFragment

class DeleteProgressDialog : FixedWidthDialogFragment(R.layout.dialog_deleting_progress) {


    private val viewModel: ViewModel by previousBackStackEntry()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return noCancelableDialog()
    }

    private fun noCancelableDialog() = object : Dialog(requireContext()) {
        init {
            setCancelable(false)
            setCanceledOnTouchOutside(false)

            window?.setBackgroundDrawable(ColorDrawable(android.R.color.transparent))
        }

        @Deprecated("Deprecated in Java")
        override fun onBackPressed() {}
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.command.collect{
                if (it == Command.ShowDeleteCompletion){
                    findNavController().apply {
                        popBackStack(R.id.overviewFragment, false)
                        navigate(R.id.toDoneDialog)
                    }
                }
            }
        }
    }


}