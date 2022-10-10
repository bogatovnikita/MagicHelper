package ar.cleaner.first.pf.ui.cpu

import android.os.Bundle
import android.text.Html
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ar.cleaner.first.pf.R
import ar.cleaner.first.pf.databinding.FragmentCoolingBinding
import ar.cleaner.first.pf.utils.BatInfoReceiver
import ar.cleaner.first.pf.utils.MenuItems
import ar.cleaner.first.pf.utils.OptimizationProvider

class CpuFragment(
    private val onOptimizeClick: Fragment.() -> Unit
) : Fragment() {

    private var _binding: FragmentCoolingBinding? = null
    private val binding get() = _binding!!

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
        binding.ivBack.setOnClickListener {
            requireActivity().onBackPressed()
        }

        BatInfoReceiver.getBatteryInfo().observe(viewLifecycleOwner) {
//            binding.tvTemp.text = getString(R.string.d_temperature, it)
        }

//        checkState()

        binding.btnOptimize.setOnClickListener {
            onOptimizeClick()
        }
    }

//    private fun checkState() {
//        if (OptimizationProvider.checkIsOptimized(MenuItems.cooling)) {
//            binding.apply {
//                ivState.setImageResource(R.drawable.ic_state_done_medium)
//                tvState.visibility = View.GONE
//            }
//            val string = getString(
//                R.string.apps_overloaded_d,
//                *OptimizationProvider.getVarArgs(MenuItems.cooling)
//            )
//            val spannableText = Html.fromHtml(string)
//            binding.tvActions.text = spannableText
//        } else {
//            val string = getString(
//                R.string.apps_overloaded_d,
//                *OptimizationProvider.getVarArgs(MenuItems.cooling)
//            )
//            val spannableText = Html.fromHtml(string)
//            val coloredSpannable = SpannableString(spannableText)
//            coloredSpannable.setSpan(
//                ForegroundColorSpan(resources.getColor(R.color.color_red)),
//                spannableText.length - 2,
//                spannableText.length,
//                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
//            )
//            binding.tvActions.text = coloredSpannable
//        }
//    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}