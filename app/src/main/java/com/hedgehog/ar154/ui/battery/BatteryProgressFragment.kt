package com.hedgehog.ar154.ui.battery

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.hedgehog.ar154.R
import com.hedgehog.ar154.data.BatteryMode
import com.hedgehog.ar154.data.OptimizationTypes
import com.hedgehog.ar154.databinding.FragmentProgressBinding
import com.hedgehog.ar154.ui.ads.showAds
import com.hedgehog.ar154.ui.progress.ActionsAdapter
import com.hedgehog.ar154.utils.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.concurrent.TimeUnit

class BatteryProgressFragment(
    private val onComplete: Fragment.(
        menuItem: MenuItems,
        data: Any?,
        simpleData: String?
    ) -> Unit
) : Fragment(R.layout.fragment_progress) {

    private val binding: FragmentProgressBinding by viewBinding()

    private lateinit var type: OptimizationTypes
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        type = OptimizationTypes.valueOf(
            requireArguments().getString(OPTIMIZATION_TYPE)!!
        )
        binding.tvOptimization.text = getString(MenuItems.power.title)
        powering()
    }

    private fun powering() {
        Utils.setScreenBrightness(5)
        val strings = resources.getStringArray(R.array.battery).toList()
        val items = when (type) {
            OptimizationTypes.BatteryLow -> strings.subList(0, 2)
            OptimizationTypes.BatteryMedium -> strings.subList(0, 4)
            else -> strings
        }
        stringActions(items)
    }

    private fun stringActions(items: List<String>) {
        val adapter = ActionsAdapter(items.toList())
        binding.rvActions.apply {
            layoutManager = LinearLayoutManager(requireContext())
            this.adapter = adapter
        }

        val repeat = items.size
        startPercents()
        lifecycleScope.launch(Dispatchers.Default) {
            repeat(items.size) {
                delay(TimeUnit.SECONDS.toMillis(8) / repeat)
                withContext(Dispatchers.Main) {
                    adapter.removeFirst()
                }
            }
            withContext(Dispatchers.Main) {
                binding.apply {
                    showAds(tvOptimization,
                        tvPercents,
                        ivDone){
                        goNext()
                    }
                }

            }
        }
    }

    private fun goNext(){
        val simpleData = when (type) {
            OptimizationTypes.BatteryLow -> { batteryLow() }
            OptimizationTypes.BatteryMedium -> { batteryMedium() }
            OptimizationTypes.BatteryHigh -> { batteryHigh() }
            else -> {""}
        }
        onComplete(MenuItems.power, null, simpleData)
    }

    private fun batteryLow(): String {
        val simpleData1 = NativeProvider.calculateWorkingMinutes(
            requireContext(),
            BatteryChargeReceiver.getInfo().value ?: 0
        ).toString()
        NativeProvider.powerLow(requireContext())
        PreferencesProvider.saveBatteryType(BatteryMode.low.name)
        return simpleData1
    }
    private fun batteryHigh(): String {
        val simpleData1 = NativeProvider.calculateWorkingMinutes(
            requireContext(),
            BatteryChargeReceiver.getInfo().value ?: 0
        ).toString()
        NativeProvider.powerHigh(requireContext())
        PreferencesProvider.saveBatteryType(BatteryMode.high.name)
        return simpleData1
    }

    private fun batteryMedium(): String {
        val simpleData1 = NativeProvider.calculateWorkingMinutes(
            requireContext(),
            BatteryChargeReceiver.getInfo().value ?: 0
        ).toString()
        NativeProvider.powerMedium(requireContext())
        PreferencesProvider.saveBatteryType(BatteryMode.medium.name)
        return simpleData1
    }

    private fun startPercents() {
        lifecycleScope.launch(Dispatchers.Default) {
            val step = 7000 / 100
            for (i in 0 .. 100) {
                withContext(Dispatchers.Main) {
                    binding.tvPercents.text = getString(R.string.d_percents, i)
                }
                delay(step.toLong())
            }
        }
    }

    companion object{
        const val OPTIMIZATION_TYPE = "optimization_type"
    }


}