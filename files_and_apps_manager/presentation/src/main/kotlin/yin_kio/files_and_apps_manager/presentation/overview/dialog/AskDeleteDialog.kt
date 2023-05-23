package yin_kio.files_and_apps_manager.presentation.overview.dialog

import Yin_Koi.files_and_apps_manager.presentation.R
import Yin_Koi.files_and_apps_manager.presentation.databinding.DialogAskDeleteBinding
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import jamycake.lifecycle_aware.previousBackStackEntry
import kotlinx.coroutines.launch
import yin_kio.files_and_apps_manager.presentation.dialog.FixedWidthDialogFragment
import yin_kio.files_and_apps_manager.presentation.overview.Command
import yin_kio.files_and_apps_manager.presentation.overview.ViewModel

internal class AskDeleteDialog : FixedWidthDialogFragment(R.layout.dialog_ask_delete) {

    private val binding: DialogAskDeleteBinding by viewBinding()
    private val viewModel: ViewModel by previousBackStackEntry()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.apply {
            cancel.setOnClickListener { viewModel.hideAskDeleteDialog() }
            close.setOnClickListener { viewModel.hideAskDeleteDialog() }
            delete.setOnClickListener { viewModel.delete() }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.command.collect{
                when(it){
                    Command.HideAskDeleteDialog -> findNavController().navigateUp()
                    Command.ShowDeleteProgress -> {
                        findNavController().apply {
                            popBackStack(R.id.overviewFragment, false)
                            navigate(R.id.toDeleteProgress)
                        }
                    }
                    is Command.DeleteApps -> it.ids.forEach { id ->
                        val uri = Uri.parse("package:$id")
                        val intent = Intent(Intent.ACTION_DELETE, uri)
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                        requireActivity().startActivity(intent)
                    }
                    else -> {}
                }
            }
        }
    }


}