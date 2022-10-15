package ar.cleaner.first.pf.ui.boost

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import ar.cleaner.first.pf.R
import ar.cleaner.first.pf.databinding.FragmentBoostBinding
import ar.cleaner.first.pf.utils.MenuItems
import ar.cleaner.first.pf.utils.OptimizationProvider

class BoostFragment(
    private val onOptimizeClick: Fragment.() -> Unit
) : Fragment() {

    private var _binding: FragmentBoostBinding? = null
    private val binding get() = _binding!!

    private val ramUsageInfo = OptimizationProvider.getRamUsageInfo()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBoostBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.arrowBackIv.setOnClickListener { requireActivity().onBackPressed() }
        checkState()
    }

    private fun checkState() {
        binding.boostButton.setOnClickListener { onOptimizeClick() }
        if (OptimizationProvider.checkIsOptimized(MenuItems.Boost)) {
            isOptimized()
        } else {
            isNotOptimized()
        }
    }

    private fun isNotOptimized() {
        with(binding) {
            percentTv.text = getString(R.string.percent_D, ramUsageInfo.percent)
            progressBar.progress = ramUsageInfo.percent
            occupiedTotalTv.text =
                getString(R.string._F_gb_F_gb, ramUsageInfo.usageGb, ramUsageInfo.totalGb)
            dangerButton.apply {
                background =
                    ContextCompat.getDrawable(requireContext(), R.drawable.background_button_danger)
                text = getString(R.string.memory_is_overloaded_optimization_is_required)
                setTextColor(ContextCompat.getColor(requireContext(), R.color.red))
            }
        }
    }

    private fun isOptimized() {
        with(binding) {
            groupOptimizeIsDone.visibility = View.VISIBLE
            groupIsNotOptimize.visibility = View.GONE
            progressBar.progress = ramUsageInfo.percent
            occupiedTotalTv.text =
                getString(R.string._F_gb_F_gb, ramUsageInfo.usageGb, ramUsageInfo.totalGb)
            dangerButton.apply {
                background =
                    ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.background_button_not_danger
                    )
                text = getString(R.string.the_phone_does_need_any_acceleration_at_the_moment)
                setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}