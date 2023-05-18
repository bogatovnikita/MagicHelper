package ar.cleaner.first.pf.ui.battery

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.Group
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import ar.cleaner.first.pf.R
import ar.cleaner.first.pf.databinding.FragmentBatteryBinding
import ar.cleaner.first.pf.domain.models.BatteryMode
import ar.cleaner.first.pf.domain.models.details.BatteryDetails
import ar.cleaner.first.pf.extensions.*
import ar.cleaner.first.pf.models.ModeGroup
import ar.cleaner.first.pf.ui.temperature.TemperatureFragment
import ar.cleaner.first.pf.ui.dialogs.DialogBluetoothPermission
import ar.cleaner.first.pf.ui.dialogs.DialogWriteSettings
import ar.cleaner.first.pf.utils.bluetoothPermissionList
import ar.cleaner.first.pf.utils.events.BaseEvent
import ar.cleaner.first.pf.utils.events.RuntimePermission
import ar.cleaner.first.pf.utils.events.SnackbarEvent
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class BatteryFragment : Fragment() {

    private var _binding: FragmentBatteryBinding? = null
    private val binding get() = _binding!!

    private lateinit var actionAdapter: OptimizationActionAdapter
    private val viewModel: BatteryViewModel by viewModels()
    private val dialog = DialogWriteSettings()
    private val dialogBluetooth = DialogBluetoothPermission()
    private var batteryMode: BatteryMode = BatteryMode.NORMAL
    private lateinit var preferences: SharedPreferences

    private val bluetoothMultiplePermissionLauncher = multiplePermissionLauncher { result ->
        if (result.all { it.value }) {
            binding.boostButton.performClick()
        } else viewModel.handleRationale(getString(R.string.snackbar_access_bluetooth))
    }

    private val startActivityForResultWiFi =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            navigateNext()
        }

    override fun onStart() {
        super.onStart()
        if (!checkWriteSettings()) dialog.show(parentFragmentManager, "BatteryFragment")
    }

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
        initPreferences()
        initAdapter()
        initBatteryMode()
        initObserver()
        initClickButtonOptimize()
    }

    private fun initPreferences() {
        preferences =
            requireContext().getSharedPreferences(
                TemperatureFragment.APP_PREFERENCES,
                Context.MODE_PRIVATE
            )
        if (!preferences.getBoolean(CHECK_BLUETOOTH_PERMISSION, false))
            preferences.edit().putBoolean(CHECK_BLUETOOTH_PERMISSION, false).apply()
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
        batteryMode = mode.mode
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
                renderState(it)
            }
        }
        viewModel.eventFlow.observeEvent(
            lifecycle,
            launchInScope = fragmentLifecycleScope,
            block = ::renderEvents
        )
    }

    private fun renderEvents(event: BaseEvent) {
        when (event) {
            is RuntimePermission -> {
                bluetoothMultiplePermissionLauncher.launch(bluetoothPermissionList)
            }
            is SnackbarEvent -> {
                lifecycleScope.launch {
                    binding.snackbarGroup.visibility = View.VISIBLE
                    binding.tvSettings.setOnClickListener {
                        openSettings()
                    }
                    delay(1500)
                    binding.snackbarGroup.visibility = View.GONE
                }
            }
        }
    }

    private fun renderState(batteryDetails: BatteryDetails) {
        if (!batteryDetails.loadingIsDone) return
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
                        R.color.black
                    )
                )
                setBackgroundResource(R.drawable.background_button_not_danger)
                text = "Бипки"
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

    private fun initClickButtonOptimize() {
        binding.arrowBackIv.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.boostButton.setOnClickListener {
            when (batteryMode) {
                BatteryMode.NORMAL -> {
                    when {
                        checkWriteSettings() -> {
                            navigateNext()
                        }
                        !checkWriteSettings() -> {
                            if (dialog.isAdded) return@setOnClickListener
                            dialog.show(parentFragmentManager, "BatteryFragment")
                        }
                    }
                }
                BatteryMode.MEDIUM, BatteryMode.HIGH -> {
                    when {
                        checkWriteSettings() && checkBluetoothPermission() -> {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                                openActivityToDisableWifi()
                            } else {
                                navigateNext()
                            }
                        }
                        !checkWriteSettings() -> {
                            if (dialog.isAdded) return@setOnClickListener
                            dialog.show(parentFragmentManager, "BatteryFragment")
                        }
                        !checkBluetoothPermission() -> {
                            if (!preferences.getBoolean(CHECK_BLUETOOTH_PERMISSION, false)) {
                                if (dialogBluetooth.isAdded) return@setOnClickListener
                                dialogBluetooth.show(parentFragmentManager, "BatterySaver")
                                dialogBluetooth.isCancelable = true
                                dialogBluetooth.addCallBackDialogPermissionWriteSetting(object :
                                    DialogBluetoothPermission.CallBackDialogPermission {
                                    override fun crossExitIvClick(isClick: Boolean) {
                                        viewModel.handleBluetoothPermission()
                                    }

                                    override fun crossAllowBtnClick(isClick: Boolean) {
                                        viewModel.handleBluetoothPermission()
                                    }
                                })
                                preferences.edit().putBoolean(CHECK_BLUETOOTH_PERMISSION, true)
                                    .apply()
                            } else {
                                viewModel.handleBluetoothPermission()
                            }
                        }
                    }
                }
            }
        }
    }

    private fun navigateNext() {
        preferences.edit().putString(BATTERY_MODE, batteryMode.name).apply()
        preferences.edit()
            .putInt(BATTERY_REMAINING_TIME_HOUR, viewModel.state.value.batteryRemainingTime.hour)
            .apply()
        preferences.edit()
            .putInt(
                BATTERY_REMAINING_TIME_MINUTE,
                viewModel.state.value.batteryRemainingTime.minute
            ).apply()
        findNavController().navigate(BatteryFragmentDirections.actionBatteryFragmentToBatteryProgressFragment())
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

    private fun checkBluetoothPermission(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S)
            checkPermissions(*bluetoothPermissionList)
        else Build.VERSION.SDK_INT <= Build.VERSION_CODES.S
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    private fun openActivityToDisableWifi() {
        startActivityForResultWiFi.launch(Intent(Settings.Panel.ACTION_INTERNET_CONNECTIVITY))
    }

    companion object {
        const val BATTERY_MODE = "BATTERY_MODE"
        const val BATTERY_REMAINING_TIME_HOUR = "BATTERY_REMAINING_TIME_HOUR"
        const val BATTERY_REMAINING_TIME_MINUTE = "BATTERY_REMAINING_TIME_MINUTE"
        const val CHECK_BLUETOOTH_PERMISSION = "CHECK_BLUETOOTH_PERMISSION"
    }
}