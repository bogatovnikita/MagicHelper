package ar.cleaner.first.pf.ui.cpu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import ar.cleaner.first.pf.R
import ar.cleaner.first.pf.databinding.FragmentCoolingBinding
import ar.cleaner.first.pf.utils.BatInfoReceiver
import ar.cleaner.first.pf.utils.MenuItems
import ar.cleaner.first.pf.utils.OptimizationProvider

class CpuFragment(
    private val onOptimizeClick: Fragment.() -> Unit
) : Fragment() {

    private var _binding: FragmentCoolingBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCoolingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initClickListener()
        checkOptimized()
    }

    private fun checkOptimized() {
        if (OptimizationProvider.checkIsOptimized(MenuItems.CoolingCpu)) isOptimized() else isNotOptimized()
    }

    private fun isNotOptimized() {
        with(binding) {
            dangerButton.apply {
                background =
                    ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.background_button_danger
                    )
                text = getString(R.string.the_processor_overheated_cooling_is_required)
                setTextColor(ContextCompat.getColor(requireContext(), R.color.red))
            }
        }
    }

    private fun isOptimized() {
        with(binding) {
            groupOptimizeIsDone.visibility = View.VISIBLE
            groupIsNotOptimize.visibility = View.GONE
            dangerButton.apply {
                background =
                    ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.background_button_not_danger
                    )
                text = getString(R.string.the_phone_does_not_need_cooling_at_the_moment)
                setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
            }
        }
    }

    private fun initView() {
        BatInfoReceiver.getBatteryInfo().observe(viewLifecycleOwner) {
            binding.percentTv.text = getString(R.string.temperature_D, it)
        }
    }

    private fun initClickListener() {
        binding.arrowBackIv.setOnClickListener { requireActivity().onBackPressed() }
        binding.boostButton.setOnClickListener { onOptimizeClick() }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}