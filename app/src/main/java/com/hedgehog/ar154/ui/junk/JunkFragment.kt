package com.hedgehog.ar154.ui.junk

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.hedgehog.ar154.R
import com.hedgehog.ar154.databinding.FragmentCleaningBinding
import com.hedgehog.ar154.utils.MenuItems
import com.hedgehog.ar154.utils.OptimizationProvider

class JunkFragment(
    private val onOptimizeClick: Fragment.() -> Unit
) : Fragment() {

    private lateinit var binding: FragmentCleaningBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCleaningBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.ivBack.setOnClickListener {
            requireActivity().onBackPressed()
        }

        initAdapter()
        checkState()
        binding.tvGarbage.text =
            getString(R.string.d_mb, *OptimizationProvider.getVarArgs(MenuItems.cleaning))

        binding.btnOptimize.setOnClickListener { onOptimizeClick() }
    }

    private fun initAdapter() {
        val adapter = JunkAdapter(OptimizationProvider.getJunkItems())
        binding.rvJunk.apply {
            layoutManager = LinearLayoutManager(requireContext())
            this.adapter = adapter
        }
    }

    private fun checkState() {
        if (OptimizationProvider.checkIsOptimized(MenuItems.cleaning)) {
            binding.ivState.setImageResource(R.drawable.ic_state_done_medium)
            binding.tvState.visibility = View.GONE
        }
    }

}
