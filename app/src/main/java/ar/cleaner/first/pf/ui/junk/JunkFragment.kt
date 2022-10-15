package ar.cleaner.first.pf.ui.junk

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import ar.cleaner.first.pf.R
import ar.cleaner.first.pf.databinding.FragmentCleaningBinding
import ar.cleaner.first.pf.utils.MenuItems
import ar.cleaner.first.pf.utils.OptimizationProvider

class JunkFragment(
    private val onOptimizeClick: Fragment.() -> Unit
) : Fragment() {

    private var _binding: FragmentCleaningBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCleaningBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initClickListener()
        checkJunk()
    }

    @SuppressLint("StringFormatMatches")
    private fun checkJunk() {
        binding.percentTv.text =
            getString(R.string.mb_D, *OptimizationProvider.getVarArgs(MenuItems.CleaningJunk))
        if (OptimizationProvider.checkIsOptimized(MenuItems.CleaningJunk)) isOptimized() else isNotOptimized()
    }

    private fun isOptimized() {
        binding.groupIsOptimized.visibility = View.VISIBLE
        binding.groupIsOptimized.visibility = View.GONE
        binding.dangerButton.apply {
            background =
                ContextCompat.getDrawable(
                    requireContext(),
                    R.drawable.background_button_not_danger
                )
            text = getString(R.string.the_phone_is_cleared_of_excess_garbage)
            setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
        }
    }

    private fun isNotOptimized() {
        val listItems = OptimizationProvider.getJunkItems()
        with(binding) {
            cacheCountTv.text = getString(R.string.mb_D, listItems[0].size)
            tempFilesCountTv.text = getString(R.string.mb_D, listItems[1].size)
            residualFilesCountTv.text = getString(R.string.mb_D, listItems[2].size)
            systemGarbageCountTv.text = getString(R.string.mb_D, listItems[3].size)
        }
        binding.dangerButton.apply {
            background =
                ContextCompat.getDrawable(
                    requireContext(),
                    R.drawable.background_button_danger
                )
            text = getString(R.string.the_phone_needs_to_be_cleaned_of_excess_garbage)
            setTextColor(ContextCompat.getColor(requireContext(), R.color.red))
        }
    }

    private fun initClickListener() {
        binding.arrowBackIv.setOnClickListener { requireActivity().onBackPressed() }
        binding.boostButton.setOnClickListener { onOptimizeClick() }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}
