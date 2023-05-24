package ar.cleaner.first.pf.ui.battery

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.Group
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
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
import ar.cleaner.first.pf.ui.dialogs.DialogBluetoothPermission
import ar.cleaner.first.pf.ui.dialogs.DialogWriteSettings
import ar.cleaner.first.pf.utils.bluetoothPermissionList
import ar.cleaner.first.pf.utils.events.BaseEvent
import ar.cleaner.first.pf.utils.events.RuntimePermission
import ar.cleaner.first.pf.utils.events.SnackbarEvent
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class BatteryFragment : Fragment(R.layout.fragment_battery) {

    private val binding: FragmentBatteryBinding by viewBinding()

    private val actionAdapter: OptimizationActionAdapter = OptimizationActionAdapter()
    private val viewModel: BatteryViewModel by viewModels()
    private val dialog = DialogWriteSettings()
    private val dialogBluetooth = DialogBluetoothPermission()

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.initBatteryDetails()
        initAdapter()
        initListeners()
        initObserver()
        initClickButtonOptimize()
    }

    private fun initAdapter() {
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            this.adapter = actionAdapter
        }
    }

    private fun initListeners() {
        binding.groupModes.setAllOnClickListener { mode ->
            when (mode) {
                binding.buttonNormalMode -> {
                    viewModel.setBatteryMode(BatteryMode.NORMAL)
                }
                binding.buttonUltraMode -> {
                    viewModel.setBatteryMode(BatteryMode.MEDIUM)
                }
                binding.buttonExtraMode -> {
                    viewModel.setBatteryMode(BatteryMode.HIGH)
                }
            }
        }
    }

    private fun setEnableMode(mode: BatteryMode) {
        val listBtn =
            listOf(
                BatteryMode.HIGH to binding.buttonExtraMode,
                BatteryMode.MEDIUM to binding.buttonUltraMode,
                BatteryMode.NORMAL to binding.buttonNormalMode
            )
        listBtn.forEach {
            val background =
                if (it.first == mode) R.drawable.card_corners_green else R.drawable.card_corners_comet
            it.second.background = ContextCompat.getDrawable(requireContext(), background)
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
        with(binding) {
            toolbar.binding.titleTv.text = getString(R.string.battery_title)
            percentTv.text = getString(R.string.percent_D, batteryDetails.batteryCharge)
            progressBar.progress = batteryDetails.batteryCharge
            groupOptimizeIsDone.isVisible = batteryDetails.isOptimized
            binding.descriptionModeTv.setText(
                if (batteryDetails.batteryMode == BatteryMode.NORMAL)
                    R.string.battery_normal_mode_activates_restrictions
                else if (batteryDetails.batteryMode == BatteryMode.MEDIUM)
                    R.string.battery_ultra_mode_activates_restrictions
                else
                    R.string.battery_extra_mode_activates_restrictions
            )
        }
        renderDescriptionItem(batteryDetails.isOptimized)
        actionAdapter.setItems(batteryDetails.batteryListFun)
        setEnableMode(batteryDetails.batteryMode)
    }

    private fun renderDescriptionItem(isOptimized: Boolean) {
        val color = if (isOptimized) R.color.black else R.color.red
        val background =
            if (isOptimized) R.drawable.bg_button_not_danger else R.drawable.bg_button_danger
        val textId =
            if (isOptimized) R.string.battery_not_danger_description else R.string.battery_danger_description
        binding.dangerButton.apply {
            setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    color
                )
            )
            setBackgroundResource(background)
            text = getString(textId)
        }
    }

    private fun initClickButtonOptimize() {
        binding.toolbar.binding.arrowBackIv.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.boostButton.setOnClickListener {
            when (viewModel.state.value.batteryMode) {
                BatteryMode.NORMAL, BatteryMode.MEDIUM -> {
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
                BatteryMode.HIGH -> {
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
                        }
                    }
                }
            }
        }
    }

    private fun navigateNext() {
        viewModel.startOptimization()
        findNavController().navigate(R.id.action_to_batteryProgressFragment)
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

}