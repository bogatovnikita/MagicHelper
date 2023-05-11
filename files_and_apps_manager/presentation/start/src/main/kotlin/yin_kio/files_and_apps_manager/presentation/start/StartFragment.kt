package yin_kio.files_and_apps_manager.presentation.start

import Yin_Koi.files_and_apps_manager.presentation.R
import Yin_Koi.files_and_apps_manager.presentation.databinding.FilesAppManagerStartBinding
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import by.kirich1409.viewbindingdelegate.viewBinding
import file_manager.domain.server.FileAndAppsServerImpl
import file_manager.domain.server.FileManagerServer
import file_manager.start_screen.use_case.StartScreenUseCaseCreator
import jamycake.lifecycle_aware.currentBackStackEntry
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import yin_kio.files_and_apps_manager.data.DeleteTimeSaverImpl
import yin_kio.files_and_apps_manager.data.UsedMemImpl

class StartFragment : Fragment(R.layout.files_app_manager_start) {


    private val binding: FilesAppManagerStartBinding by viewBinding()
    private val viewModel: ViewModel by currentBackStackEntry { createViewModel(viewModelScope) }
    // It is need for scan and overview screens.
    private val fileManagerServer: FileManagerServer by currentBackStackEntry { FileAndAppsServerImpl() }

    private fun createViewModel(coroutineScope: CoroutineScope) : ViewModel{
        val context = requireContext().applicationContext

        val uiOuter = UiOuterImpl(
            presenter = Presenter(context)
        )

        val useCase = StartScreenUseCaseCreator.create(
            uiOuter = uiOuter,
            usedMem = UsedMemImpl(context),
            lastCleanTime = DeleteTimeSaverImpl(context),
            coroutineScope = coroutineScope
        )

        uiOuter.viewModel = ViewModel(useCase)

        return uiOuter.viewModel!!
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.state.collect{
                binding.percentageUsedTv.text = it.percents
                binding.gigabyteUsedTv.text = it.occupiedAndTotal
                binding.progressBar.progress = it.progress
            }
        }
    }

}