package ar.cleaner.first.pf.ui.result

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import ar.cleaner.first.pf.R
import ar.cleaner.first.pf.databinding.FragmentResultBinding
import ar.cleaner.first.pf.domain.models.CleanerDetails
import ar.cleaner.first.pf.domain.models.details.BatteryDetails
import ar.cleaner.first.pf.domain.models.details.TemperatureDetails
import ar.cleaner.first.pf.domain.models.details.RamDetails
import ar.cleaner.first.pf.extensions.fragmentLifecycleScope
import ar.cleaner.first.pf.extensions.observeWhenResumed
import ar.cleaner.first.pf.models.MenuHorizontalItems
import ar.cleaner.first.pf.ui.battery.BatteryFragment.Companion.BATTERY_REMAINING_TIME_HOUR
import ar.cleaner.first.pf.ui.battery.BatteryFragment.Companion.BATTERY_REMAINING_TIME_MINUTE
import ar.cleaner.first.pf.ui.boost.BoostFragment
import ar.cleaner.first.pf.ui.result.ResultAdapter.Companion.BATTERY_KEY
import ar.cleaner.first.pf.ui.result.ResultAdapter.Companion.BOOST_KEY
import ar.cleaner.first.pf.ui.result.ResultAdapter.Companion.CLEANING_KEY
import ar.cleaner.first.pf.ui.result.ResultAdapter.Companion.TEMPERATURE_KEY
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlin.random.Random

@AndroidEntryPoint
class ResultFragment : Fragment(R.layout.fragment_result) {

    private val binding: FragmentResultBinding by viewBinding()

    private val viewModel: ResultViewModel by activityViewModels()

    private val args by navArgs<ResultFragmentArgs>()

    private lateinit var adapter: ResultAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkArgs()
        initClick()
        initObserver()
        initRecyclerView()
    }

    private fun checkArgs() {
        when (args.value) {
            BATTERY_KEY -> {
                viewModel.initBatteryDetails()
                binding.titleTv.text = requireContext().getString(R.string.battery_power)
            }
            BOOST_KEY -> {
                viewModel.initRamDetails()
                binding.titleTv.text = requireContext().getString(R.string.boost_title_name)
            }
        }
    }

    private fun initClick() {
        binding.arrowBackIv.setOnClickListener {
            findNavController().popBackStack(R.id.menuFragment, false)
        }
    }

    private fun initObserver() {
        viewModel.state.observeWhenResumed(lifecycleScope = fragmentLifecycleScope, ::renderState)
        viewModel.state.observeWhenResumed(lifecycleScope = fragmentLifecycleScope) { state ->
            renderRecyclerView(state)
        }
    }

    private fun renderState(screenState: ResultState) {
        with(screenState) {
            batteryDetails.render()
            ramDetails.render()
            cleanerDetails.render()
        }
    }

    private fun BatteryDetails?.render() {
        this ?: return
        val batteryRemainingTimeHour = -1
        var batteryRemainingTimeMinute = -1
        var optimizeHour = batteryRemainingTimeHour - batteryRemainingTime.hour
        var optimizeMinute = batteryRemainingTimeMinute - batteryRemainingTime.minute
        if (batteryRemainingTimeMinute < 1) batteryRemainingTimeMinute = 1
        var percent = try {
            ((batteryRemainingTimeHour * 3600 + batteryRemainingTimeMinute * 60) / (batteryRemainingTime.hour * 3600 + batteryRemainingTime.minute * 60)) * 100
        } catch (e: Exception) {
            1
        }
        if (percent > 50) percent = Random.nextInt(5, 15)
        if (optimizeHour < 0) optimizeHour = 0
        if (optimizeMinute <= 0) optimizeMinute = 1
        with(binding) {
            firstDescriptionTv.text =
                getString(R.string.battery_power_optimized_D_D, optimizeHour, optimizeMinute)
            secondDescriptionTv.text = getString(R.string.working_time_increased_by_D, percent)
            thirdDescriptionTv.text = getString(
                R.string.remaining_charging_time_D_h_D_min,
                batteryRemainingTime.hour,
                batteryRemainingTime.minute
            )
        }
    }

    private fun RamDetails?.render() {
        this ?: return
        var percentOptimized = -1
        var optimizeValue = -1
        if (percentOptimized <= 0) percentOptimized = 1
        if (optimizeValue <= 0) optimizeValue = 0.1f.toInt()
        with(binding) {
            firstDescriptionTv.text = getString(R.string.released_F_gb, optimizeValue)
            secondDescriptionTv.text =
                getString(R.string.boost_now_the_device_is_accelerated_by_D, percentOptimized)
            thirdDescriptionTv.text =
                getString(R.string.available_memory_F_gb_F_gb, usedRam, totalRam)
        }
    }

    private fun CleanerDetails?.render() {
        this ?: return
        val junkSize = -1000
        with(binding) {
            firstDescriptionTv.text = getString(R.string.released_D_gb, junkSize)
            secondDescriptionTv.text =
                getString(R.string.now_the_devices_memory_is_D_free, 100 - usedPercents)
            thirdDescriptionTv.text =
                getString(
                    R.string.available_memory_F_gb_F_gb,
                    usedMemorySize - junkSize.toDouble() / 1024,
                    totalSize
                )
        }
    }

    private fun initRecyclerView() {
        adapter = ResultAdapter(object : ResultAdapter.Listener {
            override fun onChooseMenu(item: MenuHorizontalItems) {
                when (item.id) {
                    BATTERY_KEY -> {
                        findNavController().navigate(R.id.action_to_batteryFragment)
                    }
                    BOOST_KEY -> {
                        findNavController().navigate(R.id.action_to_boostFragment)
                    }
                    TEMPERATURE_KEY -> {
                        findNavController().navigate(R.id.action_to_temperatureFragment)
                    }
                    CLEANING_KEY -> {
                        // TODO навигация к files manager
                    }
                }
            }
        })
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter
    }

    private fun renderRecyclerView(state: ResultState) {
        adapter.submitList(state.itemsList)
    }
}