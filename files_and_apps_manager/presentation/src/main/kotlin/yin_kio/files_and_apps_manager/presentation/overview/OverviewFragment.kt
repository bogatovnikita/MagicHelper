package yin_kio.files_and_apps_manager.presentation.overview

import Yin_Koi.files_and_apps_manager.presentation.R
import Yin_Koi.files_and_apps_manager.presentation.databinding.FragmentOverviewBinding
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import file_manager.domain.server.FileManagerServer
import file_manager.domain.server.GroupName
import file_manager.doman.overview.OverviewUseCaseCreator
import jamycake.lifecycle_aware.lifecycleAware
import jamycake.lifecycle_aware.previousBackStackEntry
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import yin_kio.files_and_apps_manager.data.DeleteTimeSaverImpl
import yin_kio.files_and_apps_manager.data.DeleterImpl
import yin_kio.files_and_apps_manager.presentation.overview.models.ScreenState

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

            arrowBackIv.setOnClickListener { viewModel.close() }

            delete.setOnClickListener { viewModel.showAskDeleteDialog() }
        }


        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.state.collect{
                binding.delete.alpha = it.buttonAlpha
                binding.delete.text = it.buttonText
                showChips(it)
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.command.collect{
                when(it){
                    Command.Close -> findNavController().navigateUp()
                    Command.ShowAskDeleteDialog -> TODO()
                    Command.ShowDeleteProgress -> TODO()
                    Command.ShowDeleteCompletion -> TODO()
                    else -> {}
                }
            }
        }
    }

    private fun showChips(it: ScreenState) {
        when (it.groupName) { // Так как здесь используется radiogroup достаточно включить только 1 элемент
            GroupName.Images -> binding.images.isChecked = true
            GroupName.Video -> binding.video.isChecked = true
            GroupName.Audio -> binding.audio.isChecked = true
            GroupName.Documents -> binding.documents.isChecked = true
            GroupName.Apps -> binding.apps.isChecked = true
        }
    }


    private fun createViewModel(
        coroutineScope: CoroutineScope
    ) : ViewModel {

        val context = requireContext().applicationContext

        val presenter = Presenter(context)
        val uiOuter = UiOuterImpl(presenter)
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