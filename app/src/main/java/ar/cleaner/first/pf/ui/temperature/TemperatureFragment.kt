package ar.cleaner.first.pf.ui.temperature

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import ar.cleaner.first.pf.R
import ar.cleaner.first.pf.databinding.FragmentTemperatureBinding
import ar.cleaner.first.pf.domain.models.details.TemperatureDetails
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TemperatureFragment : Fragment(R.layout.fragment_temperature) {

    private val binding: FragmentTemperatureBinding by viewBinding()

    private lateinit var preferences: SharedPreferences

    private val viewModel: TemperatureViewModel by viewModels()

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
            findNavController().navigate(TemperatureFragmentDirections.actionTemperatureFragmentToTemperatureProgressFragment())
        }
        binding.arrowBackIv.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun initObserver() {
        lifecycleScope.launch {
            viewModel.state.collect {
                renderState(it)
            }
        }
    }

    private fun renderState(temperatureDetails: TemperatureDetails) {
        renderTemperature(temperatureDetails.temperature)
        renderButton(temperatureDetails.isTemperatureChecked)
        binding.groupOptimizeIsDone.isVisible = temperatureDetails.isTemperatureChecked
    }

    private fun renderButton(isTemperatureChecked: Boolean) {
        if (isTemperatureChecked) {
            binding.dangerButton.apply {
                setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.black
                    )
                )
                setBackgroundResource(R.drawable.background_button_not_danger)
                text = getString(R.string.temperature_normal_desc)
            }
        } else {
            binding.dangerButton.apply {
                setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.red
                    )
                )
                setBackgroundResource(R.drawable.background_button_danger)
                text = getString(R.string.temperature_danger_desc)
            }
        }
    }
    private fun renderTemperature(temperature: Int) {
        val isShowTemperature = temperature != TEMPERATURE_NOT_SAVE
        binding.percentTv.isVisible = isShowTemperature
        binding.tvTempNotCheck.isVisible = !isShowTemperature
        binding.percentTv.text =
            requireContext().getString(R.string.temperature_D, temperature)
    }

    companion object {
        const val APP_PREFERENCES = "APP_PREFERENCES"
        const val COOLER_TEMPERATURE = "COOLER_TEMPERATURE"
        const val TEMPERATURE_NOT_SAVE = -1
    }
}