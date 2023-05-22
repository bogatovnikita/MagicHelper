package yin_kio.files_and_apps_manager.presentation.scan

import Yin_Koi.files_and_apps_manager.presentation.R
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import com.yin_kio.ads.showAds
import file_manager.domain.server.FileManagerServer
import file_manager.scan_progress.ScanProgressUseCaseCreator
import jamycake.lifecycle_aware.currentBackStackEntry
import jamycake.lifecycle_aware.previousBackStackEntry
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import yin_kio.file_app_manager.updater.UpdaterImpl
import yin_kio.file_grouper.GrouperImpl
import yin_kio.files_and_apps_manager.data.AdsImpl
import yin_kio.files_and_apps_manager.data.FilesAndAppsImpl
import yin_kio.files_and_apps_manager.data.PermissionsImpl

internal class ScanFragment : Fragment(R.layout.fragment_scan) {

    private val server: FileManagerServer by previousBackStackEntry()
    private val viewModel: ViewModel by currentBackStackEntry { createViewModel(viewModelScope) }




    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.command.collect{
                when(it){
                    Command.Close -> findNavController().navigateUp()
                    Command.ShowStoragePermissionDialog -> findNavController().navigate(R.id.toPermissionDialog)
                    Command.ShowUsageStatsPermissionDialog -> findNavController().navigate(R.id.toUsageStatsPermission)
                    Command.ShowInter -> {
                        showAds {
                            findNavController().popBackStack(R.id.startFragment, false)
                            findNavController().navigate(R.id.overviewFragment)
                        }
                    }
                    Command.ShowProgress -> {}
                    else -> {}
                }
            }
        }
    }



    private fun createViewModel(coroutineScope: CoroutineScope) : ViewModel {
        val context = requireContext().applicationContext


        val updater = UpdaterImpl(
            filesAndApps = FilesAndAppsImpl(context),
            server = server,
            grouper = GrouperImpl()
        )


        val uiOuter = UiOuterImpl(
            presenter = Presenter(context)
        )
        val useCase = ScanProgressUseCaseCreator.create(
            uiOuter = uiOuter,
            updater = updater,
            permissions = PermissionsImpl(context),
            coroutineScope = coroutineScope,
            ads = AdsImpl(context)
        )

        uiOuter.viewModel = ViewModel(useCase)

        return uiOuter.viewModel!!
    }
}