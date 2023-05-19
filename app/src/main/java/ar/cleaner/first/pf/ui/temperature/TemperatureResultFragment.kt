package ar.cleaner.first.pf.ui.temperature

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import ar.cleaner.first.pf.R
import ar.cleaner.first.pf.databinding.FragmentTemperatureResultBinding
import ar.cleaner.first.pf.domain.usecases.temperature.TemperatureUseCase
import ar.cleaner.first.pf.models.MenuHorizontalItems
import ar.cleaner.first.pf.ui.result.ResultAdapter
import ar.cleaner.first.pf.ui.result.ResultListProvider
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class TemperatureResultFragment : Fragment(R.layout.fragment_temperature_result) {

    private val binding: FragmentTemperatureResultBinding by viewBinding()

    private lateinit var adapter: ResultAdapter

    @Inject
    lateinit var temperature: TemperatureUseCase

    @Inject
    lateinit var listProvider: ResultListProvider

    private val listResult: List<MenuHorizontalItems> by lazy {
        listProvider.getResultList(
            ResultListProvider.TYPE_TEMPERATURE
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        binding.percentTv.text =
            requireContext().getString(R.string.temperature_D, temperature.getTemperature())
    }

    private fun initRecyclerView() {
        adapter = ResultAdapter(object : ResultAdapter.Listener {
            override fun onChooseMenu(item: MenuHorizontalItems) {
                when (item.type) {
                    ResultAdapter.BATTERY_KEY -> {
                        findNavController().navigate(R.id.action_to_batteryFragment)
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