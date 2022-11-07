package ar.cleaner.first.pf.ui.junk

import android.Manifest
import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import ar.cleaner.first.pf.R
import ar.cleaner.first.pf.databinding.FragmentCleaningBinding
import ar.cleaner.first.pf.extensions.checkPermissions
import ar.cleaner.first.pf.extensions.checkUsageStatsAllowed
import ar.cleaner.first.pf.extensions.openSettings
import ar.cleaner.first.pf.ui.cooling.CoolingFragment
import ar.cleaner.first.pf.ui.dialogs.DialogAccessUsageSettings
import ar.cleaner.first.pf.ui.dialogs.DialogManageStorage
import ar.cleaner.first.pf.ui.dialogs.DialogReadPermission
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random

@AndroidEntryPoint
class JunkFragment : Fragment() {

    private var _binding: FragmentCleaningBinding? = null
    private val binding get() = _binding!!

    private val viewModel: JunkViewModel by viewModels()

    private lateinit var preferences: SharedPreferences

    private val dialogManageStorage = DialogManageStorage()
    private val dialogAccessUsageSettings = DialogAccessUsageSettings()
    private val dialogReadPermission = DialogReadPermission()

    private var junkSize = 0

    private val permissionsList = listOf(
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.READ_EXTERNAL_STORAGE
    ).toTypedArray()

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { result ->
            viewModel.handleStorageGranted(result.all { it.value })
            if (result.all { it.value }) {
                launchPermissionsOrSubscribeOnData()
            } else {
                showSnackBar()
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCleaningBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        launchPermissionsOrSubscribeOnData()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        preferences = requireContext().getSharedPreferences(
            CoolingFragment.APP_PREFERENCES,
            Context.MODE_PRIVATE
        )
        initClick()
    }

    private fun initClick() {
        binding.arrowBackIv.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.boostButton.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                if (Environment.isExternalStorageManager() && checkUsageStatsAllowed()) {
                    preferences.edit().putInt(JUNK_SIZE, junkSize).apply()
                    findNavController().navigate(
                        JunkFragmentDirections.actionJunkFragmentToJunkProgressFragment(
                            viewModel.state.value.listParcelableJunk.toTypedArray()
                        )
                    )
                } else if (!Environment.isExternalStorageManager()) {
                    dialogManageStorage.show(
                        parentFragmentManager,
                        "JunkFragmentStorage"
                    )
                } else if (!checkUsageStatsAllowed()) {
                    dialogAccessUsageSettings.show(
                        parentFragmentManager,
                        "JunkFragmentUsage"
                    )
                }
            } else {
                if (checkPermissions(*permissionsList) && checkUsageStatsAllowed()) {
                    preferences.edit().putInt(JUNK_SIZE, junkSize).apply()
                    findNavController().navigate(
                        JunkFragmentDirections.actionJunkFragmentToJunkProgressFragment(
                            viewModel.state.value.listParcelableJunk.toTypedArray()
                        )
                    )
                } else if (!checkPermissions(*permissionsList)) {
                    showSnackBar()
                } else if (!checkUsageStatsAllowed()) {
                    dialogAccessUsageSettings.show(
                        parentFragmentManager,
                        "JunkFragmentUsage"
                    )
                }
            }
        }
    }

    private fun initObservers() {
        lifecycleScope.launch {
            viewModel.state.collect {
                renderState(it)
            }
        }
    }

    private fun renderState(state: JunkState) {
        if (state.isLoadingJunkFiles) {
            binding.shimmerGroup.visibility = View.GONE
            if (!state.responseUseCase) return
            if (!state.isOptimizeDone) {
                with(binding) {
                    groupNotOptimized.visibility = View.VISIBLE
                    groupIsOptimized.visibility = View.GONE
                    junkSize =
                        state.valueEmptyFolders + state.valueCache + state.valueThumbnails + state.valueUnnecessaryApk
                    binding.percentTv.text = getString(R.string.mb_D, junkSize)
                    cacheCountTv.text = getString(R.string.mb_D, state.valueCache)
                    tempFilesCountTv.text = getString(R.string.mb_D, state.valueUnnecessaryApk)
                    residualFilesCountTv.text =
                        getString(R.string.mb_D, state.valueEmptyFolders)
                    systemGarbageCountTv.text = getString(R.string.mb_D, state.valueThumbnails)
                    dangerButton.apply {
                        setTextColor(
                            ContextCompat.getColor(
                                requireContext(),
                                R.color.red
                            )
                        )
                        setBackgroundResource(R.drawable.background_button_danger)
                        text =
                            getString(R.string.the_phone_needs_to_be_cleaned_of_excess_garbage)
                    }
                }
            } else {
                with(binding) {
                    groupNotOptimized.visibility = View.GONE
                    groupIsOptimized.visibility = View.VISIBLE
                    junkSize = Random.nextInt(4, 7)
                    binding.percentTv.text = getString(R.string.mb_D, junkSize)
                    dangerButton.apply {
                        setTextColor(
                            ContextCompat.getColor(
                                requireContext(),
                                R.color.black
                            )
                        )
                        setBackgroundResource(R.drawable.background_button_not_danger)
                        text = getString(R.string.the_phone_is_cleared_of_excess_garbage)
                    }
                }
            }
        }
    }

    private fun launchPermissionsOrSubscribeOnData() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            when {
                !Environment.isExternalStorageManager() -> {
                    dialogManageStorage.show(
                        parentFragmentManager,
                        "JunkFragmentStorage"
                    )
                }
                !checkUsageStatsAllowed() -> {
                    dialogAccessUsageSettings.show(
                        parentFragmentManager,
                        "JunkFragmentUsage"
                    )
                }
                else -> {
                    viewModel.loadingJunkFilesIsDone()
                    initObservers()
                }
            }
        } else {
            when {
                checkPermissions(*permissionsList) -> {
                    checkUsageStats()
                }
                else -> {
                    if (preferences.getBoolean(CHECK_WRITE_READ_PERMISSION, true)) {
                        dialogReadPermission.show(parentFragmentManager, "JunkFragment")
                        dialogReadPermission.isCancelable = true
                        dialogReadPermission.addCallBackDialogPermissionWriteSetting(object :
                            DialogReadPermission.CallBackDialogPermission {
                            override fun crossExitIvClick(isClick: Boolean) {
                                requestPermissionLauncher.launch(permissionsList)
                            }

                            override fun crossAllowBtnClick(isClick: Boolean) {
                                requestPermissionLauncher.launch(permissionsList)
                            }
                        })
                        preferences.edit().putBoolean(CHECK_WRITE_READ_PERMISSION, false).apply()
                    } else {
                        requestPermissionLauncher.launch(permissionsList)
                    }
                }
            }
        }
    }

    private fun checkUsageStats() {
        if (!checkUsageStatsAllowed()) {
            dialogAccessUsageSettings.show(
                parentFragmentManager,
                "JunkFragmentUsage"
            )
        } else {
            viewModel.handleUsageStatsGranted(true)
            viewModel.loadingJunkFilesIsDone()
            initObservers()
        }
    }

    private fun showSnackBar() {
        lifecycleScope.launch {
            binding.snackbarGroup.visibility = View.VISIBLE
            binding.tvSettings.setOnClickListener {
                openSettings()
            }
            delay(1500)
            binding.snackbarGroup.visibility = View.GONE
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        const val CHECK_WRITE_READ_PERMISSION = "CHECK_WRITE_READ_PERMISSION"
        const val JUNK_SIZE = "JUNK_SIZE"
    }
}
