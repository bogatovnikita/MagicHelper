package com.hedgehog.ar154.ui.battery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.hedgehog.ar154.R
import com.hedgehog.ar154.data.BatteryMode
import com.hedgehog.ar154.data.OptimizationTypes
import com.hedgehog.ar154.databinding.FragmentBatteryBinding
import com.hedgehog.ar154.utils.BatteryChargeReceiver
import com.hedgehog.ar154.utils.MenuItems
import com.hedgehog.ar154.utils.OptimizationProvider
import com.hedgehog.ar154.utils.PreferencesProvider

class BatteryFragment(
    private val onOptimizeClick: Fragment.(OptimizationTypes) -> Unit
) : Fragment() {

    private lateinit var binding: FragmentBatteryBinding

    private lateinit var actionAdapter: OptimizationActionAdapter

    private lateinit var batteryMode: BatteryMode

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBatteryBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.ivBack.setOnClickListener {
            requireActivity().onBackPressed()
        }

        checkState()

        BatteryChargeReceiver.getInfo().observe(viewLifecycleOwner) {
            binding.tvPercents.text = getString(R.string.d_percents, it)
            binding.tvLeft.text =
                getString(R.string.d_h_d_min, *OptimizationProvider.getVarArgs(MenuItems.power))
            binding.pbCharge.progress = it
        }

        initRadioButtons()

        binding.btnOptimize.setOnClickListener {
            val type = when (batteryMode) {
                BatteryMode.low -> OptimizationTypes.BatteryLow
                BatteryMode.medium -> OptimizationTypes.BatteryMedium
                BatteryMode.high -> OptimizationTypes.BatteryHigh
            }
            onOptimizeClick(type)
        }
    }

    private fun initRadioButtons() {
        actionAdapter = OptimizationActionAdapter()
        binding.rvActions.apply {
            layoutManager = LinearLayoutManager(requireContext())
            this.adapter = actionAdapter
        }

        val actions = resources.getStringArray(R.array.battery).toList()
        val modeItems = mutableListOf<ModeGroup>()
        modeItems.add(
            ModeGroup(
                binding.rbNormal,
                binding.tvPercentsNormal,
                binding.tvNormal,
                actions.subList(0, 2),
                BatteryMode.low
            )
        )
        modeItems.add(
            ModeGroup(
                binding.rbUltra,
                binding.tvPercentsUltra,
                binding.tvUltra,
                actions.subList(0, 4),
                BatteryMode.medium
            )
        )
        modeItems.add(
            ModeGroup(
                binding.rbExtra,
                binding.tvPercentsExtra,
                binding.tvExtra,
                actions,
                BatteryMode.high
            )
        )
        modeItems.forEach { mode ->
            mode.radioButton.setOnClickListener {
                enable(mode)
                disable(modeItems.filterNot { it == mode })
            }
        }

        val item = when (BatteryMode.getByName(PreferencesProvider.getBatteryType())) {
            BatteryMode.low -> modeItems[0]
            BatteryMode.medium -> modeItems[1]
            BatteryMode.high -> modeItems[2]
        }
        enable(item)
    }

    private fun enable(modeGroup: ModeGroup) {
        modeGroup.radioButton.isChecked = true
        modeGroup.modeName.alpha = 1f
        modeGroup.percents.alpha = 1f
        actionAdapter.setItems(modeGroup.modeActions)
        batteryMode = modeGroup.mode
    }

    private fun disable(modeGroups: List<ModeGroup>) {
        modeGroups.forEach {
            it.radioButton.isChecked = false
            it.modeName.alpha = 0.5f
            it.percents.alpha = 0.5f
        }
    }

    private fun checkState() {
        if (OptimizationProvider.checkIsOptimized(MenuItems.power)) {
            binding.ivState.setImageResource(R.drawable.ic_state_done_medium)
            binding.tvState.visibility = View.GONE
        }
    }

    private data class ModeGroup(
        val radioButton: RadioButton,
        val percents: TextView,
        val modeName: TextView,
        val modeActions: List<String>,
        val mode: BatteryMode
    )
}