package yin_kio.files_and_apps_manager.presentation.scan

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import file_manager.domain.server.FileManagerServer
import file_manager.scan_progress.ScanProgressUseCaseCreator
import jamycake.lifecycle_aware.currentBackStackEntry
import jamycake.lifecycle_aware.lifecycleAware
import jamycake.lifecycle_aware.previousBackStackEntry
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import yin_kio.files_and_apps_manager.data.FilesAndAppsImpl
import yin_kio.files_and_apps_manager.data.PermissionsImpl
import yin_kio.files_and_apps_manager.presentation.scan.databinding.FamFragmentScanBinding

class ScanFragment : Fragment(R.layout.fam_fragment_scan) {

    private val server: FileManagerServer by previousBackStackEntry()
    private val viewModel: ViewModel by currentBackStackEntry { createViewModel(viewModelScope) }




    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.command.collect{
                when(it){
                    Command.Close -> findNavController().navigateUp()
                    Command.RequestPermission -> TODO()
                    Command.ShowPermissionDialog -> TODO()
                    Command.ShowInter -> TODO()
                    Command.ShowProgress -> TODO()
                }
            }
        }
    }



    private fun createViewModel(coroutineScope: CoroutineScope) : ViewModel{
        val context = requireContext().applicationContext


        val uiOuter = UiOuterImpl()
        val useCase = ScanProgressUseCaseCreator.create(
            uiOuter = uiOuter,
            filesAndApps = FilesAndAppsImpl(context),
            fileManagerServer = server,
            permissions = PermissionsImpl(context),
            coroutineScope = coroutineScope
        )

        uiOuter.viewModel = ViewModel(useCase)

        return uiOuter.viewModel!!
    }
}