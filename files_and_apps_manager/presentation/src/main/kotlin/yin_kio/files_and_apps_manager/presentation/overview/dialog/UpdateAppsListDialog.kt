package yin_kio.files_and_apps_manager.presentation.overview.dialog

import Yin_Koi.files_and_apps_manager.presentation.R
import android.app.Dialog
import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import jamycake.lifecycle_aware.previousBackStackEntry
import kotlinx.coroutines.launch
import yin_kio.files_and_apps_manager.presentation.dialog.FixedWidthDialogFragment
import yin_kio.files_and_apps_manager.presentation.overview.Command
import yin_kio.files_and_apps_manager.presentation.overview.ViewModel

class UpdateAppsListDialog : FixedWidthDialogFragment(R.layout.dialog_update_apps_progress) {

    private val viewModel: ViewModel by previousBackStackEntry()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return noCancelableDialog(requireContext())
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.command.collect{
                when(it){
                    Command.HideUpdateAppsProgress -> findNavController().navigateUp()
                    else -> {}
                }
            }
        }
    }

}