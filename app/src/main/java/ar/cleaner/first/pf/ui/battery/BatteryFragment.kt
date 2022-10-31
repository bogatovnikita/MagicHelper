package ar.cleaner.first.pf.ui.battery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.Group
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import ar.cleaner.first.pf.R
import ar.cleaner.first.pf.databinding.FragmentBatteryBinding
import ar.cleaner.first.pf.domain.models.BatteryMode
import ar.cleaner.first.pf.domain.models.details.BatteryDetails
import ar.cleaner.first.pf.models.ModeGroup
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class BatteryFragment : Fragment() {

    private var _binding: FragmentBatteryBinding? = null
    private val binding get() = _binding!!

    private lateinit var actionAdapter: OptimizationActionAdapter
    private val viewModel: BatteryViewModel by viewModels()
    private var value = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBatteryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.initBatteryDetails()
        initAdapter()
        initBatteryMode()
        initObserver()
    }

    private fun initAdapter() {
        actionAdapter = OptimizationActionAdapter()
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            this.adapter = actionAdapter
        }
    }

    private fun initBatteryMode() {
        val actions = resources.getStringArray(R.array.battery_items).toList()
        val listMode = mutableListOf<ModeGroup>()
        listMode.add(
            ModeGroup(
                binding.buttonNormalMode,
                R.string.normal_mode_activates_restrictions,
                actions.subList(0, 2),
                BatteryMode.NORMAL
            )
        )
        listMode.add(
            ModeGroup(
                binding.buttonUltraMode,
                R.string.ultra_mode_activates_restrictions,
                actions.subList(0, 4),
                BatteryMode.MEDIUM
            )
        )
        listMode.add(
            ModeGroup(
                binding.buttonExtraMode,
                R.string.extra_mode_activates_restrictions,
                actions,
                BatteryMode.HIGH
            )
        )
        binding.groupModes.setAllOnClickListener { mode ->
            when (mode) {
                binding.buttonNormalMode -> {
                    enableMode(listMode[0])
                    disableMode(listMode.filterNot { it == listMode[0] })
                }
                binding.buttonUltraMode -> {
                    enableMode(listMode[1])
                    disableMode(listMode.filterNot { it == listMode[1] })
                }
                binding.buttonExtraMode -> {
                    enableMode(listMode[2])
                    disableMode(listMode.filterNot { it == listMode[2] })
                }
            }
        }
        enableMode(listMode[1])
    }

    private fun enableMode(mode: ModeGroup) {
        mode.modeButton.background =
            ContextCompat.getDrawable(requireContext(), R.drawable.card_corners_green)
        actionAdapter.setItems(mode.modeAction)
        binding.descriptionModeTv.setText(mode.modeHeader)
    }

    private fun disableMode(modeList: List<ModeGroup>) {
        modeList.forEach {
            it.modeButton.background =
                ContextCompat.getDrawable(requireContext(), R.drawable.card_corners_comet)
        }
    }

    private fun initObserver() {
        lifecycleScope.launch {
            viewModel.state.collect {
                value += 1
                if (value == 1) return@collect
                renderState(it)
            }
        }
    }

    private fun renderState(batteryDetails: BatteryDetails) {
        with(binding) {
            titleTv.text = getString(R.string.battery_power)
            percentTv.text = getString(R.string.percent_D, batteryDetails.batteryCharge)
            occupiedTotalTv.text = getString(
                R.string.battery_power_only_time_D_D,
                batteryDetails.batteryRemainingTime.hour,
                batteryDetails.batteryRemainingTime.minute
            )
            progressBar.progress = batteryDetails.batteryCharge
        }

        if (batteryDetails.isOptimized) {
            binding.groupOptimizeIsDone.visibility = View.VISIBLE
            binding.dangerButton.apply {
                setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.green
                    )
                )
                setBackgroundResource(R.drawable.background_button_not_danger)
                text = getString(R.string.the_phone_does_not_need_optimization_at_the_moment)
            }
        } else {
            binding.groupOptimizeIsDone.visibility = View.GONE
            binding.dangerButton.apply {
                setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.red
                    )
                )
                setBackgroundResource(R.drawable.background_button_danger)
                text = getString(R.string.you_need_to_increase_the_working_time_of_the_phone)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun Group.setAllOnClickListener(listener: View.OnClickListener?) {
        referencedIds.forEach { id ->
            rootView.findViewById<View>(id).setOnClickListener(listener)
        }
    }
}