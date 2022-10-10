package ar.cleaner.first.pf.ui.junk

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
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
        binding.ivBack.setOnClickListener {
            requireActivity().onBackPressed()
        }

        initAdapter()
        checkState()
//        binding.tvGarbage.text =
//            getString(R.string.d_mb, *OptimizationProvider.getVarArgs(MenuItems.cleaning))

        binding.btnOptimize.setOnClickListener { onOptimizeClick() }
    }

    private fun initAdapter() {
//        val adapter = JunkAdapter(OptimizationProvider.getJunkItems())
        binding.rvJunk.apply {
            layoutManager = LinearLayoutManager(requireContext())
            this.adapter = adapter
        }
    }

    private fun checkState() {
//        if (OptimizationProvider.checkIsOptimized(MenuItems.cleaning)) {
//            binding.ivState.setImageResource(R.drawable.ic_state_done_medium)
            binding.tvState.visibility = View.GONE
//        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}
