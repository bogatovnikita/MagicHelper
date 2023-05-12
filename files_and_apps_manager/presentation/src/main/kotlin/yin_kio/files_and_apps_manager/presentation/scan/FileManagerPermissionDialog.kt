package yin_kio.files_and_apps_manager.presentation.scan

import Yin_Koi.files_and_apps_manager.presentation.R
import Yin_Koi.files_and_apps_manager.presentation.databinding.FilesAppManagerPermissionDialogBinding
import android.app.Dialog
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import jamycake.lifecycle_aware.previousBackStackEntry

class FileManagerPermissionDialog : DialogFragment(R.layout.files_app_manager_permission_dialog) {

    private val binding: FilesAppManagerPermissionDialogBinding by viewBinding()
    private val viewModel: ViewModel by previousBackStackEntry()


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return super.onCreateDialog(savedInstanceState).apply {
            window?.setBackgroundDrawable(ColorDrawable(
                requireContext().getColor(android.R.color.transparent)
            ))
        }
    }




    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.cancelBtn.setOnClickListener { viewModel.close() }
        binding.allowBtn.setOnClickListener { viewModel.requestPermission() }


        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.command.collect{
                when(it){
                    Command.Close -> repeat(2){findNavController().navigateUp()}
                    Command.RequestPermission -> TODO()
                    else -> {}
                }
            }
        }
    }

}