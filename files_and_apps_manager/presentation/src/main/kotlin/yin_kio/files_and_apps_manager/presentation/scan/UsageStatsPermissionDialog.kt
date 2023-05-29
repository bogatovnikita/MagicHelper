package yin_kio.files_and_apps_manager.presentation.scan

import Yin_Koi.files_and_apps_manager.presentation.R
import Yin_Koi.files_and_apps_manager.presentation.databinding.DialogFileAppManagerUsageStatsPermissionBinding
import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.permissions.requestPackageUsageStats
import jamycake.lifecycle_aware.previousBackStackEntry
import yin_kio.files_and_apps_manager.presentation.dialog.FixedWidthDialogFragment

internal class UsageStatsPermissionDialog : FixedWidthDialogFragment(R.layout.dialog_file_app_manager_usage_stats_permission) {


    private val binding: DialogFileAppManagerUsageStatsPermissionBinding by viewBinding()
    private val viewModel: ViewModel by previousBackStackEntry()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.cancel.setOnClickListener { viewModel.close() }
        binding.crossExitIv.setOnClickListener { viewModel.close() }
        binding.allow.setOnClickListener { viewModel.requestPermission() }


        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.command.collect{
                when(it){
                    Command.Close -> closeDialogAndScan()
                    Command.RequestUsageStatsPermission -> {
                        requireActivity().requestPackageUsageStats()
                        closeDialogAndScan()
                    }
                    Command.ShowProgress -> findNavController().navigateUp()
                    else -> {}
                }
            }
        }
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        viewModel.close()
    }


    private fun closeDialogAndScan() {
        findNavController().popBackStack(R.id.startFragment, false)
    }

}