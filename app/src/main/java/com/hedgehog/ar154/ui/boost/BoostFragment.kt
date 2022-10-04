package com.hedgehog.ar154.ui.boost

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.hedgehog.ar154.R
import com.hedgehog.ar154.databinding.FragmentBoostBinding
import com.hedgehog.ar154.ui.battery.OptimizationActionAdapter
import com.hedgehog.ar154.utils.MenuItems
import com.hedgehog.ar154.utils.OptimizationProvider

class BoostFragment(
    private val onOptimizeClick: Fragment.() -> Unit
) : Fragment() {

    private lateinit var binding: FragmentBoostBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBoostBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.ivBack.setOnClickListener {
            requireActivity().onBackPressed()
        }

        binding.apply {
            val usageInfo = OptimizationProvider.getRamUsageInfo()
            tvPercents.text = getString(R.string.d_percents, usageInfo.percent)
            tvUsage.text = getString(R.string.d_gb_d_gb, usageInfo.availableGb, usageInfo.totalGb)
            pbUsage.progress = usageInfo.percent
            rvActions.apply {
                layoutManager = LinearLayoutManager(requireContext())
                val adapter = OptimizationActionAdapter()
                adapter.setItems(resources.getStringArray(R.array.optimization).toList())
                this.adapter = adapter
            }
        }

        checkState()

        binding.btnOptimize.setOnClickListener {
            onOptimizeClick()
        }
    }

    private fun checkState() {
        if (OptimizationProvider.checkIsOptimized(MenuItems.boost)) {
            binding.ivState.setImageResource(R.drawable.ic_state_done_medium)
            binding.tvState.visibility = View.GONE
            binding.tvActions.text = getString(R.string.ram_optimized)
            binding.rvActions.visibility = View.GONE
        }
    }

}