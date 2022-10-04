package com.hedgehog.ar154.ui.menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.hedgehog.ar154.R
import com.hedgehog.ar154.databinding.FragmentMenuBinding
import com.hedgehog.ar154.utils.MenuItems
import com.hedgehog.ar154.utils.OptimizationProvider

class MenuFragment(
    val onBattery: Fragment.() -> Unit,
    val onBoost: Fragment.() -> Unit,
    val onCpu: Fragment.() -> Unit,
    val onJunk: Fragment.() -> Unit,
) : Fragment() {

    private lateinit var binding: FragmentMenuBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMenuBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.recyclerView.adapter = MenuAdapter(requireContext(), OptimizationProvider) {
            when (it) {
                MenuItems.boost -> onBoost()
                MenuItems.power -> onBattery()
                MenuItems.cooling -> onCpu()
                MenuItems.cleaning -> onJunk()
            }
        }
        val info = OptimizationProvider.getRamUsageInfo()
        binding.tvPercents.text = info.percent.toString()
        binding.tvRamUsage.text = getString(R.string._2f_gb, info.usageGb)
        binding.tvRamTotal.text = getString(R.string.__2f_gb, info.totalGb)

    }
}