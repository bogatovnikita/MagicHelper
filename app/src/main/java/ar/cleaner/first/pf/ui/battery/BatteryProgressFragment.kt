package ar.cleaner.first.pf.ui.battery

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import ar.cleaner.first.pf.R
import ar.cleaner.first.pf.ads.appPreloadAd
import ar.cleaner.first.pf.ads.appShowAds
import ar.cleaner.first.pf.databinding.FragmentProgressBinding
import ar.cleaner.first.pf.domain.models.BatteryMode
import ar.cleaner.first.pf.domain.usecases.battery.BatteryOptimizationUseCase
import ar.cleaner.first.pf.ui.cooling.CoolingFragment
import ar.cleaner.first.pf.ui.progress.ActionsAdapter
import ar.cleaner.first.pf.ui.result.ResultFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@AndroidEntryPoint
class BatteryProgressFragment : Fragment() {

    private var _binding: FragmentProgressBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var batteryOptimizationUseCase: BatteryOptimizationUseCase
    private lateinit var preferences: SharedPreferences

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
        appPreloadAd()
        initArguments()
    }

    private fun initArguments() {
        val actions = resources.getStringArray(R.array.battery_items).toList()
        var actionsNormal: List<String> = emptyList()
        var actionsMedium: List<String> = emptyList()
        preferences = requireContext().getSharedPreferences(
            CoolingFragment.APP_PREFERENCES,
            Context.MODE_PRIVATE
        )
        when (preferences.getString(BatteryFragment.BATTERY_MODE, BatteryMode.NORMAL.name)) {
            BatteryMode.NORMAL.name -> {
                updateBatteryOptimizationUseCase(BatteryMode.NORMAL)
                actionsNormal = actions.subList(0, 2)
                initRecyclerView(actionsNormal)
            }
            BatteryMode.MEDIUM.name -> {
                updateBatteryOptimizationUseCase(BatteryMode.MEDIUM)
                actionsMedium = actions.subList(0, 4)
                initRecyclerView(actionsMedium)
            }
            BatteryMode.HIGH.name -> {
                updateBatteryOptimizationUseCase(BatteryMode.HIGH)
                initRecyclerView(actions)
            }
        }
    }

    private fun updateBatteryOptimizationUseCase(batteryMode: BatteryMode) {
        lifecycleScope.launch {
            batteryOptimizationUseCase.invoke(batteryMode).collect {}
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
            findNavController().navigate(
                BatteryProgressFragmentDirections.actionBatteryProgressFragmentToResultFragment(
                    ResultFragment.BATTERY_KEY
                )
            )
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}