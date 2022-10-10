package ar.cleaner.first.pf.ui.battery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import ar.cleaner.first.pf.R
import ar.cleaner.first.pf.data.BatteryMode
import ar.cleaner.first.pf.data.OptimizationTypes
import ar.cleaner.first.pf.databinding.FragmentProgressBinding
import ar.cleaner.first.pf.ui.ads.showAds
import ar.cleaner.first.pf.ui.progress.ActionsAdapter
import ar.cleaner.first.pf.utils.*
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

    private var _binding: FragmentProgressBinding? = null
    private val binding get() = _binding!!

    private lateinit var type: OptimizationTypes

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProgressBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        type = OptimizationTypes.valueOf(
            requireArguments().getString(OPTIMIZATION_TYPE)!!
        )
//        binding.tvOptimization.text = getString(MenuItems.power.title)
        powering()
    }

    private fun powering() {
        Utils.setScreenBrightness(5)
//        val strings = resources.getStringArray(R.array.battery).toList()
//        val items = when (type) {
//            OptimizationTypes.BatteryLow -> strings.subList(0, 2)
//            OptimizationTypes.BatteryMedium -> strings.subList(0, 4)
//            else -> strings
//        }
//        stringActions(items)
    }

    private fun stringActions(items: List<String>) {
        val adapter = ActionsAdapter(items.toList())
//        binding.rvActions.apply {
//            layoutManager = LinearLayoutManager(requireContext())
//            this.adapter = adapter
//        }

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
                    showAds() {
                        goNext()
                    }
                }

            }
        }
    }

    private fun goNext() {
//        val simpleData = when (type) {
//            OptimizationTypes.BatteryLow -> {
//                batteryLow()
//            }
//            OptimizationTypes.BatteryMedium -> {
//                batteryMedium()
//            }
//            OptimizationTypes.BatteryHigh -> {
//                batteryHigh()
//            }
//            else -> {
//                ""
//            }
//        }
//        onComplete(MenuItems.power, null, simpleData)
    }

    private fun batteryLow(): String {
        val simpleData1 =
            NativeProvider.calculateWorkingMinutes(
                requireContext(),
                BatteryChargeReceiver.getInfo().value ?: 0
            ).toString()
        NativeProvider.powerLow(requireContext())
        PreferencesProvider.saveBatteryType(BatteryMode.low.name)
        return simpleData1
    }

    private fun batteryHigh(): String {
        val simpleData1 =
            NativeProvider.calculateWorkingMinutes(
                requireContext(),
                BatteryChargeReceiver.getInfo().value ?: 0
            ).toString()
        NativeProvider.powerHigh(requireContext())
        PreferencesProvider.saveBatteryType(BatteryMode.high.name)
        return simpleData1
    }

    private fun batteryMedium(): String {
        val simpleData1 =
            NativeProvider.calculateWorkingMinutes(
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
            for (i in 0..100) {
                withContext(Dispatchers.Main) {
//                    binding.tvPercents.text = getString(R.string.d_percents, i)
                }
                delay(step.toLong())
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        const val OPTIMIZATION_TYPE = "optimization_type"
    }

}