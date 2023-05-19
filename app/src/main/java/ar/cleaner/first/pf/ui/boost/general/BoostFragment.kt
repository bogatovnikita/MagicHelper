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
import kotlinx.coroutines.launch

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
        lifecycleScope.launch {
            viewModel.state.collect {
                renderState(it)
            }
        }
    }

    private fun renderState(state: BoostState) {
        state.ramDetails ?: return
        if (!state.loadData) return

        initProgress(state)
        setVisibilityGroup(state.boostStatus)
        initOptimizeButton(state.boostStatus)
    }

    private fun initProgress(state: BoostState) {
        with(binding) {
            percentTv.text =
                requireContext().getString(R.string.percent_D, state.ramDetails!!.usagePercents)
            progressBar.progress = state.ramDetails.usagePercents
            occupiedTotalTv.text = requireContext().getString(
                R.string._F_gb_F_gb,
                state.ramDetails.usedRam,
                state.ramDetails.totalRam
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
                if (isOptimize) R.drawable.background_button_not_danger
                else R.drawable.background_button_danger
            )
        }
    }

    private fun initClickListener() {
        binding.arrowBackIv.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.boostButton.setOnClickListener {
            findNavController().navigate(
                R.id.action_boostFragment_to_boostProgressFragment
            )
        }
    }

    companion object {
        const val BOOST_PERCENT = "BOOST_PERCENT"
        const val BOOST_CLEAR_VALUE = "BOOST_CLEAR_VALUE"
    }
}