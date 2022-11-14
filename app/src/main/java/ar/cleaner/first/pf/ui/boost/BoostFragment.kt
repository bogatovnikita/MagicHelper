package ar.cleaner.first.pf.ui.boost

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import ar.cleaner.first.pf.R
import ar.cleaner.first.pf.databinding.FragmentBoostBinding
import ar.cleaner.first.pf.extensions.checkUsageStatsAllowed
import ar.cleaner.first.pf.ui.cooling.CoolingFragment
import ar.cleaner.first.pf.ui.dialogs.DialogAccessUsageSettings
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class BoostFragment : Fragment() {

    private var _binding: FragmentBoostBinding? = null
    private val binding get() = _binding!!
    private val viewModel: BoostViewModel by viewModels()
    private val dialog = DialogAccessUsageSettings()
    private lateinit var preferences: SharedPreferences
    private var value = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBoostBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.initRamDetails()
        initObserver()
        initClick()
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

    private fun renderState(state: BoostState) {
        state.ramDetails ?: return
        binding.percentTv.text = requireContext().getString(
            R.string.percent_D, state.ramDetails.usagePercents
        )
        binding.progressBar.progress = state.ramDetails.usagePercents
        binding.occupiedTotalTv.text = requireContext().getString(
            R.string._F_gb_F_gb,
            state.ramDetails.usedRam,
            state.ramDetails.totalRam
        )
        if (state.ramDetails.isOptimized) {
            binding.groupOptimizeIsDone.visibility = View.VISIBLE
            binding.groupIsNotOptimize.visibility = View.GONE
            binding.dangerButton.apply {
                setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.black
                    )
                )
                setBackgroundResource(R.drawable.background_button_not_danger)
                text = getString(R.string.the_phone_does_need_any_acceleration_at_the_moment)
            }
        } else {
            binding.groupOptimizeIsDone.visibility = View.GONE
            binding.groupIsNotOptimize.visibility = View.VISIBLE
            binding.dangerButton.apply {
                setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.red
                    )
                )
                setBackgroundResource(R.drawable.background_button_danger)
                text = getString(R.string.memory_is_overloaded_optimization_is_required)
            }

        }
    }

    private fun initClick() {
        binding.arrowBackIv.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.boostButton.setOnClickListener {
            checkPermissionOrSubscribeOnData()
        }
    }

    private fun checkPermissionOrSubscribeOnData() {
        when {
            checkUsageStatsAllowed() -> {
                preferences =
                    requireContext().getSharedPreferences(
                        CoolingFragment.APP_PREFERENCES,
                        Context.MODE_PRIVATE
                    )
                preferences.edit()
                    .putInt(BOOST_PERCENT, viewModel.state.value.ramDetails!!.usagePercents).apply()
                preferences.edit().putFloat(
                    BOOST_CLEAR_VALUE,
                    viewModel.state.value.ramDetails!!.usedRam.toFloat()
                ).apply()
                findNavController().navigate(
                    BoostFragmentDirections.actionBoostFragmentToBoostProgressFragment()
                )
            }
            else -> {
                dialog.show(parentFragmentManager, "BoostFragment")
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        const val BOOST_PERCENT = "BOOST_PERCENT"
        const val BOOST_CLEAR_VALUE = "BOOST_CLEAR_VALUE"
    }
}