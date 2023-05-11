package yin_kio.files_and_apps_manager.presentation.scan

import android.os.Bundle
import android.view.View
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import jamycake.lifecycle_aware.previousBackStackEntry
import yin_kio.files_and_apps_manager.presentation.scan.databinding.FilesAppManagerPermissionDialogBinding

class FileManagerPermissionDialog : DialogFragment(R.layout.files_app_manager_permission_dialog) {

    private val binding: FilesAppManagerPermissionDialogBinding by viewBinding()
    private val viewModel: ViewModel by previousBackStackEntry()


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