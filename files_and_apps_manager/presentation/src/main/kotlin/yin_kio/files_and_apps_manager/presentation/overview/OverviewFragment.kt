package yin_kio.files_and_apps_manager.presentation.overview

import Yin_Koi.files_and_apps_manager.presentation.R
import Yin_Koi.files_and_apps_manager.presentation.databinding.FragmentOverviewBinding
import Yin_Koi.files_and_apps_manager.presentation.databinding.PopupSortBinding
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.PopupWindow
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import file_manager.domain.server.FileManagerServer
import file_manager.domain.server.GroupName
import file_manager.domain.server.SortingMode
import file_manager.doman.overview.OverviewUseCaseCreator
import file_manager.doman.overview.ui_out.Selectable
import jamycake.lifecycle_aware.currentBackStackEntry
import jamycake.lifecycle_aware.previousBackStackEntryWithCache
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import yin_kio.file_app_manager.updater.ContentUpdaterImpl
import yin_kio.file_grouper.GrouperImpl
import yin_kio.files_and_apps_manager.data.DeleteTimeSaverImpl
import yin_kio.files_and_apps_manager.data.FilesAndAppsImpl
import yin_kio.files_and_apps_manager.data.FilesDeleterImpl
import yin_kio.files_and_apps_manager.presentation.overview.adapter.AppAdapter
import yin_kio.files_and_apps_manager.presentation.overview.adapter.DocAdapter
import yin_kio.files_and_apps_manager.presentation.overview.adapter.ImageAdapter
import yin_kio.files_and_apps_manager.presentation.overview.models.FileOrAppItem
import yin_kio.files_and_apps_manager.presentation.overview.models.ScreenState

internal class OverviewFragment : Fragment(R.layout.fragment_overview) {

    private val binding: FragmentOverviewBinding by viewBinding()
    private val server: FileManagerServer by previousBackStackEntryWithCache(R.id.overviewFragment)
    private val viewModel: ViewModel by currentBackStackEntry(R.id.overviewFragment) { createViewModel(viewModelScope) }

    private var onDismissSortingPopup: (() -> Unit)? = null // эта лямбда вынесена для того, чтобы диалог не прятался при дисмисе попапа

    private val sortingPopup: PopupWindow by lazy { createSortingPopup() }

    private val onItemUpdate: (fileOrApp: FileOrAppItem, selectable: Selectable) -> Unit = {
            item, selectable -> viewModel.updateSelectable(item.id, selectable)
    }

    private val onItemClick: (fileOrApp: FileOrAppItem, selectable: Selectable) -> Unit = {
            item, selectable -> viewModel.switchItemSelection(item.id, selectable)
    }


    private val imageAdapter by lazy { ImageAdapter(
        onUpdate = onItemUpdate,
        onItemClick = onItemClick
    ) } // ВНИМАНИЕ!!! Здесь идёт жуткое дублирование.
    private val docAdapter by lazy { DocAdapter(
        onUpdate = onItemUpdate,
        onItemClick = onItemClick
    ) }
    private val appAdapter by lazy { AppAdapter(
        onUpdate = onItemUpdate,
        onItemClick = onItemClick
    ) }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.recycler.itemAnimator = null

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

            selectAllCheckbox.onClick { viewModel.switchAllSelection(viewModel.state.value.selectedGroup) }
            selectAllText.onClick { viewModel.switchAllSelection(viewModel.state.value.selectedGroup) }
        }
    }

    private fun setupStateObserver() {



        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.state.collect {
                    showButton(it)
                    showChips(it)
                    showSortingControlPanel(it)
                    showList(it)

                    binding.selectAllCheckbox.isChecked = it.isAllSelected

                }
            }


        }
    }

    private fun showList(it: ScreenState) {
        val adapter = when (it.selectedGroup) {
            GroupName.Images -> imageAdapter
            GroupName.Video -> imageAdapter
            GroupName.Audio -> docAdapter
            GroupName.Documents -> docAdapter
            GroupName.Apps -> appAdapter
        }

        if (binding.recycler.adapter != adapter){

            binding.recycler.adapter = adapter
        }

        adapter.submitList(it.content)
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


    @SuppressLint("NotifyDataSetChanged")
    private fun setupCommandsObserver() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.command.collect {
                when (it) {
                    Command.Close -> findNavController().navigateUp()
                    Command.ShowAskDeleteDialog -> findNavController().apply {
                        if (currentDestination?.id == R.id.overviewFragment){
                            navigate(R.id.toAskDelete)
                        }
                    }
                    Command.UpdateListContent -> binding.recycler.adapter?.notifyDataSetChanged()
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
        when (it.selectedGroup) { // Так как здесь используется radiogroup достаточно включить только 1 элемент
            GroupName.Images -> binding.images.isChecked = true
            GroupName.Video -> binding.video.isChecked = true
            GroupName.Audio -> binding.audio.isChecked = true
            GroupName.Documents -> binding.documents.isChecked = true
            GroupName.Apps -> binding.apps.isChecked = true
        }

        binding.images.isVisible = it.availableGroups.contains(GroupName.Images)
        binding.video.isVisible = it.availableGroups.contains(GroupName.Video)
        binding.audio.isVisible = it.availableGroups.contains(GroupName.Audio)
        binding.documents.isVisible = it.availableGroups.contains(GroupName.Documents)
        binding.apps.isVisible = it.availableGroups.contains(GroupName.Apps)
    }


    private fun createViewModel(
        coroutineScope: CoroutineScope
    ) : ViewModel {

        val context = requireContext().applicationContext

        val updater = ContentUpdaterImpl(
            filesAndApps = FilesAndAppsImpl(context),
            server = server,
            grouper = GrouperImpl()
        )

        val presenter = Presenter(context)
        val uiOuter = UiOuterImpl(presenter)
        val useCase = OverviewUseCaseCreator.create(
            uiOuter = uiOuter,
            server = server,
            filesDeleter = FilesDeleterImpl(),
            deleteTimeSaver = DeleteTimeSaverImpl(context),
            contentUpdater = updater,
            appsDeleter = uiOuter,
            coroutineScope = coroutineScope,
        )

        uiOuter.viewModel = ViewModel(useCase)
        return uiOuter.viewModel!!
    }

    private fun createSortingPopup() : PopupWindow{


        val binding: PopupSortBinding = PopupSortBinding.inflate(layoutInflater, null, false)

        when(viewModel.state.value.sortingMode){
            SortingMode.NewFirst -> binding.newFirst.isChecked = true
            SortingMode.OldFirst -> binding.oldFirst.isChecked = true
            SortingMode.BigFirst -> binding.bigFirst.isChecked = true
            SortingMode.SmallFirst -> binding.smallFirst.isChecked = true
        }


        val popup = PopupWindow(requireContext()).apply {
            setBackgroundDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.bg_popup))
            contentView = binding.root
            setOnDismissListener { onDismissSortingPopup?.invoke() }
            isOutsideTouchable = true
            elevation = 100f
        }


        binding.newFirst.onClick {
            viewModel.setSortingMode(SortingMode.NewFirst)
        }
        binding.oldFirst.onClick {
            viewModel.setSortingMode(SortingMode.OldFirst)
        }
        binding.bigFirst.onClick {
            viewModel.setSortingMode(SortingMode.BigFirst)
        }
        binding.smallFirst.onClick {
            viewModel.setSortingMode(SortingMode.SmallFirst)
        }

        return popup
    }

    private fun <T : View> T.onClick(action: (T) -> Unit){
        setOnClickListener { action(this) }
    }
}