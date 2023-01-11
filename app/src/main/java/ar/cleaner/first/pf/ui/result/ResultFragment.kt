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
import ar.cleaner.first.pf.domain.models.details.CpuDetails
import ar.cleaner.first.pf.domain.models.details.RamDetails
import ar.cleaner.first.pf.extensions.fragmentLifecycleScope
import ar.cleaner.first.pf.extensions.observeWhenResumed
import ar.cleaner.first.pf.models.MenuHorizontalItems
import ar.cleaner.first.pf.ui.battery.BatteryFragment.Companion.BATTERY_REMAINING_TIME_HOUR
import ar.cleaner.first.pf.ui.battery.BatteryFragment.Companion.BATTERY_REMAINING_TIME_MINUTE
import ar.cleaner.first.pf.ui.boost.BoostFragment
import ar.cleaner.first.pf.ui.cooling.CoolingFragment
import ar.cleaner.first.pf.ui.cooling.CoolingFragment.Companion.APP_PREFERENCES
import ar.cleaner.first.pf.ui.junk.JunkFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlin.random.Random

@AndroidEntryPoint
class ResultFragment : Fragment() {

    private var _binding: FragmentResultBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ResultViewModel by activityViewModels()

    private val args by navArgs<ResultFragmentArgs>()

    private lateinit var preferences: SharedPreferences

    private lateinit var adapter: ResultAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentResultBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        preferences = requireContext().getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE)
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
                binding.titleTv.text = requireContext().getString(R.string.boost)
            }
            COOLING_KEY -> {
                viewModel.initCpuDetails()
                binding.titleTv.text = requireContext().getString(R.string.cooling_cpu)

            }
            CLEANING_KEY -> {
                viewModel.initCleanerDetails()
                binding.titleTv.text = requireContext().getString(R.string.clear_junk)
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
            cpuDetails.render()
            cleanerDetails.render()
        }
    }

    private fun BatteryDetails?.render() {
        this ?: return
        val batteryRemainingTimeHour = preferences.getInt(BATTERY_REMAINING_TIME_HOUR, 0)
        var batteryRemainingTimeMinute = preferences.getInt(BATTERY_REMAINING_TIME_MINUTE, 0)
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
        var percentOptimized = preferences.getInt(BoostFragment.BOOST_PERCENT, 0) - usagePercents
        var optimizeValue =
            preferences.getFloat(BoostFragment.BOOST_CLEAR_VALUE, 0f) - usedRam.toFloat()
        if (percentOptimized <= 0) percentOptimized = 1
        if (optimizeValue <= 0) optimizeValue = 0.1f
        with(binding) {
            firstDescriptionTv.text = getString(R.string.released_F_gb, optimizeValue)
            secondDescriptionTv.text =
                getString(R.string.now_the_device_is_accelerated_by_D, percentOptimized)
            thirdDescriptionTv.text =
                getString(R.string.available_memory_F_gb_F_gb, usedRam, totalRam)
        }
    }

    private fun CpuDetails?.render() {
        this ?: return
        var cooledTemp =
            preferences.getInt(CoolingFragment.COOLER_TEMPERATURE, 0) - temperature.toInt()
        if (cooledTemp <= 0) cooledTemp = 1
        with(binding) {
            secondDescriptionTv.text =
                getString(R.string.the_normal_temperature_of_the_processor_is_25_30)
            thirdDescriptionTv.text =
                getString(R.string.processor_temperature_D, temperature.toInt())
            firstDescriptionTv.text = getString(R.string.cooled_D, cooledTemp)
        }
    }

    private fun CleanerDetails?.render() {
        this ?: return
        val junkSize = preferences.getInt(JunkFragment.JUNK_SIZE, 0)
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
                        findNavController().navigate(ResultFragmentDirections.actionResultFragmentToBatteryFragment())
                    }
                    BOOST_KEY -> {
                        findNavController().navigate(ResultFragmentDirections.actionResultFragmentToBoostFragment())
                    }
                    COOLING_KEY -> {
                        findNavController().navigate(ResultFragmentDirections.actionResultFragmentToCoolingFragment())
                    }
                    CLEANING_KEY -> {
                        findNavController().navigate(ResultFragmentDirections.actionResultFragmentToJunkFragment())
                    }
                }
            }
        })
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter
    }

    private fun renderRecyclerView(state: ResultState) {
        val list = state.itemsList.filterNot { it.id == args.value }
        adapter.submitList(list)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        const val BATTERY_KEY = 1
        const val BOOST_KEY = 2
        const val COOLING_KEY = 3
        const val CLEANING_KEY = 4
    }
}