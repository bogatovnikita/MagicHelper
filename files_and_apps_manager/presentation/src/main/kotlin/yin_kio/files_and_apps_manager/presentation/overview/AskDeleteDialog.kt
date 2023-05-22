package yin_kio.files_and_apps_manager.presentation.overview

import Yin_Koi.files_and_apps_manager.presentation.R
import Yin_Koi.files_and_apps_manager.presentation.databinding.DialogAskDeleteBinding
import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import by.kirich1409.viewbindingdelegate.viewBinding
import jamycake.lifecycle_aware.previousBackStackEntry
import kotlinx.coroutines.launch
import yin_kio.files_and_apps_manager.presentation.dialog.FixedWidthDialogFragment

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
                    else -> {}
                }
            }
        }
    }


}