package ar.cleaner.first.pf.ui.cooling

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import ar.cleaner.first.pf.R
import ar.cleaner.first.pf.databinding.FragmentCoolingBinding
import ar.cleaner.first.pf.domain.models.details.CpuDetails
import ar.cleaner.first.pf.extensions.fragmentLifecycleScope
import ar.cleaner.first.pf.extensions.observeWhenResumed
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CoolingFragment : Fragment() {

    private var _binding: FragmentCoolingBinding? = null
    private val binding get() = _binding!!

    private lateinit var preferences: SharedPreferences

    private val viewModel: CoolingViewModel by viewModels()
    private var value = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCoolingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.initCpuDetails()
        initObserver()
        initClick()
    }

    private fun initClick() {
        binding.boostButton.setOnClickListener {
            preferences =
                requireContext().getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE)
            preferences.edit().putInt(COOLER_TEMPERATURE, viewModel.state.value.temperature.toInt())
                .apply()
            findNavController().navigate(CoolingFragmentDirections.actionCoolingFragmentToCoolingProgressFragment())
        }
        binding.arrowBackIv.setOnClickListener {
            findNavController().popBackStack()
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

    private fun renderState(cpuDetails: CpuDetails) {
        binding.percentTv.text =
            requireContext().getString(R.string.percent_D, cpuDetails.temperature.toInt())
        if (cpuDetails.isOptimized) {
            binding.groupOptimizeIsDone.visibility = View.VISIBLE
            binding.groupIsNotOptimize.visibility = View.GONE
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
            binding.groupIsNotOptimize.visibility = View.VISIBLE
            binding.groupOptimizeIsDone.visibility = View.GONE
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

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        const val APP_PREFERENCES = "APP_PREFERENCES"
        const val COOLER_TEMPERATURE = "COOLER_TEMPERATURE"
    }
}