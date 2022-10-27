package ar.cleaner.first.pf.ui.result

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
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
import ar.cleaner.first.pf.ui.cooling.CoolingFragment
import ar.cleaner.first.pf.ui.cooling.CoolingFragment.Companion.APP_PREFERENCES
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ResultFragment : Fragment() {

    private var _binding: FragmentResultBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ResultViewModel by viewModels()

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
        checkArgs()
        initClick()
        initObserver()
        initRecyclerView()
    }

    private fun checkArgs() {
        when (args.value) {
            1 -> {
                viewModel.initRamDetails()
                binding.titleTv.text = requireContext().getString(R.string.boost)
            }
            2 -> {
                viewModel.initBatteryDetails()
                binding.titleTv.text = requireContext().getString(R.string.battery_power)

            }
            3 -> {
                viewModel.initCpuDetails()
                binding.titleTv.text = requireContext().getString(R.string.cooling_cpu)

            }
            4 -> {
                viewModel.initCleanerDetails()
                binding.titleTv.text = requireContext().getString(R.string.clear_junk)
            }
        }
    }

    private fun initClick() {
        binding.arrowBackIv.setOnClickListener {
            findNavController().navigate(ResultFragmentDirections.actionResultFragmentToMenuFragment())
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
    }

    private fun RamDetails?.render() {
        this ?: return
    }

    private fun CpuDetails?.render() {
        this ?: return
        preferences = requireContext().getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE)
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
    }

    private fun initRecyclerView() {
        adapter = ResultAdapter(object : ResultAdapter.Listener {
            override fun onChooseMenu(item: MenuHorizontalItems) {
                when (item.id) {
                    1 -> {
                        Toast.makeText(requireContext(), item.title, Toast.LENGTH_SHORT).show()
                    }
                    2 -> {
                        Toast.makeText(requireContext(), item.title, Toast.LENGTH_SHORT).show()
                    }
                    3 -> {
                        Toast.makeText(requireContext(), item.title, Toast.LENGTH_SHORT).show()
                    }
                    4 -> {
                        Toast.makeText(requireContext(), item.title, Toast.LENGTH_SHORT).show()
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
}