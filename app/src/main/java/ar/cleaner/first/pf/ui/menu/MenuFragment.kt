package ar.cleaner.first.pf.ui.menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import ar.cleaner.first.pf.R
import ar.cleaner.first.pf.databinding.FragmentMenuBinding
import ar.cleaner.first.pf.domain.models.CleanerDetails
import ar.cleaner.first.pf.domain.models.details.BatteryDetails
import ar.cleaner.first.pf.domain.models.details.CpuDetails
import ar.cleaner.first.pf.domain.models.details.RamDetails
import ar.cleaner.first.pf.extensions.fragmentLifecycleScope
import ar.cleaner.first.pf.extensions.observeWhenResumed
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MenuFragment : Fragment() {

    private var _binding: FragmentMenuBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MenuViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMenuBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setColorStatusBar()
        initObserver()
    }

    private fun initObserver() {
        viewModel.state.observeWhenResumed(
            lifecycleScope = fragmentLifecycleScope,
            ::renderState
        )
    }

    private fun renderState(screenState: MenuState) {
        with(screenState) {
            ramDetails.render()
            batteryDetails.render()
            cleanerDetails.render()
            cpuDetails.render()
        }
    }

    private fun RamDetails?.render() {
        this ?: return
    }

    private fun BatteryDetails?.render() {
        this ?: return
    }

    private fun CleanerDetails?.render() {
        this ?: return
    }

    private fun CpuDetails?.render() {
        this ?: return
    }

    private fun setColorStatusBar() {
        requireActivity().window.statusBarColor =
            ContextCompat.getColor(requireActivity(), R.color.dark_blue)
        requireActivity().window.decorView.systemUiVisibility = 0
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}