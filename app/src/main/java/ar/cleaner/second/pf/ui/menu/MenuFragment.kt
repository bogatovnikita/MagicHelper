package ar.cleaner.second.pf.ui.menu

import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavOptions
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
            batteryAnim.isVisible = screenState.isChargingNow
            batteryIconIv.isVisible = !screenState.isChargingNow
            if (!screenState.isChargingNow) {
                when (screenState.batteryCharge) {
                    in 0..20 -> batteryIconIv.setImageResource(R.drawable.ic_battery_20_percent)
                    in 21..40 -> batteryIconIv.setImageResource(R.drawable.ic_battery_40_percent)
                    in 41..60 -> batteryIconIv.setImageResource(R.drawable.ic_battery_60_percent)
                    in 61..80 -> batteryIconIv.setImageResource(R.drawable.ic_battery_80_percent)
                    in 81..100 -> batteryIconIv.setImageResource(R.drawable.ic_battery_100_percent)
                }
            }

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
                val navOptions = navOptions()

                findNavController().navigate(R.id.files_and_apps_graph, null, navOptions)
            }

        }
    }

    private fun navOptions(): NavOptions {
        return NavOptions.Builder()
            .setEnterAnim(R.anim.slide_in)
            .setExitAnim(R.anim.slide_out)
            .setPopEnterAnim(R.anim.slide_in)
            .setPopExitAnim(R.anim.slide_out)
            .build()
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