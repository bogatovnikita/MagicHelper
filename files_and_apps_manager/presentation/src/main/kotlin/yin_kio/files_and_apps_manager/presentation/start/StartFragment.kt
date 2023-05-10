package yin_kio.files_and_apps_manager.presentation.start

import Yin_Koi.files_and_apps_manager.presentation.R
import Yin_Koi.files_and_apps_manager.presentation.databinding.FilesAppManagerStartBinding
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import file_manager.start_screen.use_case.StartScreenUseCaseCreator
import jamycake.lifecycle_aware.currentBackStackEntry
import kotlinx.coroutines.launch
import yin_kio.files_and_apps_manager.data.DeleteTimeSaverImpl
import yin_kio.files_and_apps_manager.data.UsedMemImpl

class StartFragment : Fragment(R.layout.files_app_manager_start) {


    private val binding: FilesAppManagerStartBinding by viewBinding()
    private val viewModel: ViewModel by currentBackStackEntry { createViewModel() }

    private fun createViewModel() : ViewModel{
        val context = requireContext().applicationContext

        val uiOuter = UiOuterImpl(
            presenter = Presenter(context)
        )

        val useCase = StartScreenUseCaseCreator.create(
            uiOuter = uiOuter,
            usedMem = UsedMemImpl(context),
            lastCleanTime = DeleteTimeSaverImpl(context)
        )

        uiOuter.viewModel = ViewModel(useCase)

        return uiOuter.viewModel!!
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.state.collect{
                Log.d("!!!", "collect")
                binding.percentageUsedTv.text = it.percents
                binding.gigabyteUsedTv.text = it.occupiedAndTotal
                binding.progressBar.progress = it.progress
            }
        }
    }

}