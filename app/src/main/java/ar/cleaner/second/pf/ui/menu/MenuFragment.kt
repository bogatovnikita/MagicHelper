package ar.cleaner.second.pf.ui.menu

import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ar.cleaner.second.pf.R
import ar.cleaner.second.pf.databinding.FragmentMenuBinding
import ar.cleaner.second.pf.extensions.fragmentLifecycleScope
import ar.cleaner.second.pf.extensions.observeWhenResumed
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MenuFragment : Fragment(R.layout.fragment_menu) {

    private val binding: FragmentMenuBinding by viewBinding()

    private val viewModel: MenuViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setColorStatusBar()
        initClick()
        initObserver()
    }

    override fun onResume() {
        super.onResume()
        viewModel.updateAllInfo()
    }

    private fun setColorStatusBar() {
        requireActivity().window.statusBarColor =
            ContextCompat.getColor(requireActivity(), R.color.dark_blue)
        requireActivity().window.decorView.systemUiVisibility = 0
    }

    private fun initObserver() {
        viewModel.state.observeWhenResumed(
            lifecycleScope = fragmentLifecycleScope,
            ::renderState
        )
    }

    private fun renderState(screenState: MenuState) {
        renderRamState(screenState)
        renderMemoryState(screenState)
        renderBatteryState(screenState)
        renderTemperatureState(screenState)
    }

    private fun renderTemperatureState(screenState: MenuState) = screenState.apply {
        with(binding) {
            coolingDescriptionTv.text =
                getString(if (isTemperatureChecked) R.string.done else R.string.temperature_desc_not_optimized)
            coolingDescriptionTv.setColor(isTemperatureChecked)
        }
    }

    private fun renderRamState(screenState: MenuState) = screenState.apply {
        with(binding) {
            ramProgressBar.progressPercent = usageRamPercents
            ramPercentTv.text = getString(R.string.percent_D, usageRamPercents.toInt())
            descriptionRamTv.text = getString(R.string._F_gb_F_gb, usedRam, totalRam)
            boostDescriptionTv.setColor(isRamOptimized)
            boostDescriptionTv.text =
                getString(if (isRamOptimized) R.string.done else R.string.boost_need_atantion)
        }
    }

    private fun renderMemoryState(screenState: MenuState) = screenState.apply {
        with(binding) {
            storageProgressBar.progressPercent = usageMemoryPercents.toFloat()
            storagePercentTv.text = getString(R.string.percent_D, usageMemoryPercents)
            descriptionStorageTv.text =
                getString(R.string._F_gb_F_gb, usedMemorySize, totalSize)
            cleanDescriptionTv.text = getString(
                if (isMemoryOptimized) R.string.done else R.string.file_manager_cleaning_is_required
            )
            cleanDescriptionTv.setColor(isMemoryOptimized)
        }
    }

    private fun renderBatteryState(screenState: MenuState) = screenState.apply {
        with(binding) {
            if (isNeedShowTimeToFullCharge && screenState.batteryCharge != 100) {
                if (timeToFullCharge == 0 to 0) {
                    batteryDescriptionTv.text = paintEndOfTheString()
                } else {
                    batteryDescriptionTv.text = getString(
                        R.string.battery_time_to_full_charge_D_D,
                        timeToFullCharge.first,
                        timeToFullCharge.second
                    )
                }
            } else {
                batteryDescriptionTv.text =
                    getString(R.string.battery_power_description_D, batteryCharge)
            }
        }
    }

    private fun initClick() {
        with(binding) {
            backgroundBatteryTransparent.setOnClickListener {
                findNavController().navigate(R.id.action_to_batteryFragment)
            }
            backgroundBoostTransparent.setOnClickListener {
                findNavController().navigate(R.id.action_to_boostFragment)
            }
            backgroundCoolingTransparent.setOnClickListener {
                findNavController().navigate(R.id.action_to_temperatureFragment)
            }
            backgroundCleanTransparent.setOnClickListener {
                findNavController().navigate(R.id.files_and_apps_graph)
            }

        }
    }

    private fun TextView.setColor(isOptimized: Boolean) {
        setTextColor(
            ContextCompat.getColor(
                requireContext(),
                if (isOptimized) R.color.green else R.color.red
            )
        )
    }

    private fun paintEndOfTheString(): Spannable {
        val text = getString(R.string.battery_time_to_full_charge_calculating)
        val outPutColoredText: Spannable = SpannableString(text)
        outPutColoredText.setSpan(
            ForegroundColorSpan(resources.getColor(R.color.green)), 18, text.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        return outPutColoredText
    }

}