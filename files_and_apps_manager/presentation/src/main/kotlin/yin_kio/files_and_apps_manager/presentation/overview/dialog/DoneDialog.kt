package yin_kio.files_and_apps_manager.presentation.overview.dialog

import Yin_Koi.files_and_apps_manager.presentation.R
import Yin_Koi.files_and_apps_manager.presentation.databinding.DialogDeletingDoneBinding
import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import file_manager.domain.server.GroupName
import jamycake.lifecycle_aware.previousBackStackEntry
import kotlinx.coroutines.launch
import yin_kio.files_and_apps_manager.presentation.dialog.FixedWidthDialogFragment
import yin_kio.files_and_apps_manager.presentation.overview.Command
import yin_kio.files_and_apps_manager.presentation.overview.ViewModel

internal class DoneDialog : FixedWidthDialogFragment(R.layout.dialog_deleting_done) {

    private val binding: DialogDeletingDoneBinding by viewBinding()

    private val viewModel: ViewModel by previousBackStackEntry()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return if (viewModel.state.value.selectedGroup == GroupName.Apps){
            noCancelableDialog(requireContext())
        } else {
            super.onCreateDialog(savedInstanceState)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.done.setOnClickListener { viewModel.completeDelete() }
        binding.close.setOnClickListener { viewModel.completeDelete() }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.command.collect{

                when(it){
                    Command.HideDoneDialog -> findNavController().navigateUp()
                    Command.ShowUpdateAppsProgress -> findNavController().apply {
                        popBackStack(R.id.overviewFragment, false)
                        navigate(R.id.toUpdateAppsListProgress)
                    }
                    else -> {}
                }
            }
        }

    }

}