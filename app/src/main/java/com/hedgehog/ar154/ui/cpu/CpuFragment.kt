package com.hedgehog.ar154.ui.cpu

import android.os.Bundle
import android.text.Html
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.hedgehog.ar154.R
import com.hedgehog.ar154.databinding.FragmentCoolingBinding
import com.hedgehog.ar154.utils.BatInfoReceiver
import com.hedgehog.ar154.utils.MenuItems
import com.hedgehog.ar154.utils.OptimizationProvider

class CpuFragment(
    private val onOptimizeClick: Fragment.() -> Unit
) : Fragment() {

    private lateinit var binding: FragmentCoolingBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCoolingBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.ivBack.setOnClickListener {
            requireActivity().onBackPressed()
        }

        BatInfoReceiver.getBatteryInfo().observe(viewLifecycleOwner) {
            binding.tvTemp.text = getString(R.string.d_temperature, it)
        }

        checkState()

        binding.btnOptimize.setOnClickListener {
            onOptimizeClick()
        }
    }

    private fun checkState() {
        if (OptimizationProvider.checkIsOptimized(MenuItems.cooling)) {
            binding.apply {
                ivState.setImageResource(R.drawable.ic_state_done_medium)
                tvState.visibility = View.GONE
            }
            val string = getString(
                R.string.apps_overloaded_d,
                *OptimizationProvider.getVarArgs(MenuItems.cooling)
            )
            val spannableText = Html.fromHtml(string)
            binding.tvActions.text = spannableText
        } else {
            val string = getString(
                R.string.apps_overloaded_d,
                *OptimizationProvider.getVarArgs(MenuItems.cooling)
            )
            val spannableText = Html.fromHtml(string)
            val coloredSpannable = SpannableString(spannableText)
            coloredSpannable.setSpan(
                ForegroundColorSpan(resources.getColor(R.color.color_red)),
                spannableText.length-2,
                spannableText.length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            binding.tvActions.text = coloredSpannable
        }
    }

}