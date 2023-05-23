package ar.cleaner.first.pf.ui.boost.general

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import ar.cleaner.first.pf.R
import ar.cleaner.first.pf.databinding.FragmentBoostBinding
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BoostFragment : Fragment(R.layout.fragment_boost) {

    private val binding: FragmentBoostBinding by viewBinding()
    private val viewModel: BoostViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObserver()
        initClickListener()
    }

    private fun initObserver() {
        lifecycleScope.launchWhenResumed {
            viewModel.state.collect {
                renderState(it)
            }
        }
    }

    private fun renderState(state: BoostState) {
        state.boostDetails ?: return
        if (!state.loadData) return

        initProgress(state)
        setVisibilityGroup(state.boostStatus)
        initOptimizeButton(state.boostStatus)
    }

    private fun initProgress(state: BoostState) {
        with(binding) {
            percentTv.text =
                requireContext().getString(R.string.percent_D, state.boostDetails!!.usagePercents)
            progressBar.progress = state.boostDetails.usagePercents
            occupiedTotalTv.text = requireContext().getString(
                R.string._F_gb_F_gb,
                state.boostDetails.usedRam,
                state.boostDetails.totalRam
            )
        }
    }

    private fun setVisibilityGroup(isOptimize: Boolean) {
        binding.groupOptimizeIsDone.isVisible = isOptimize
        binding.groupIsNotOptimize.isVisible = !isOptimize
    }

    private fun initOptimizeButton(isOptimize: Boolean) {
        binding.dangerButton.apply {
            setTextColor(
                if (isOptimize) ContextCompat.getColor(requireContext(), R.color.black)
                else ContextCompat.getColor(requireContext(), R.color.red)
            )

            text =
                if (isOptimize) getString(R.string.boost_phone_dont_need_optimize)
                else getString(R.string.general_action_required)

            setBackgroundResource(
                if (isOptimize) R.drawable.bg_button_not_danger
                else R.drawable.bg_button_danger
            )
        }
    }

    private fun initClickListener() {
        binding.toolbar.binding.arrowBackIv.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.boostButton.setOnClickListener {
            findNavController().navigate(
                R.id.action_to_boostProgressFragment
            )
        }
    }
}