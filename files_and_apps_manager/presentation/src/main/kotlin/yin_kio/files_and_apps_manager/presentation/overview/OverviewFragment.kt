package yin_kio.files_and_apps_manager.presentation.overview

import Yin_Koi.files_and_apps_manager.presentation.R
import Yin_Koi.files_and_apps_manager.presentation.databinding.FragmentOverviewBinding
import Yin_Koi.files_and_apps_manager.presentation.databinding.PopupSortBinding
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.PopupWindow
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import file_manager.domain.server.FileManagerServer
import file_manager.domain.server.GroupName
import file_manager.domain.server.SortingMode
import file_manager.doman.overview.OverviewUseCaseCreator
import jamycake.lifecycle_aware.lifecycleAware
import jamycake.lifecycle_aware.previousBackStackEntry
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import yin_kio.files_and_apps_manager.data.DeleteTimeSaverImpl
import yin_kio.files_and_apps_manager.data.DeleterImpl
import yin_kio.files_and_apps_manager.presentation.overview.adapter.AppAdapter
import yin_kio.files_and_apps_manager.presentation.overview.adapter.DocAdapter
import yin_kio.files_and_apps_manager.presentation.overview.adapter.ImageAdapter
import yin_kio.files_and_apps_manager.presentation.overview.models.ScreenState

internal class OverviewFragment : Fragment(R.layout.fragment_overview) {

    private val binding: FragmentOverviewBinding by viewBinding()
    private val server: FileManagerServer by previousBackStackEntry()
    private val viewModel: ViewModel by lifecycleAware { createViewModel(viewModelScope) }

    private var onDismissSortingPopup: (() -> Unit)? = null // эта лямбда вынесена для того, чтобы диалог не прятался при дисмисе попапа

    private val sortingPopup: PopupWindow by lazy { createSortingPopup() }


    private val imageAdapter by lazy { ImageAdapter() }
    private val docAdapter by lazy { DocAdapter() }
    private val appAdapter by lazy { AppAdapter() }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.d("!!!", "server $server")

        setupListeners()
        setupStateObserver()
        setupCommandsObserver()
    }

    override fun onDestroy() {
        super.onDestroy()
        onDismissSortingPopup = null
        sortingPopup.dismiss() // Нужно для того, чтобы не выбрасывалось исключение, при изменении конфигурации
    }

    private fun setupListeners() {
        binding.apply {
            images.onClick { viewModel.switchGroup(GroupName.Images) }
            video.onClick { viewModel.switchGroup(GroupName.Video) }
            audio.onClick { viewModel.switchGroup(GroupName.Audio) }
            documents.onClick { viewModel.switchGroup(GroupName.Documents) }
            apps.onClick { viewModel.switchGroup(GroupName.Apps) }

            arrowBackIv.onClick { viewModel.close() }

            delete.onClick { viewModel.showAskDeleteDialog() }

            sortImage.onClick {  viewModel.showSortingSelection() }
            sortText.onClick {  viewModel.showSortingSelection() }
            onDismissSortingPopup = { viewModel.hideSortingSelection() }
        }
    }

    private fun setupStateObserver() {

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.state.collect {
                showButton(it)
                showChips(it)
                showSortingControlPanel(it)
                showList(it)

            }
        }
    }

    private fun showList(it: ScreenState) {
        val adapter = when (it.groupName) {
            GroupName.Images -> imageAdapter
            GroupName.Video -> imageAdapter
            GroupName.Audio -> docAdapter
            GroupName.Documents -> docAdapter
            GroupName.Apps -> appAdapter
        }

        binding.recycler.adapter = adapter

        adapter.submitList(it.filesOrApps)
    }

    private fun showSortingControlPanel(it: ScreenState) {
        binding.sortText.text = it.sortingModeText

        binding.sortImage.isEnabled = !it.isShowSortingSelection
        binding.sortText.isEnabled = !it.isShowSortingSelection

        if (it.isShowSortingSelection) {
            binding.sortImage.post {
                sortingPopup.showAsDropDown(binding.sortImage)
            }
        } else {
            sortingPopup.dismiss()
        }
    }


    private fun setupCommandsObserver() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.command.collect {
                when (it) {
                    Command.Close -> findNavController().navigateUp()
                    Command.ShowAskDeleteDialog -> TODO()
                    Command.ShowDeleteProgress -> TODO()
                    Command.ShowDeleteCompletion -> TODO()
                    else -> {}
                }
            }
        }
    }


    private fun showButton(it: ScreenState) {
        binding.delete.alpha = it.buttonAlpha
        binding.delete.text = it.buttonText
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

    private fun createSortingPopup() : PopupWindow{


        val binding: PopupSortBinding = PopupSortBinding.inflate(layoutInflater, null, false)

        val sortingMode = viewModel.state.value.sortingMode

        when(sortingMode){
            SortingMode.NewFirst -> binding.newFirst.isChecked = true
            SortingMode.OldFirst -> binding.oldFirst.isChecked = true
            SortingMode.BigFirst -> binding.bigFirst.isChecked = true
            SortingMode.SmallFirst -> binding.smallFirst.isChecked = true
        }

        binding.newFirst.onClick { viewModel.setSortingMode(SortingMode.NewFirst) }
        binding.oldFirst.onClick { viewModel.setSortingMode(SortingMode.OldFirst) }
        binding.bigFirst.onClick { viewModel.setSortingMode(SortingMode.BigFirst) }
        binding.smallFirst.onClick { viewModel.setSortingMode(SortingMode.SmallFirst) }

        val popup = PopupWindow(requireContext()).apply {
            setBackgroundDrawable(ColorDrawable(requireContext().getColor(android.R.color.transparent)))
            contentView = binding.root
            setOnDismissListener { onDismissSortingPopup?.invoke() }
            isOutsideTouchable = true


        }
        return popup
    }

    private fun <T : View> T.onClick(action: (T) -> Unit){
        setOnClickListener { action(this) }
    }
}