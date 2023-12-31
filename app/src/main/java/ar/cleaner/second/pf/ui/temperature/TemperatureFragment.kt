package ar.cleaner.second.pf.ui.temperature

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import ar.cleaner.first.pf.domain.models.details.TemperatureDetails
import ar.cleaner.second.pf.R
import ar.cleaner.second.pf.databinding.FragmentTemperatureBinding
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TemperatureFragment : Fragment(R.layout.fragment_temperature) {

    private val binding: FragmentTemperatureBinding by viewBinding()

    private val viewModel: TemperatureViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.initCpuDetails()
        initObserver()
        initClick()
    }

    private fun initClick() {
        binding.boostButton.setOnClickListener {
            findNavController().navigate(R.id.action_to_temperatureProgressFragment)
        }

        binding.toolbar.binding.arrowBackIv.setOnClickListener {
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
                setBackgroundResource(R.drawable.bg_button_not_danger)
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
                setBackgroundResource(R.drawable.bg_button_danger)
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
        const val TEMPERATURE_NOT_SAVE = -1
    }
}