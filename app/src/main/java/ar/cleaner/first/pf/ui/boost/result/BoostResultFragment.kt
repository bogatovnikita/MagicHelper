package ar.cleaner.first.pf.ui.boost.result

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import ar.cleaner.first.pf.R
import ar.cleaner.first.pf.databinding.FragmentBoostResultBinding
import ar.cleaner.first.pf.models.MenuHorizontalItems
import ar.cleaner.first.pf.ui.result.ResultAdapter
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BoostResultFragment : Fragment(R.layout.fragment_boost_result) {

    private val binding: FragmentBoostResultBinding by viewBinding()
    private val viewModel: BoostResultViewModel by viewModels()
    private lateinit var adapter: ResultAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initClickListener()
        initRecyclerView()
        initObserver()
    }

    private fun initObserver() {
        lifecycleScope.launchWhenResumed {
            viewModel.state.collect {
                renderState(it)
            }
        }
    }

    private fun renderState(state: BoostResultState) {
        state.boostResultDetails ?: return
        if (!state.loadData) return

        initDetails(state)
        initAdapterList(state)

    }

    private fun initDetails(state: BoostResultState) {
        binding.firstDescriptionTv.text =
            getString(R.string.released_F_gb, state.boostResultDetails!!.releasedMemory)

        if (state.boostResultDetails.optimizedPercentages <= 0)
            binding.secondDescriptionTv.visibility = View.GONE

        binding.secondDescriptionTv.text =
            getString(
                R.string.boost_now_the_device_is_accelerated_by_D,
                state.boostResultDetails.optimizedPercentages
            )
        binding.thirdDescriptionTv.text =
            getString(
                R.string.available_memory_F_gb_F_gb,
                state.boostResultDetails.usedRam,
                state.boostResultDetails.totalRam
            )
    }

    private fun initAdapterList(state: BoostResultState) = adapter.submitList(state.resultList)

    private fun initClickListener() {
        binding.toolbar.binding.arrowBackIv.setOnClickListener {
            findNavController().popBackStack(R.id.menuFragment, false)
        }
    }

    private fun initRecyclerView() {
        adapter = ResultAdapter(object : ResultAdapter.Listener {
            override fun onChooseMenu(item: MenuHorizontalItems) {
                when (item.type) {
                    ResultAdapter.BATTERY_KEY -> {
                        findNavController().navigate(R.id.action_to_batteryFragment)
                    }
                    ResultAdapter.TEMPERATURE_KEY -> {
                        findNavController().navigate(R.id.action_to_temperatureFragment)
                    }
                    ResultAdapter.CLEANING_KEY -> {
                        findNavController().navigate(R.id.files_and_apps_graph)
                    }
                }
            }
        })
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter
    }
}