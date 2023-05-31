package ar.cleaner.second.pf.ui.battery

import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import ar.cleaner.first.pf.domain.models.BatteryMode
import ar.cleaner.first.pf.domain.usecases.battery.BatteryOptimizationUseCase
import ar.cleaner.second.pf.R
import ar.cleaner.second.pf.ads.appShowAds
import ar.cleaner.second.pf.databinding.FragmentProgressBinding
import ar.cleaner.second.pf.ui.base_fragment.BaseFragment
import ar.cleaner.second.pf.ui.progress.ActionsAdapter
import by.kirich1409.viewbindingdelegate.viewBinding
import com.yin_kio.ads.preloadAd
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@AndroidEntryPoint
class BatteryProgressFragment : BaseFragment(R.layout.fragment_progress) {

    private val binding: FragmentProgressBinding by viewBinding()

    @Inject
    lateinit var batteryOptimizationUseCase: BatteryOptimizationUseCase

    private var scanIsDone = false

    override fun onResume() {
        super.onResume()
        if (scanIsDone) scanIsDone()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        preloadAd()
        initArguments()
    }

    private fun initArguments() {
        val actions = resources.getStringArray(R.array.battery_items).toList()
        val actionsNormal: List<String>
        val actionsMedium: List<String>
        when (batteryOptimizationUseCase.getOptimizationMode()) {
            BatteryMode.NORMAL -> {
                updateBatteryOptimizationUseCase(BatteryMode.NORMAL)
                actionsNormal = actions.subList(0, 2)
                initRecyclerView(actionsNormal)
            }
            BatteryMode.MEDIUM -> {
                updateBatteryOptimizationUseCase(BatteryMode.MEDIUM)
                actionsMedium = actions.subList(0, 4)
                initRecyclerView(actionsMedium)
            }
            BatteryMode.HIGH -> {
                updateBatteryOptimizationUseCase(BatteryMode.HIGH)
                initRecyclerView(actions)
            }
        }
    }

    private fun updateBatteryOptimizationUseCase(batteryMode: BatteryMode) {
        lifecycleScope.launch {
            batteryOptimizationUseCase.startOptimization(batteryMode).collect {

            }
        }
    }

    private fun initRecyclerView(actions: List<String>) {
        val adapter = ActionsAdapter(actions)
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            this.adapter = adapter
        }
        val repeat = actions.size
        lifecycleScope.launch(Dispatchers.Default) {
            repeat(repeat) {
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
                withContext(Dispatchers.Main) {
                    binding.recyclerView.visibility = View.GONE
                    binding.isDoneTv.visibility = View.VISIBLE
                }
                delay(1000)
                withContext(Dispatchers.Main) { goScreenResult() }
            }
        }
    }

    private fun goScreenResult() {
        appShowAds {
            findNavController().navigate( R.id.action_to_batteryResultFragment)
        }
    }

}