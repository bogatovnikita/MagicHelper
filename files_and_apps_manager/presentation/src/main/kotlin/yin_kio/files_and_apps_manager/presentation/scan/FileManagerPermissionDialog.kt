package yin_kio.files_and_apps_manager.presentation.scan

import Yin_Koi.files_and_apps_manager.presentation.R
import Yin_Koi.files_and_apps_manager.presentation.databinding.DialogFileAppManagerPermissionBinding
import android.Manifest
import android.app.Dialog
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.permissions.requestManageExternalStorage
import jamycake.lifecycle_aware.previousBackStackEntry
import yin_kio.files_and_apps_manager.presentation.dialog.FixedWidthDialogFragment

internal class FileManagerPermissionDialog : FixedWidthDialogFragment(R.layout.dialog_file_app_manager_permission) {

    private val binding: DialogFileAppManagerPermissionBinding by viewBinding()
    private val viewModel: ViewModel by previousBackStackEntry()

    private val permissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ){
        if (it.containsValue(false)){
            viewModel.close()
        } else {
            viewModel.scan()
        }
    }






    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.cancelBtn.setOnClickListener { viewModel.close() }
        binding.crossExitIv.setOnClickListener { viewModel.close() }
        binding.allowBtn.setOnClickListener { viewModel.requestPermission() }


        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.command.collect{
                when(it){
                    Command.Close -> closeDialogAndScan()
                    Command.RequestStoragePermission -> requestPermission()
                    Command.ShowProgress -> findNavController().navigateUp()
                    else -> {}
                }
            }
        }
    }

    private fun requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            requireActivity().requestManageExternalStorage()
            closeDialogAndScan()
        } else {
            permissionLauncher.launch(
                arrayOf(
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
            )
        }
    }

    private fun closeDialogAndScan() {
        findNavController().popBackStack(R.id.startFragment, false)
    }

}