package ar.cleaner.first.pf.ui.battery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.Group
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import ar.cleaner.first.pf.R
import ar.cleaner.first.pf.data.BatteryMode
import ar.cleaner.first.pf.data.OptimizationTypes
import ar.cleaner.first.pf.databinding.FragmentBatteryBinding
import ar.cleaner.first.pf.utils.BatteryChargeReceiver
import ar.cleaner.first.pf.utils.MenuItems
import ar.cleaner.first.pf.utils.OptimizationProvider

class BatteryFragment(
    private val onOptimizeClick: Fragment.(OptimizationTypes) -> Unit
) : Fragment() {

    private var _binding: FragmentBatteryBinding? = null
    private val binding get() = _binding!!

    private lateinit var actionAdapter: OptimizationActionAdapter
    private lateinit var batteryMode: BatteryMode

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
        initClickListener()
        initAdapter()
        checkState()
        initBatteryInfo()
        initClickModeBattery()
    }

    private fun initAdapter() {
        actionAdapter = OptimizationActionAdapter()
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            this.adapter = actionAdapter
        }
    }

    private fun initClickModeBattery() {
        val actions = resources.getStringArray(R.array.battery_items).toList()
        val listMode = mutableListOf<ModeGroup>()
        listMode.add(
            ModeGroup(
                binding.buttonNormalMode,
                R.string.normal_mode_activates_restrictions,
                actions.subList(0, 2),
                BatteryMode.low
            )
        )
        listMode.add(
            ModeGroup(
                binding.buttonUltraMode,
                R.string.ultra_mode_activates_restrictions,
                actions.subList(0, 4),
                BatteryMode.medium
            )
        )
        listMode.add(
            ModeGroup(
                binding.buttonExtraMode,
                R.string.extra_mode_activates_restrictions,
                actions,
                BatteryMode.high
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
        batteryMode = mode.mode
    }

    private fun disableMode(modeList: List<ModeGroup>) {
        modeList.forEach {
            it.modeButton.background =
                ContextCompat.getDrawable(requireContext(), R.drawable.card_corners_comet)
        }
    }

    private fun initClickListener() {
        binding.arrowBackIv.setOnClickListener {
            requireActivity().onBackPressed()
        }
        binding.boostButton.setOnClickListener {
            val type = when (batteryMode) {
                BatteryMode.low -> OptimizationTypes.BatteryLow
                BatteryMode.medium -> OptimizationTypes.BatteryMedium
                BatteryMode.high -> OptimizationTypes.BatteryHigh
            }
            onOptimizeClick(type)
        }
    }

    private fun initBatteryInfo() {
        BatteryChargeReceiver.getInfo().observe(viewLifecycleOwner) {
            binding.percentTv.text = getString(R.string.percent_d, it)
            binding.occupiedTotalTv.text = getString(
                R.string.battery_power_only_time,
                *OptimizationProvider.getVarArgs(MenuItems.BatteryPower)
            )
            binding.progressBar.progress = it
        }
    }

    private fun checkState() {
        if (OptimizationProvider.checkIsOptimized(MenuItems.BatteryPower)) {
            isOptimized()
        } else {
            isNotOptimized()
        }
    }

    private fun isNotOptimized() {
        binding.dangerButton.apply {
            background =
                ContextCompat.getDrawable(
                    requireContext(),
                    R.drawable.background_button_danger
                )
            text = getString(R.string.you_need_to_increase_the_working_time_of_the_phone)
            setTextColor(ContextCompat.getColor(requireContext(), R.color.red))
        }
    }

    private fun isOptimized() {
        binding.groupOptimizeIsDone.visibility = View.VISIBLE
        binding.dangerButton.apply {
            background =
                ContextCompat.getDrawable(
                    requireContext(),
                    R.drawable.background_button_not_danger
                )
            text = getString(R.string.the_phone_does_not_need_optimization_at_the_moment)
            setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
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

    private data class ModeGroup(
        val modeButton: View,
        val modeHeader: Int,
        val modeAction: List<String>,
        val mode: BatteryMode
    )
}