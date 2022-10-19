package ar.cleaner.first.pf.ui.battery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import ar.cleaner.first.pf.R
import ar.cleaner.first.pf.data.BatteryMode
import ar.cleaner.first.pf.data.OptimizationTypes
import ar.cleaner.first.pf.databinding.FragmentProgressBinding
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

    private var scanIsDone = false

    override fun onResume() {
        super.onResume()
        if (scanIsDone) scanIsDone()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProgressBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListScan()
    }

    private fun initListScan() {
        type = OptimizationTypes.valueOf(
            requireArguments().getString(OPTIMIZATION_TYPE)!!
        )
        val strings = resources.getStringArray(R.array.battery_items).toList()
        val items = when (type) {
            OptimizationTypes.BatteryLow -> {
                strings.subList(0, 2)
            }
            OptimizationTypes.BatteryMedium -> {
                strings.subList(0, 4)
            }
            else -> strings
        }
        stringAction(items)
    }

    private fun stringAction(items: List<String>) {
        val adapter = ActionsAdapter(items)
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            this.adapter = adapter
        }
        Utils.turnOffBluetooth()
        WifiSwitch(requireContext()).disable()
        val repeat = items.size
        lifecycleScope.launch(Dispatchers.Default) {
            repeat(items.size) {
                delay(TimeUnit.SECONDS.toMillis(8) / repeat)
                withContext(Dispatchers.Main) {
                    adapter.removeFirst()
                }
            }
            scanIsDone = true
            if (scanIsDone) scanIsDone()
        }
    }

    private fun scanIsDone() {
        lifecycleScope.launch(Dispatchers.Default) {
            withContext(Dispatchers.Main) {
                delay(500)
                binding.recyclerView.visibility = View.GONE
                binding.isDoneTv.visibility = View.VISIBLE
                delay(1000)
                binding.apply {
//                    showAds() {
                    goNext()
//                    }
                }
            }
        }
    }

    private fun goNext() {
        val simpleData = when (type) {
            OptimizationTypes.BatteryLow -> {
                batteryLow()
            }
            OptimizationTypes.BatteryMedium -> {
                batteryMedium()
            }
            OptimizationTypes.BatteryHigh -> {
                batteryHigh()
            }
        }
        onComplete(MenuItems.BatteryPower, null, simpleData)
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

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        const val OPTIMIZATION_TYPE = "optimization_type"
    }

}