package ar.cleaner.first.pf.ui.battery

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import ar.cleaner.first.pf.R
import ar.cleaner.first.pf.databinding.FragmentBatteryResultBinding
import ar.cleaner.first.pf.domain.models.BatteryMode
import ar.cleaner.first.pf.domain.usecases.battery.BatteryOptimizationUseCase
import ar.cleaner.first.pf.models.MenuHorizontalItems
import ar.cleaner.first.pf.ui.result.ResultAdapter
import ar.cleaner.first.pf.ui.result.ResultListProvider
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class BatteryResultFragment : Fragment(R.layout.fragment_battery_result) {

    private val binding: FragmentBatteryResultBinding by viewBinding()

    private lateinit var adapter: ResultAdapter

    @Inject
    lateinit var batteryOptimizationUseCase: BatteryOptimizationUseCase

    @Inject
    lateinit var listProvider: ResultListProvider

    private val listResult: List<MenuHorizontalItems> by lazy {
        listProvider.getResultList(
            ResultListProvider.TYPE_BATTERY
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        renderOptimizationMode()
        binding.arrowBackIv.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun renderOptimizationMode() {
        val typeId = when(batteryOptimizationUseCase.getOptimizationMode()) {
            BatteryMode.NORMAL -> R.string.battery_normal_mode
            BatteryMode.MEDIUM -> R.string.battery_ultra_mode
            BatteryMode.HIGH -> R.string.battery_extra_mode
        }
        binding.secondDescriptionTv.text = getString(R.string.battery_switch_on_mode_S, getString(typeId))
    }

    private fun initRecyclerView() {
        adapter = ResultAdapter(object : ResultAdapter.Listener {
            override fun onChooseMenu(item: MenuHorizontalItems) {
                when (item.type) {
                    ResultAdapter.TEMPERATURE_KEY -> {
                        findNavController().navigate(R.id.action_to_temperatureFragment)
                    }
                    ResultAdapter.BOOST_KEY -> {
                        findNavController().navigate(R.id.action_to_boostFragment)
                    }
                    ResultAdapter.CLEANING_KEY -> {
                        // TODO навигация к files manager
                    }
                }
            }
        })
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter
        adapter.submitList(listResult)
    }

}