package ar.cleaner.first.pf.ui.result

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import ar.cleaner.first.pf.R
import ar.cleaner.first.pf.databinding.FragmentResultBinding
import ar.cleaner.first.pf.models.MenuHorizontalItems
import ar.cleaner.first.pf.ui.result.ResultAdapter.Companion.BATTERY_KEY
import ar.cleaner.first.pf.ui.result.ResultAdapter.Companion.BOOST_KEY
import ar.cleaner.first.pf.ui.result.ResultAdapter.Companion.CLEANING_KEY
import ar.cleaner.first.pf.ui.result.ResultAdapter.Companion.TEMPERATURE_KEY
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint

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

    private fun initRecyclerView() {
        adapter = ResultAdapter(object : ResultAdapter.Listener {
            override fun onChooseMenu(item: MenuHorizontalItems) {
                when (item.type) {
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