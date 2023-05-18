package yin_kio.files_and_apps_manager.presentation.overview

import Yin_Koi.files_and_apps_manager.presentation.R
import Yin_Koi.files_and_apps_manager.presentation.databinding.FragmentOverviewBinding
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import by.kirich1409.viewbindingdelegate.viewBinding
import file_manager.domain.server.FileManagerServer
import file_manager.domain.server.GroupName
import file_manager.doman.overview.OverviewUseCaseCreator
import jamycake.lifecycle_aware.lifecycleAware
import jamycake.lifecycle_aware.previousBackStackEntry
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import yin_kio.files_and_apps_manager.data.DeleteTimeSaverImpl
import yin_kio.files_and_apps_manager.data.DeleterImpl

internal class OverviewFragment : Fragment(R.layout.fragment_overview) {

    private val binding: FragmentOverviewBinding by viewBinding()
    private val server: FileManagerServer by previousBackStackEntry()
    private val viewModel: ViewModel by lifecycleAware { createViewModel(viewModelScope) }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.apply {
            images.setOnClickListener { viewModel.switchGroup(GroupName.Images) }
            video.setOnClickListener { viewModel.switchGroup(GroupName.Video) }
            audio.setOnClickListener { viewModel.switchGroup(GroupName.Audio) }
            documents.setOnClickListener { viewModel.switchGroup(GroupName.Documents) }
            apps.setOnClickListener { viewModel.switchGroup(GroupName.Apps) }
        }


        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.state.collect{
                when(it.groupName){ // Так как здесь используется radiogroup достаточно включить только 1 элемент
                    GroupName.Images -> binding.images.isChecked = true
                    GroupName.Video -> binding.video.isChecked = true
                    GroupName.Audio -> binding.audio.isChecked = true
                    GroupName.Documents -> binding.documents.isChecked = true
                    GroupName.Apps -> binding.apps.isChecked = true
                }
            }
        }
    }


    private fun createViewModel(
        coroutineScope: CoroutineScope
    ) : ViewModel {

        val context = requireContext().applicationContext

        val uiOuter = UiOuterImpl()
        val useCase = OverviewUseCaseCreator.create(
            uiOuter = uiOuter,
            server = server,
            deleter = DeleterImpl(),
            deleteTimeSaver = DeleteTimeSaverImpl(context),
            coroutineScope = coroutineScope
        )

        uiOuter.viewModel = ViewModel(useCase)
        return uiOuter.viewModel!!
    }


}