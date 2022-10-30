package ar.cleaner.first.pf.ui.menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
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
        viewModel.initAllUseCase()
        initClick()
        initObserver()
    }

    private fun setColorStatusBar() {
        requireActivity().window.statusBarColor =
            ContextCompat.getColor(requireActivity(), R.color.dark_blue)
        requireActivity().window.decorView.systemUiVisibility = 0
    }

    private fun initClick() {
        with(binding) {
            backgroundBatteryTransparent.setOnClickListener {
                findNavController().navigate(MenuFragmentDirections.actionMenuFragmentToBatteryFragment())
            }
            backgroundBoostTransparent.setOnClickListener {
                findNavController().navigate(MenuFragmentDirections.actionMenuFragmentToBoostFragment())
            }
            backgroundCoolingTransparent.setOnClickListener {
                findNavController().navigate(MenuFragmentDirections.actionMenuFragmentToCoolingFragment())
            }
            backgroundCleanTransparent.setOnClickListener {
                findNavController().navigate(MenuFragmentDirections.actionMenuFragmentToJunkFragment())
            }

        }
    }

    private fun initObserver() {
        viewModel.state.observeWhenResumed(
            lifecycleScope = fragmentLifecycleScope,
            ::renderState
        )
    }

    private fun renderState(screenState: MenuState) {
        with(screenState) {
            batteryDetails.render()
            ramDetails.render()
            cpuDetails.render()
            cleanerDetails.render()
        }
    }

    private fun BatteryDetails?.render() {
        this ?: return
        binding.batteryDescriptionTv.text = getString(
            R.string.battery_power_description_D_D,
            batteryRemainingTime.hour,
            batteryRemainingTime.minute
        )

    }

    private fun RamDetails?.render() {
        this ?: return
        with(binding) {
            ramProgressBar.progressPercent = usagePercents.toFloat()
            ramPercentTv.text = getString(R.string.percent_D, usagePercents)
            descriptionRamTv.text = getString(R.string._F_gb_F_gb, usedRam, totalRam)
            if (isOptimized) {
                boostDescriptionTv.text = getString(R.string.boost_description)
                boostDescriptionTv.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.green
                    )
                )
            } else {
                boostDescriptionTv.text = getString(R.string.boost_description_not_optimize)
                boostDescriptionTv.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.red
                    )
                )
            }
        }
    }

    private fun CpuDetails?.render() {
        this ?: return
        with(binding) {
            if (isOptimized) {
                coolingDescriptionTv.text = getString(R.string.clean_junk_done)
                coolingDescriptionTv.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.green
                    )
                )
            } else {
                coolingDescriptionTv.text =
                    getString(R.string.cooling_cpu_description_not_optimized)
                coolingDescriptionTv.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.red
                    )
                )
            }
        }
    }

    private fun CleanerDetails?.render() {
        this ?: return
        with(binding) {
            storageProgressBar.progressPercent = usedPercents.toFloat()
            storagePercentTv.text = getString(R.string.percent_D, usedPercents)
            descriptionStorageTv.text = getString(R.string._F_gb_F_gb, usedMemorySize, totalSize)
            if (isOptimized) {
                cleanDescriptionTv.text = getString(R.string.clean_junk_done)
                cleanDescriptionTv.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.green
                    )
                )
            } else {
                cleanDescriptionTv.text = getString(
                    R.string.clean_junk_description_not_optimized_D,
                    trashSize
                )
                cleanDescriptionTv.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.red
                    )
                )
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}