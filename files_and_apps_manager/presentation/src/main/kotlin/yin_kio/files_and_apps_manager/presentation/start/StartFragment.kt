package yin_kio.files_and_apps_manager.presentation.start

import Yin_Koi.files_and_apps_manager.presentation.R
import Yin_Koi.files_and_apps_manager.presentation.databinding.FragmentStartBinding
import android.content.res.ColorStateList
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import file_manager.domain.server.FileAndAppsServerImpl
import file_manager.domain.server.FileManagerServer
import file_manager.start_screen.use_case.StartScreenUseCaseCreator
import jamycake.lifecycle_aware.currentBackStackEntry
import jamycake.lifecycle_aware.lifecycleAware
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import yin_kio.files_and_apps_manager.data.DeleteTimeSaverImpl
import yin_kio.files_and_apps_manager.data.UsedMemImpl

internal class StartFragment : Fragment(R.layout.fragment_start) {


    private val binding: FragmentStartBinding by viewBinding()
    private val viewModel: ViewModel by lifecycleAware { createViewModel(viewModelScope) }
    private val server = currentBackStackEntry<FileManagerServer>(R.id.startFragment) { FileAndAppsServerImpl() }


    override fun onStart() {
        super.onStart()
        viewModel.update()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        server.value

        binding.scanButton.setOnClickListener {
            viewModel.scan()
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.state.collect{
                binding.percentageUsedTv.text = it.percents
                binding.gigabyteUsedTv.text = it.occupiedAndTotal
                binding.progressBar.progress = it.progress
                binding.dangerButton.text = it.dangerText
                binding.dangerButton.setTextColor(it.dangerColor)
                binding.dangerButton.setBackgroundResource(it.dangerBg)
            }
        }

        viewLifecycleOwner.lifecycleScope.launch{
            viewModel.command.collect{
                when(it){
                    Command.ShowScanProgress -> findNavController().navigate(R.id.toScanFragment)
                    Command.Close -> findNavController().navigateUp()
                }
            }
        }
    }



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
}