package yin_kio.files_and_apps_manager.presentation.overview

import Yin_Koi.files_and_apps_manager.presentation.R
import Yin_Koi.files_and_apps_manager.presentation.databinding.DialogDeletingDoneBinding
import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import jamycake.lifecycle_aware.previousBackStackEntry
import kotlinx.coroutines.launch
import yin_kio.files_and_apps_manager.presentation.dialog.FixedWidthDialogFragment

internal class DoneDialog : FixedWidthDialogFragment(R.layout.dialog_deleting_done) {

    private val binding: DialogDeletingDoneBinding by viewBinding()

    private val viewModel: ViewModel by previousBackStackEntry()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.done.setOnClickListener { viewModel.completeDelete() }
        binding.close.setOnClickListener { viewModel.completeDelete() }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.command.collect{
                if (it == Command.HideDoneDialog){
                    findNavController().navigateUp()
                }
            }
        }

    }

}